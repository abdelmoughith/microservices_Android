package com.example.b3s.models.order;


import java.util.List;

public class Order {
    private String city;
    private String zip_code;
    private String street;
    private String country;
    private String phone_no;
    private List<OrderItem> order_Items;

    public Order() {
    }

    public Order(List<OrderItem> order_Items) {
        this.city = "Marrakech";
        this.zip_code = "12345";
        this.street = "EMSI centre";
        this.country = "Morocoo";
        this.phone_no = "0644331202";
        this.order_Items = order_Items;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public List<OrderItem> getOrder_Items() {
        return order_Items;
    }

    public void setOrder_Items(List<OrderItem> order_Items) {
        this.order_Items = order_Items;
    }
}