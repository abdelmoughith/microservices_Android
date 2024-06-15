package com.example.b3s.API;

import com.example.b3s.account.Username;
import com.example.b3s.account.Token;
import com.example.b3s.account.User;
import com.example.b3s.models.Product;
import com.example.b3s.models.ProductsList;
import com.example.b3s.models.order.Order;
import com.example.b3s.models.scrapping.ProductScrapping;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface Controller{
    /*@GET("/products")
    Call<List<Product>> getPeople();*/

    @GET("/products")
    Call<ProductsList> getProducts();

    @GET("/productsq")
    Call<ProductsList> getProductsq(@QueryMap Map<String, String> options);

    @GET("products/{id}")
    Call<Product> getOne(@Path("id") Integer id);

    @POST("/api/register")
    Call<ResponseBody> register(@Body User user);

    @POST("/login")
    Call<Token> login(@Body Username username);

    @GET("/userinfo")
    Call<User> getUserInfo(@Header("Authorization") String authorizationHeader);


    /*@GET("api/v1/categories")
    Call<List<Entity>> getCategory(@Query("search") String category);

    /*@GET("api/v1/categories")
    Call<List<Product>> getCategories();*/

    @POST("/order/add")
    Call<String> addOrder(@Header("Authorization") String authorizationHeader,
                          @Body Order order);

    @GET("get_outside")
    Call<List<ProductScrapping>> getAllOutside(@Query("q") String keyword);
}
