package com.example.kursova;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Products extends AppCompatActivity {

    private ListView productsList;
    private ProductAdapter productAdapter;
    private ArrayList<Product> productList = new ArrayList<>();
    public static ArrayList<Product> selectedProducts = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        productList.add(new Product("Свинина сушена", "Філе", new MRE(316, 12, 16, 42), 60));
        productList.add(new Product("Пластівці горохові", "Пластівці", new MRE(350, 23, 2, 60), 100));
        productList.add(new Product("Сушена морква", "Овочі", new MRE(68, 30, 1, 4), 20));
        productList.add(new Product("Локшина яєчна", "Бакалія", new MRE(350, 12.4, 0.9, 75.6), 100));
        productList.add(new Product("Сушений сир твердий", "Сир", new MRE(211, 16.7, 15, 1), 50));
        productList.add(new Product("Сухі вершки", "Молочна продукція", new MRE(102, 2.9, 11.5, 25.1), 20));

        productsList = findViewById(R.id.listView);
        productAdapter = new ProductAdapter(getApplicationContext(), productList);
        productsList.setAdapter(productAdapter);

        Spinner spinner = findViewById(R.id.spinner);
        String[] sortingOptions = {"Сортувати за А-Я", "Сортувати за Я-А", "Сортувати за вагою", "Сортувати за калорійністю"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sortingOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedOption = adapterView.getItemAtPosition(i).toString();
                if(selectedOption.equals("Сортувати за А-Я")){
                    // Відсортувати елементи productList в алфавітному порядку від "А" до "Я"
                    Collections.sort(productList, new Comparator<Product>() {
                        @Override
                        public int compare(Product p1, Product p2) {
                            return p1.getName().compareTo(p2.getName());
                        }
                    });
                }
                if (selectedOption.equals("Сортувати за Я-А")) {
                    // Відсортувати елементи productList в алфавітному порядку від "Я" до "А"
                    Collections.sort(productList, new Comparator<Product>() {
                        @Override
                        public int compare(Product p1, Product p2) {
                            return p2.getName().compareTo(p1.getName());
                        }
                    });
                }
                if (selectedOption.equals("Сортувати за вагою")){
                    Collections.sort(productList, new Comparator<Product>() {
                        @Override
                        public int compare(Product p1, Product p2) {
                            return Double.compare(p1.getWeight(), p2.getWeight());
                        }
                    });
                }
                if (selectedOption.equals("Сортувати за калорійністю")){
                    Collections.sort(productList, new Comparator<Product>() {
                        @Override
                        public int compare(Product p1, Product p2) {
                            return Double.compare(p1.getMre().getCalories(), p2.getMre().getCalories());
                        }
                    });
                }
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
            for (Product product : productList) {
                if (product.isChecked()) {
                    selectedProducts.add(product);
                }
            }
            Intent intent = new Intent(Products.this, CreateTrailMix.class);
            startActivity(intent);
        });


    }

}
