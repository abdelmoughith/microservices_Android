package com.example.b3s.models.order;



public class OrderItem {
    private int product;
    private String name;
    private int quantity;
    private double price;

    public OrderItem() {
    }

    public OrderItem(int product, String name, int quantity, double price) {
        this.product = product;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}