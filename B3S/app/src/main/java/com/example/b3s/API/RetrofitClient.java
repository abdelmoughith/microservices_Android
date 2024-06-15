package com.example.b3s.API;


import com.example.b3s.account.Username;
import com.example.b3s.account.Token;
import com.example.b3s.account.User;
import com.example.b3s.models.Product;
import com.example.b3s.models.ProductsList;
import com.example.b3s.models.order.Order;

import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static final String BASE_URL="http://192.168.239.191:1999/";
    //public static final String BASE_URL="http://192.168.1.113:1999/";
    private Controller controller;
    public static Retrofit retrofit;

    /*
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


     */

    public RetrofitClient() {
        this.controller = retrofit.create(Controller.class);
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    public Call<ProductsList> getProducts() {
        return controller.getProducts();
    }
    public Call<ResponseBody> register(User user) {
        return controller.register(user);
    }
    public Call<Token> login(Username username) {
        return controller.login(username);
    }
    public Call<User> getUserInfo(String tokenAccess) {
        return controller.getUserInfo(tokenAccess);
    }
    public Call<String> addOrder(String JWT, Order order) {
        return controller.addOrder(JWT, order);
    }

}
