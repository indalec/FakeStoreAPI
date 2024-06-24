package com.example.fakestoreapi2;

import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkService {


    public static List<Product> getProducts() {




        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://fakestoreapi.com/products")
                .build();

        try {

            Response response = client.newCall(request).execute();
            ObjectMapper mapper = new ObjectMapper();

            JsonNode results = mapper.readTree(response.body().string());
            List<Product> products = new ArrayList<>();

            for (JsonNode result : results) {
                Product product = new Product(
                        result.get("title").asText(),
                        result.get("price").asDouble(),
                        result.get("description").asText(),
                        result.get("category").asText(),
                        result.get("image").asText(),
                        result.get("rating").get("rate").asDouble()

                );

                products.add(product);
            }

            return products;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static List<Product> getProductByCategory(String category) {
        ObjectMapper objectMapper = new ObjectMapper();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://fakestoreapi.com/products/category/"+category)
                .build();

        Log.d("test", "Connecting to the Internet!!!!");
        try {
            Log.d("test", "Connecting to the Internet!!!!");

            Response response = client.newCall(request).execute();
            JsonNode root = objectMapper.readTree(response.body().string());
//            JsonNode results = root.get("results");

            List<Product> products = new ArrayList<>();

            for (JsonNode result : root) {
                Product product = new Product(
                        result.get("title").asText(),
                        result.get("price").asDouble(),
                        result.get("description").asText(),
                        result.get("category").asText(),
                        result.get("image").asText(),
                        result.get("rating").get("rate").asDouble()
                );
                products.add(product);
            }

            return products;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static List<String> getCategories() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://fakestoreapi.com/products/categories")
                .build();

        try {
            Response response = client.newCall(request).execute();
            ObjectMapper mapper = new ObjectMapper();

            JsonNode results = mapper.readTree(response.body().string());
            List<String> categories = new ArrayList<>();

            for (JsonNode result : results) {
                categories.add(result.asText());
            }

            return categories;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
