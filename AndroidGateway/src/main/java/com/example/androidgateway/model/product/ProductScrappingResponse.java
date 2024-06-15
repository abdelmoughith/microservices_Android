package com.example.androidgateway.model.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductScrappingResponse {
    private List<ProductScrapping> products;
}
