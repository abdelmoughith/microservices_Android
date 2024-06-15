package com.example.b3s.scrap;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b3s.API.Controller;
import com.example.b3s.R;
import com.example.b3s.UI.Adapter;
import com.example.b3s.UI.AdapterMenuSearch;
import com.example.b3s.UI.AdapterScrapping;
import com.example.b3s.UI.OnMenuItemSelectedListener;
import com.example.b3s.account.SharedPreferencesHelper;
import com.example.b3s.account.Token;
import com.example.b3s.account.User;
import com.example.b3s.models.Product;
import com.example.b3s.models.ViewModel;
import com.example.b3s.models.scrapping.ProductScrapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScrappingActivity extends AppCompatActivity implements OnMenuItemSelectedListener {

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
        setContentView(R.layout.activity_scrapping);
        viewModel = new ViewModel(getApplication());

        recyclerView = findViewById(R.id.recycler);
        search_bar = findViewById(R.id.search_bar);
        menuRecyclerView = findViewById(R.id.search_menu);
        initiliaze_recycler("All", toCompare);
        populateMenuItems(toCompare);


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
                    initiliaze_recycler("All", "");
                }else {

                    initiliaze_recycler("All", toCompare);
                    populateMenuItems(toCompare);
                }
            }
        });



    }

    private void initiliaze_recycler(String catg, String keyword) {
        viewModel.setScrapped(this, keyword);

        viewModel.getScrapped().observe(this, entityList -> {
            List<ProductScrapping> entities;

            if (catg.equals("All")){
                entities = entityList;
            } else {
                entities = entityList.stream().
                        filter( l -> l.getBoutique().equals(catg)).
                        collect(Collectors.toList());
            }



            AdapterScrapping adapter = new AdapterScrapping(getApplicationContext(), entities);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerView.setHasFixedSize(false);
            recyclerView.setAdapter(adapter);
        });
    }



    private void populateMenuItems(String q) {
        menuItems = new ArrayList<>();
        // Simulated search results
        menuItems.clear();
        menuItems.add("All");
        // Set up RecyclerView

        viewModel.setScrapped(this, q);
        viewModel.getScrapped().observe(this, entityList -> {
            List<String> list = entityList.stream()
                    .map(listP -> listP.getBoutique()).distinct()
                    .collect(Collectors.toList());
            list.add(0, "All");
            menuAdapter = new AdapterMenuSearch(this, list, this);
            menuRecyclerView.setHorizontalScrollBarEnabled(true);
            menuRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            menuRecyclerView.setAdapter(menuAdapter);


        });


    }

    @Override
    public void onMenuItemSelected(String menuItem) {
        initiliaze_recycler("All", menuItem);
    }
}