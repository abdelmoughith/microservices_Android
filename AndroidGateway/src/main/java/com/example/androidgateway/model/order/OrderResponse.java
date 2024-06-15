package com.example.androidgateway.model.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {
    private Integer id;
    private List<OrderItem> orderItems;
    private String city;
    private String zipCode;
    private String street;
    private String state;
    private String country;
    private String phoneNo;
    private Double totalAmount;
    private String paymentStatus;
    private String paymentMode;
    private String status;
    private String createAt;
    private Integer user;
}
