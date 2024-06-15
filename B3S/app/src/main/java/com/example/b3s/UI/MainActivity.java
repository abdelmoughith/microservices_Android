package com.example.b3s.UI;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b3s.API.Controller;
import com.example.b3s.API.RetrofitClient;
import com.example.b3s.R;
import com.example.b3s.account.Login;
import com.example.b3s.account.SharedPreferencesHelper;
import com.example.b3s.account.Token;
import com.example.b3s.account.User;
import com.example.b3s.models.Product;
import com.example.b3s.models.ViewModel;
import com.example.b3s.scrap.ScrappingActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements OnMenuItemSelectedListener {

    RecyclerView recyclerView;
    EditText search_bar;
    String toCompare = "";
    // menu
    private RecyclerView menuRecyclerView;
    private AdapterMenuSearch menuAdapter;
    private List<String> menuItems;
    private User userMain;
    public static Token token;
    private String accessToken;
    private MutableLiveData<List<Product>> liveData;

    ViewModel viewModel;
    SharedPreferencesHelper sharedPreferencesHelper;
    private Controller controller;
    private TextView b3s;
    ImageButton logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navbar();
        viewModel = new ViewModel(getApplication());
        liveData = new MutableLiveData<>();
        b3s = findViewById(R.id.bs);
        sharedPreferencesHelper = new SharedPreferencesHelper(this);
        // TODO FOR LOG OUT
        //sharedPreferencesHelper.clearTokens();
        accessToken = sharedPreferencesHelper.getAccessToken();

        if (accessToken != null){

            Retrofit retrofit = RetrofitClient.getRetrofit();
            controller = retrofit.create(Controller.class);
            fetchProtectedData("Bearer " +accessToken);


        } else {
            Intent intentTo = new Intent(MainActivity.this, Login.class);
            activityLauncher.launch(intentTo);
        }

        // TODO TEST DIRECT
        /*
        Intent intent = new Intent(getApplicationContext(), ProductViewer.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("ID", 21);
        startActivity(intent);

         */
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(view -> {
            sharedPreferencesHelper.clearTokens();
            Intent intentTo = new Intent(MainActivity.this, Login.class);
            activityLauncher.launch(intentTo);
        });


        recyclerView = findViewById(R.id.recycler);
        search_bar = findViewById(R.id.search_bar);
        menuRecyclerView = findViewById(R.id.search_menu);



        initiliaze_recycler("All");
        // Populate menu items based on search results
        populateMenuItems();



        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String toCompare = editable.toString();
                if (toCompare.isEmpty()){
                    initiliaze_recycler("All");
                }else {
                    Map<String, String> filters = new HashMap<>();
                    filters.put("keyword", toCompare);

                    initiliaze_recycler("All", filters);
                }
            }
        });






    }

    ImageButton homeButton;
    ImageButton scrapButton;
    ImageButton profileButton;
    private void navbar() {
        homeButton = findViewById(R.id.homeButton);
        scrapButton = findViewById(R.id.scrapButton);
        profileButton = findViewById(R.id.profileButton);


        scrapButton.setOnClickListener(view -> {
            Intent intentscrap = new Intent(MainActivity.this, ScrappingActivity.class);
            activityLauncher.launch(intentscrap);
        });

        profileButton.setOnClickListener(view -> {

        });
    }


    private void accossiateUser(User u){
        this.userMain = u;
    }

    private void fetchProtectedData(String token) {
        Call<User> call = controller.getUserInfo(token);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user!=null){
                        accossiateUser(user);
                        // Code update: Run the UI update on the main thread
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (userMain != null) {
                                    //b3s.setText(userMain.getEmail());
                                    //Toast.makeText(MainActivity.this, userMain.getEmail(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "null", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }

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

    ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        // Login successful, retrieve token
                        token = data.getParcelableExtra("token");

                        // Now you can use the token in MainActivity
                        if (token != null) {
                            // Do something with the token
                        }
                    }
                } else {
                    // Handle login failure or cancellation
                }
            });


    @Override
    public void onMenuItemSelected(String menuItem) {
        initiliaze_recycler(menuItem);
    }



    private void initiliaze_recycler(String catg) {
        viewModel.setData(this);
        viewModel.getData().observe(this, entityList -> {
            List<Product> entities;
            if (catg.equals("All")){
                entities = entityList.getProducts();
            } else {
                entities = entityList.getProducts().stream().
                        filter( l -> l.getCategory().equals(catg)).
                        collect(Collectors.toList());
            }

            Adapter adapter = new Adapter(getApplicationContext(), entities);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerView.setHasFixedSize(false);
            recyclerView.setAdapter(adapter);
        });
    }
    private void initiliaze_recycler(String catg, Map<String, String> options) {
        viewModel.setDataQuery(this, options);
        viewModel.getData().observe(this, entityList -> {
            List<Product> entities;
            if (catg.equals("All")){
                entities = entityList.getProducts();
            } else {
                entities = entityList.getProducts().stream().
                        filter( l -> l.getCategory().equals(catg)).
                        collect(Collectors.toList());
            }

            Adapter adapter = new Adapter(getApplicationContext(), entities);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerView.setHasFixedSize(false);
            recyclerView.setAdapter(adapter);
        });
    }


    private void populateMenuItems() {
        menuItems = new ArrayList<>();
        // Simulated search results
        menuItems.clear();
        menuItems.add("All");
        // Set up RecyclerView

            viewModel.setData(this);
            viewModel.getData().observe(this, entityList -> {
                List<String> list = entityList.getProducts().stream()
                        .map(listP -> listP.getCategory()).distinct()
                        .collect(Collectors.toList());
                list.add(0, "All");
                menuAdapter = new AdapterMenuSearch(this, list, this);
                menuRecyclerView.setHorizontalScrollBarEnabled(true);
                menuRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                menuRecyclerView.setAdapter(menuAdapter);


            });


    }

}