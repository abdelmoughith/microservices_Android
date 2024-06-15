package com.example.androidgateway.model.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductsList {
    private List<Product> products;
    private int perPage;
    private int count;
}
