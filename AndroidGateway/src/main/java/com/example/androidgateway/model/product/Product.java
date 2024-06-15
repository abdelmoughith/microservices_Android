package com.example.androidgateway.model.product;


import lombok.Data;

import java.util.List;

@Data
public class Product {
    private int id;
    private List<String> reviews;
    private String name;
    private String image;
    private String description;
    private String price;
    private String brand;
    private String category;
    private String ratings;
    private int stock;
    private String createAt;
    private int user;
}
