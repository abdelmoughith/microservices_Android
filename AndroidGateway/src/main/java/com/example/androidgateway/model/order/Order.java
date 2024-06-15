package com.example.androidgateway.model.order;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private String city;
    private String zip_code;
    private String street;
    private String country;
    private String phone_no;
    private List<OrderItem> order_Items;

}