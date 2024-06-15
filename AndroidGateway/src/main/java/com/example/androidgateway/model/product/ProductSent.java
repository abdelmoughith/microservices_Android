package com.example.androidgateway.model.product;

import lombok.Data;

@Data
public class ProductSent {
    private String name;
    private String description;
    private String price;
    private String brand;
    private String category;
    private String ratings;
    private int stock;
}
