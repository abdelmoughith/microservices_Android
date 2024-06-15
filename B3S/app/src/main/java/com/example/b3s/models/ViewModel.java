package com.example.b3s.models;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.b3s.API.APIClient;
import com.example.b3s.API.Controller;
import com.example.b3s.models.scrapping.ProductScrapping;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewModel extends AndroidViewModel {
    private Controller controller;
    private MutableLiveData<ProductsList> data = new MutableLiveData<>();
    private MutableLiveData<Product> one = new MutableLiveData<>();
    private MutableLiveData<List<ProductScrapping>> scrapped = new MutableLiveData<>();


    public ViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<ProductScrapping>> getScrapped() {
        return scrapped;
    }

    public void setScrapped(Context context,String keyword) {
        APIClient.getINSTANCE().getJumia(keyword).enqueue(new Callback<List<ProductScrapping>>() {
            @Override
            public void onResponse(Call<List<ProductScrapping>> call, Response<List<ProductScrapping>> response) {
                if (response.isSuccessful()){
                    scrapped.setValue(response.body());
                } else {
                    Toast.makeText(context, "404", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductScrapping>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("errr", t.getMessage());
            }
        });
    }

    public void setDataQuery(Context context, Map<String, String> options) {
        APIClient.getINSTANCE().getProductsq(options).enqueue(new Callback<ProductsList>() {
            @Override
            public void onResponse(Call<ProductsList> call, Response<ProductsList> response) {
                if (response.isSuccessful()){
                    data.setValue(response.body());
                    Toast.makeText(context, "200OK", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "404", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductsList> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void setData(Context context) {
        APIClient.getINSTANCE().getProducts().enqueue(new Callback<ProductsList>() {
            @Override
            public void onResponse(Call<ProductsList> call, Response<ProductsList> response) {
                if (response.isSuccessful()){
                    data.setValue(response.body());
                    Toast.makeText(context, "200OK", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "404", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductsList> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public MutableLiveData<ProductsList> getData() {
        return data;
    }

    public MutableLiveData<Product> getOne() {
        return one;
    }

    public void setOne(Context context, Integer ID) {
        APIClient.getINSTANCE().getOne(ID).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()){
                    one.setValue(response.body());
                    Toast.makeText(context, "200OK", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "404", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(context, "500", Toast.LENGTH_SHORT).show();
            }
        });
    }



    /*

    private MutableLiveData<ResponseBody> registered = new MutableLiveData<>();
    private MutableLiveData<User> user = new MutableLiveData<>();
    private Token token;
    private LoginCallback loginCallback;

    public ViewModel(Application application) {
        super(application);
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public void setUser(Context context, String accessToken) {
        APIClient.getINSTANCE().getUserInfo(accessToken).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    user.setValue(response.body());
                    Toast.makeText(context, user.getValue().getEmail(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "token error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, "NO Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setToken(Context context, String username, String password, LoginCallback callback) {
        this.loginCallback = callback; // Assign the callback
        // Make a network request to create the user account
        APIClient.getINSTANCE().login(new Username(username, password)).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    token = response.body();
                    Toast.makeText(context, "Logged in", Toast.LENGTH_SHORT).show();
                    if (loginCallback != null) {
                        loginCallback.onLoginSuccess(token); // Invoke the callback on success
                    }
                } else {
                    //Toast.makeText(context, "Failed to connect account. Please try again.", Toast.LENGTH_SHORT).show();
                    if (loginCallback != null) {
                      //  loginCallback.onLoginFailure("Failed to connect account. Please try again."); // Invoke the callback on failure
                    }
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(context, "Network error. Please check your internet connection.", Toast.LENGTH_SHORT).show();
                if (loginCallback != null) {
                    loginCallback.onLoginFailure("Network error. Please check your internet connection."); // Invoke the callback on failure
                }
            }
        });
    }
    public interface userCallBACK {
        void onLoginSuccess(User user);
        void onLoginFailure(String errorMessage);
    }

    public interface LoginCallback {
        void onLoginSuccess(Token token);
        void onLoginFailure(String errorMessage);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////

    public void setRegistred(Context context, User user) {
        // Make a network request to create the user account
        APIClient.getINSTANCE().register(user).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    //Toast.makeText(context, "Account created successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(context, "Failed to create account. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Toast.makeText(context, "Network error. Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public Token getToken() {
        return token;
    }

    public MutableLiveData<List<Product>> getData() {
        return data;
    }

    public void setData(Context context) {
        APIClient.getINSTANCE().getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()){
                    data.setValue(response.body());
                    //Toast.makeText(context, "200OK", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(context, "404", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                //Toast.makeText(context, "NO Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public MutableLiveData<ResponseBody> getRegistred() {
        return this.registered;
    }

*/
}
