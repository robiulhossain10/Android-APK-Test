package com.robiul.firstapk.products;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.robiul.firstapk.MainActivity;
import com.robiul.firstapk.R;
import com.robiul.firstapk.dbUtil.ProductDao;
import com.robiul.firstapk.products.entity.Product;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductsAdd extends AppCompatActivity {
    ProductDao productDao = new ProductDao(this);
    private Product product;
    EditText name,email,quantity,price;
    Button addproduct;
    Button btnback;

    ImageView imgView;
    private Uri selectedImageUri;
    private Uri cameraImageUri;

    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private ActivityResultLauncher<String> permissionLauncher;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_products_add);

        name = findViewById(R.id.etName);
        email = findViewById(R.id.etEmail);
        quantity = findViewById(R.id.etQuantity);
        price = findViewById(R.id.etPrice);
        addproduct = findViewById(R.id.btnAddProduct);
        btnback = findViewById(R.id.backbutton);
        imgView = findViewById(R.id.imageView);




        addproduct.setOnClickListener(v->
        {
            saveProduct(this);
        });

//        addproduct.setOnClickListener(v-> {
//            String Name = name.getText().toString().trim();
//            String Email = email.getText().toString().trim();
//            String Price = price.getText().toString().trim();
//            String Quantity = quantity.getText().toString().trim();
//
//
//            double price = Double.parseDouble(Price);
//            int qty = Integer.parseInt(Quantity);
//
//            if (product == null){
//                product = new Product( Name,Email,price,qty);
//                long id = productDao.insert(product);
//                if (id>0) Toast.makeText(this, "Product Added", Toast.LENGTH_SHORT).show();
//                else Toast.makeText(this,"Insert Failed",Toast.LENGTH_SHORT).show();
//            }

//            else {
//                product.setName(Name);
//                product.setEmail(Email);
//                product.setPrice(price);
//                product.setQuantity(qty);
//
//                int rows = productDao.update(product);
//                if (rows>0)Toast.makeText(this,"Product Updated",Toast.LENGTH_SHORT).show();
//                else Toast.makeText(this,"Update Failed", Toast.LENGTH_SHORT).show();
//
//            }

//             product = new Product(Name,Email,Price,Quantity);
//             productDao.insert(product);
//        });

        btnback.setOnClickListener(v->{
            Intent intent = new Intent(ProductsAdd.this, MainActivity.class);
            startActivity(intent);
        });

        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if(isGranted) openCamera();
                    else Toast.makeText(this,"Camera Permission Denied", Toast.LENGTH_SHORT).show();
                });

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null && result.getData().getData() != null) {
                        selectedImageUri = result.getData().getData();

                        getContentResolver().takePersistableUriPermission(selectedImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

                        imgView.setImageURI(selectedImageUri);
                    } else if (result.getResultCode() == RESULT_OK && cameraImageUri != null) {
                        selectedImageUri = cameraImageUri;
                        imgView.setImageURI(selectedImageUri);
                    }
                });

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        imgView.setOnClickListener(v->
                showImageSourceDialog()
        );

        // Check if editing
        int productId = getIntent().getIntExtra("PRODUCT_ID", -1);
        System.out.println("product ID :---------------"+productId);
        if (productId != -1) {

            product = productDao.getProductById(productId);
            System.out.println(product);
            if (product != null) {
                System.out.println(product);
                name.setText(product.getName());
                email.setText(product.getEmail());
                price.setText(String.valueOf(product.getPrice()));
                quantity.setText(String.valueOf(product.getQuantity()));
                if (product.getImageUri() != null) {
                    selectedImageUri = Uri.parse(product.getImageUri());
                    imgView.setImageURI(selectedImageUri);
                }
            }


        }


    }

    private void showImageSourceDialog() {
        String[] options = {"Camera", "Gallery"};
        new AlertDialog.Builder(this)
                .setTitle("Select Image Source")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        // Camera
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                == PackageManager.PERMISSION_GRANTED) {
                            openCamera();
                        } else {
                            permissionLauncher.launch(Manifest.permission.CAMERA);
                        }
                    } else {
                        // Gallery
                        Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        galleryIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        galleryIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                        galleryIntent.setType("image/*");
                        imagePickerLauncher.launch(galleryIntent);
                    }
                })
                .show();
    }

    // 📸 Open Camera
    private void openCamera() {
        try {
            File photoFile = createImageFile();
            cameraImageUri = FileProvider.getUriForFile(
                    this,
                    getPackageName() + ".provider",
                    photoFile
            );
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri);
            imagePickerLauncher.launch(cameraIntent);
        } catch (IOException e) {
            Toast.makeText(this, "Error opening camera", Toast.LENGTH_SHORT).show();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "IMG_" + timeStamp;
        File storageDir = getExternalFilesDir(null);
        return File.createTempFile(fileName, ".jpg", storageDir);
    }



    // 💾 Save Product
    private void saveProduct(Context con) {
        String Name = name.getText().toString().trim();
        String Email = email.getText().toString().trim();
        String Price = price.getText().toString().trim();
        String Quantity = quantity.getText().toString().trim();

        if (Name.isEmpty() || Email.isEmpty() || Price.isEmpty() || Quantity.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(Price);
        int qty = Integer.parseInt(Quantity);

        if (product == null) {
            product = new Product(0, Name, Email, price, qty,
                    selectedImageUri != null ? selectedImageUri.toString() : null);
            long id = productDao.insert(product);
            if (id > 0) Toast.makeText(this, "Product Added!", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "Insert Failed!", Toast.LENGTH_SHORT).show();
        } else {
            product.setName(Name);
            product.setEmail(Email);
            product.setPrice(price);
            product.setQuantity(qty);
            product.setImageUri(selectedImageUri != null ? selectedImageUri.toString() : null);

            int rows = productDao.update(product);
            if (rows > 0) Toast.makeText(this, "Product Updated!", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "Update Failed!", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(con, ProductList.class);
        startActivity(intent);
        finish();
    }
}