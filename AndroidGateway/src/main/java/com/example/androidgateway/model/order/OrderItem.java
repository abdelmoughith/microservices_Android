package com.example.androidgateway.model.order;


import lombok.Data;

@Data
public class OrderItem {
    private int product;
    private String name;
    private int quantity;
    private double price;
}