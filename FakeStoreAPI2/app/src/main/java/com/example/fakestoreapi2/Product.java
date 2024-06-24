package com.example.fakestoreapi2;

import java.util.Objects;

public class Product {
    //POJO (Plain Old Java Object)

//    private  int id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;
    private double rate;

//    private int rateCount;

    public Product(String title, double price, String description,
                   String category, String image, double rate) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.image = image;
        this.rate = rate;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }

    public double getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", image='" + image + '\'' +
                ", rate=" + rate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return price == product.price && rate == product.rate && Objects.equals(title, product.title) && Objects.equals(description, product.description) && Objects.equals(category, product.category) && Objects.equals(image, product.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, price, description, category, image, rate);
    }
}
