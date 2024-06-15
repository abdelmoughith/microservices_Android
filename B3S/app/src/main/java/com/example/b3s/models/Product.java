package com.example.b3s.models;

import java.util.List;

public class Product {
    private Integer id;
    private String name;
    private String image;
    private String description;
    private String brand;
    private String price;
    private String ratings;
    private Integer stock;
    private String createAt;
    private String category;
    private Integer user;
    private List<String> reviews;

    public Product() {
    }

    public Product(Integer id, String name, String image, String description, String brand, String price, String ratings, Integer stock, String createAt, String category, Integer user, List<String> reviews) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.brand = brand;
        this.price = price;
        this.ratings = ratings;
        this.stock = stock;
        this.createAt = createAt;
        this.category = category;
        this.user = user;
        this.reviews = reviews;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }
}
