package com.robiul.firstapk.products;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.robiul.firstapk.R;
import com.robiul.firstapk.dbUtil.ProductAdapter;
import com.robiul.firstapk.dbUtil.ProductDao;
import com.robiul.firstapk.products.entity.Product;

import java.util.List;

public class ProductList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private ProductDao productDao;
    private FloatingActionButton fabAdd;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_list);


        SharedPreferences sharedPreferences = getSharedPreferences("RobiulApps", MODE_PRIVATE);

        String username = sharedPreferences.getString("username", "Guest");
        String password = sharedPreferences.getString("password", null);
        boolean loggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        Toast.makeText(this,"User: " + username + " , Password: " + password, Toast.LENGTH_SHORT).show();



        recyclerView = findViewById(R.id.recyclerView);
        fabAdd = findViewById(R.id.fabAdd);
        productDao = new ProductDao(this);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fabAdd.setOnClickListener(v->{
            Intent intent = new Intent(ProductList.this,ProductsAdd.class);
            startActivity(intent);
        });


//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }

    private void loadProducts(){
        productList = productDao.getAllProducts();
        productAdapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(productAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProducts();
    }
}