package com.example.kursova;

import static com.example.kursova.Products.selectedProducts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CreateTrailMix extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trail_mix);

        ListView listViewSelectedProducts = findViewById(R.id.listViewSelectedProducts);
        ProductAdapter productAdapter = new ProductAdapter(getApplicationContext(), selectedProducts);
        listViewSelectedProducts.setAdapter(productAdapter);
    }
}