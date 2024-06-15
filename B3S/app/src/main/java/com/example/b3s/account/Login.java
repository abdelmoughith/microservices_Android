package com.example.b3s.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.b3s.API.Controller;
import com.example.b3s.R;
import com.example.b3s.UI.MainActivity;
import com.example.b3s.models.ViewModel;
import com.example.b3s.API.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    Button loginBtn;

    private Controller controller;

    private SharedPreferencesHelper sharedPreferencesHelper;
    ViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Retrofit retrofit = RetrofitClient.getRetrofit();
        controller = retrofit.create(Controller.class);

        sharedPreferencesHelper = new SharedPreferencesHelper(this);
        sharedPreferencesHelper.clearTokens();
        checkUserLoginStatus();




        emailEditText = findViewById(R.id.emailL);
        passwordEditText = findViewById(R.id.passwordL);
        loginBtn = findViewById(R.id.loginBtn);


        emailEditText.setText("yyyy@gmail.com");
        passwordEditText.setText("123456789");
        //logging(this);

        loginBtn.setOnClickListener(view -> {
            //finish();
            logging(this);

        });




    }
    private void checkUserLoginStatus() {
        String accessToken = sharedPreferencesHelper.getAccessToken();
        if (accessToken != null) {
            // User is already logged in, fetch user data and navigate to MainActivity
            fetchProtectedData( "Bearer " + accessToken);
        }
    }

    private void logging(Context context) {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Assuming you have a ViewModel instance to access the ViewModel methods

        Username loginRequest = new Username(email, password);
        Call<Token> call = controller.login(loginRequest);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    // save in shared preferences
                    sharedPreferencesHelper.saveTokens(response.body().getAccess(), response.body().getRefresh());

                    //Toast.makeText(getApplicationContext(), response.body().getAccess(), Toast.LENGTH_SHORT).show();
                    String token = "Bearer " + response.body().getAccess();
                    fetchProtectedData(token);
                } else {
                    Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchProtectedData(String token) {
        Call<User> call = controller.getUserInfo(token);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user!=null)
                        navigateToMainActivity(user, token);
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigateToMainActivity(User user, String tk) {
        Intent intent = new Intent(Login.this, MainActivity.class);
        intent.putExtra("USER_DATA", user);
        intent.putExtra("USER_TOKEN", tk);
        startActivity(intent);
        finish();
    }

}