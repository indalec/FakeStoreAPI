package com.example.fakestoreapi2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView detailImageView = findViewById(R.id.detailImageView);

        /*TextView productScreenTitle = findViewById(R.id.productScreenTitle);*/
        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView categoryTextView = findViewById(R.id.categoryTextView);
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        TextView priceTextView = findViewById(R.id.priceTextView);
        TextView ratingTextView = findViewById(R.id.ratingTextView);

        Intent intent = getIntent();


        String imageURL = intent.getStringExtra("image");
        String nameDetail = intent.getStringExtra("title");
        String categoryDetail = intent.getStringExtra("category");
        String descriptionDetail = intent.getStringExtra("description");
        Double priceDetail = intent.getDoubleExtra("price", 0.00);
        String ratingDetail = intent.getStringExtra("rating");


//        try {
//            inputStream = new URL(imageURL).openStream();
//            bitmap = BitmapFactory.decodeStream(inputStream);
//            imageView.setImageBitmap(bitmap);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        new Thread(() -> {

            InputStream inputStream = null;

            try {
                inputStream = new URL(imageURL).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                /*this.*/runOnUiThread(() -> {
                    detailImageView.setImageBitmap(bitmap);
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }).start();

        nameTextView.setText(nameDetail);
        String formattedPrice = String.format ("%.2f", priceDetail);
        priceTextView.setText(formattedPrice + " €");
        categoryTextView.setText(categoryDetail);
        descriptionTextView.setText(descriptionDetail);
        ratingTextView.setText(ratingDetail);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> {
            Intent intentButton = new Intent();

            setResult(0, intentButton);
            finish();
        });

    }
}