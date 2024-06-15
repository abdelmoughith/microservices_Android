package com.example.androidgateway.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductScrapping {
    private String old;
    private String reduction;
    private String boutique;
    @JsonProperty("new")
    private String newprice;
    private String image;
    private String name;
    private String link;

}
