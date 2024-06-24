package com.example.fakestoreapi2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    Spinner spinner;
    RecyclerView listOfItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });


        listOfItems = findViewById(R.id.listOfItems);
        //setting linear layout Manager: display one element after the other vertically
        listOfItems.setLayoutManager(new LinearLayoutManager(this));
        spinner = findViewById(R.id.spinner);
//        new Thread(() -> {
//            List<Product> products = NetworkService.getProduct();
//
//            Log.d("product list", products.size() + ""); // to display "things/values" in the LogCat terminal
//            runOnUiThread(() -> {
////                listOfItems.setAdapter(new FakeStoreAdapter(products));
//                updateView(products);
//            });
//        }).start();
/*        String[] categories = {"-- All categories --", "electronics", "jewelery", "men's clothing", "women's clothing"};
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> productArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categories);
        spinner.setAdapter(productArrayAdapter);*/

        setupSpinner();
        fetchAllProducts();

    }

    private void setupSpinner() {
        new Thread(() -> {
            List<String> categories = NetworkService.getCategories();
            // Adding the default "-- All categories --" option at the beginning
            categories.add(0, "-- All categories --");

            runOnUiThread(() -> {
                ArrayAdapter<String> productArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categories);
                spinner.setAdapter(productArrayAdapter);

                // Adding the listener for item selection
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        fetchProductsByCategory(categories.get(position), position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                // Fetch and displaying all products initially

            });
        }).start();
    }

    private void fetchProductsByCategory(String category, int position) {
        new Thread(() -> {
            List<Product> products;
            if (position == 0) {
                // If the first item is selected, fetch all products
                products = NetworkService.getProducts();
            } else {
                // Fetching products by selected category
                products = NetworkService.getProductByCategory(category);
            }

            // Updating the view on the UI thread
            runOnUiThread(() -> {
                updateView(products);
            });
        }).start();
    }

    private void fetchAllProducts() {
        new Thread(() -> {
            List<Product> products = NetworkService.getProducts();
            runOnUiThread(() -> {
                updateView(products);
            });
        }).start();
    }

    private void updateView(List<Product> products) {
        FakeStoreAdapter fakeStoreAdapter = new FakeStoreAdapter(products);

        fakeStoreAdapter.setOnItemClickListener((view, position) -> {
            Toast.makeText(
                    this,
                    "Hello Hello Hello" + position,
                    Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, ProductDetailActivity.class);
            intent.putExtra("title", products.get(position).getTitle());
            intent.putExtra("category", products.get(position).getCategory());
            intent.putExtra("description", products.get(position).getDescription());
            intent.putExtra("price", products.get(position).getPrice());
            intent.putExtra("rating", products.get(position).getRate());
            intent.putExtra("image", products.get(position).getImage());


            startActivity(intent);
        });

        listOfItems.setAdapter(fakeStoreAdapter);
    }
}