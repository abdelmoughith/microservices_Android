package com.example.b3s.API;

import com.example.b3s.models.Product;
import com.example.b3s.models.ProductsList;
import com.example.b3s.models.scrapping.ProductScrapping;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static final String BASE_URL="http://192.168.239.191:1999/";
    //private static final String BASE_URL="http://192.168.1.113:1999/";
    private Controller controller;
    private static APIClient INSTANCE;

    public APIClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        controller = retrofit.create(Controller.class);
    }

    public static APIClient getINSTANCE() {
        if (INSTANCE == null){
            INSTANCE = new APIClient();
        }
        return INSTANCE;
    }
    public Call<ProductsList> getProducts() {
        return controller.getProducts();
    }
    public Call<ProductsList> getProductsq(Map<String, String> options) {
        return controller.getProductsq(options);
    }
    public Call<Product> getOne(Integer id){ return controller.getOne(id); }
    public Call<List<ProductScrapping>> getJumia(String keyword){ return controller.getAllOutside(keyword); }

}

