package com.robiul.firstapk.products;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.robiul.firstapk.MainActivity;
import com.robiul.firstapk.R;
import com.robiul.firstapk.dbUtil.ProductDao;
import com.robiul.firstapk.products.entity.Product;

public class ProductsAdd extends AppCompatActivity {
    ProductDao productDao = new ProductDao(this);
    private Product product;
EditText name,email,quantity,price;
Button addproduct;
Button btnback;



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





        addproduct.setOnClickListener(v-> {
            String Name = name.getText().toString().trim();
            String Email = email.getText().toString().trim();
            String Price = price.getText().toString().trim();
            String Quantity = quantity.getText().toString().trim();


            double price = Double.parseDouble(Price);
            int qty = Integer.parseInt(Quantity);

            if (product == null){
                product = new Product( Name,Email,price,qty);
                long id = productDao.insert(product);
                if (id>0) Toast.makeText(this, "Product Added", Toast.LENGTH_SHORT).show();
                else Toast.makeText(this,"Insert Failed",Toast.LENGTH_SHORT).show();
            }

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
        });

        btnback.setOnClickListener(v->{
            Intent intent = new Intent(ProductsAdd.this, MainActivity.class);
            startActivity(intent);
        });
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }
}