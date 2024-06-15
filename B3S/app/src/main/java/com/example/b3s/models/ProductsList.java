package com.example.b3s.models;

import java.util.List;

public class ProductsList {
    private List<Product> products;
    private int perPage;
    private int count;

    public ProductsList(List<Product> products, int perPage, int count) {
        this.products = products;
        this.perPage = perPage;
        this.count = count;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
