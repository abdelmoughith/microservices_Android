package com.example.b3s.UI;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b3s.API.Controller;
import com.example.b3s.API.RetrofitClient;
import com.example.b3s.R;
import com.example.b3s.account.SharedPreferencesHelper;
import com.example.b3s.account.Token;
import com.example.b3s.account.Username;
import com.example.b3s.models.Product;
import com.example.b3s.models.ViewModel;
import com.example.b3s.models.order.Order;
import com.example.b3s.models.order.OrderItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductViewer extends AppCompatActivity {
    ImageView imageView;
    TextView brand, title, description, stock, price, discountPercentage, rating, category;
    TextView tvQuantity;
    Button btnMinus, btnPlus, add_to_cart;
    ImageButton like;
    int quantityOfProduct;
    boolean liked;
    Controller controller;
    SharedPreferencesHelper sharedPreferencesHelper;
    String accessToken;
    OrderItem orderItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_viewer);
        quantityOfProduct = 1;
        liked = false;
        intializiar();
        intializiarListenner();
        Retrofit retrofit = RetrofitClient.getRetrofit();
        controller = retrofit.create(Controller.class);
        sharedPreferencesHelper = new SharedPreferencesHelper(this);

        accessToken = sharedPreferencesHelper.getAccessToken();
        orderItem = new OrderItem();

        ViewModel viewModel = new ViewModel(getApplication());
        viewModel.setOne(this, getIntent().getExtras().getInt("ID"));

        viewModel.getOne().observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                //order
                orderItem.setProduct(product.getId());
                orderItem.setName(product.getName());
                orderItem.setPrice(Double.valueOf(product.getPrice()));
                orderItem.setQuantity(1);
                // display
                title.setText( product.getName() );
                brand.setText("Brand : " + product.getBrand());
                description.setText("Description :\n" + product.getDescription());
                stock.setText("Stock : " + product.getStock());
                price.setText(product.getPrice() + " $");
                discountPercentage.setText("DiscountPercentage : " + product.getBrand());
                rating.setText("Rating : " + product.getRatings());
                category.setText("Category : " + product.getCategory());
                Picasso.get().load(product.getImage()).into(imageView);
                //Adapter2 adapter2 = new Adapter2(getApplicationContext(), product.getImages());
                //recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                //recyclerView.setAdapter(adapter2);
            }
        });
    }

    private void intializiarListenner() {
        btnPlus.setOnClickListener(view -> {
            quantityOfProduct++;
            tvQuantity.setText(String.valueOf(quantityOfProduct));
        });
        btnMinus.setOnClickListener(view -> {
            if ( !(quantityOfProduct == 1) ){
                quantityOfProduct--;
                tvQuantity.setText(String.valueOf(quantityOfProduct));
            }

        });
        like.setOnClickListener(view -> {
            if (liked){
                liked = false;
                like.setBackgroundResource(R.drawable.counter);
                like.setColorFilter(getColor( R.color.orangedarker), PorterDuff.Mode.SRC_IN);
            } else {
                liked = true;
                like.setBackgroundResource(R.drawable.counter_selected);
                like.setColorFilter(getColor( R.color.white), PorterDuff.Mode.SRC_IN);

            }
        });
        add_to_cart.setOnClickListener(view -> {
            List<OrderItem> orderItemList = new ArrayList<>();
            orderItemList.add(orderItem);
            Order myOrder = new Order(orderItemList);
            makeOrder(getApplicationContext(), "Bearer " + accessToken, myOrder);
        });
    }

    private void intializiar() {
        //increment qty
        tvQuantity = findViewById(R.id.tvQuantity);
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        like = findViewById(R.id.like);
        add_to_cart = findViewById(R.id.add_to_cart);
        //
        brand = findViewById(R.id.brand);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        stock = findViewById(R.id.stock);
        price = findViewById(R.id.price);
        discountPercentage = findViewById(R.id.discountPercentage);
        rating = findViewById(R.id.rating);
        category = findViewById(R.id.category);
        imageView = findViewById(R.id.images_preview);
        // setting
    }
    private void makeOrder(Context context, String JWT, Order order) {


        Call<String> call = controller.addOrder(JWT, order);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    // save in shared preferences
                    Toast.makeText(context, "Order made successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Unable to make order", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}