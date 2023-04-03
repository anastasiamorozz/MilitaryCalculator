package com.example.kursova;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Products extends AppCompatActivity {

    private ListView productsList;
    private ProductAdapter productAdapter;
    private ArrayList<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        productsList = findViewById(R.id.listView);
        productAdapter = new ProductAdapter(getApplicationContext(), productList);
        productsList.setAdapter(productAdapter);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                productAdapter.getFilter().filter(newText);
                return false;
            }
        });

        Button exitButton = findViewById(R.id.button3);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Products.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button continueButton = findViewById(R.id.button4);
        continueButton.setOnClickListener(v -> {
            ArrayList<Product> selectedProducts = new ArrayList<>();
            for (Product product : productList) {
                if (product.isChecked()) {
                    selectedProducts.add(product);
                }
            }
            // Do something with the selected products
        });

        Product product1 = new Product("Свинина сушена", "філе", new MRE("Свинина сушена", 316, 12, 16, 42), 60);
        Product product2 = new Product("Пластівці горохові", "пластівці", new MRE("Пластівці горохові", 350, 23, 2, 60), 100);
        Product product3 = new Product("Сушена морква", "овочі", new MRE("Сушена морква", 68, 30, 1, 4), 20);
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
    }
}
