package com.example.b3s.UI;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b3s.R;
import com.example.b3s.models.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.VHolder> {

    private Context context;
    private List<Product> list;
    private int selectedItem = 0;


    public Adapter(Context context, List<Product> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item ,parent , false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHolder holder, int position) {
        Picasso.get().load(list.get(position).getImage()).into(holder.overview_image);
        holder.brand.setText(list.get(position).getBrand());
        holder.title.setText(list.get(position).getName());
        holder.price.setText(list.get(position).getPrice() + " $");
        holder.brand.setText(list.get(position).getBrand());
        holder.layout_over.setOnClickListener(view -> {
            Intent intent = new Intent(context, ProductViewer.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("ID", list.get(position).getId());
            intent.putExtra("PRICE", Double.valueOf(list.get(position).getPrice()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class VHolder extends RecyclerView.ViewHolder{
        ImageView overview_image;
        TextView brand, title, price;
        RelativeLayout layout_over;
        public VHolder(@NonNull View itemView) {
            super(itemView);

            layout_over = itemView.findViewById(R.id.layout_over);
            overview_image = itemView.findViewById(R.id.overview_image);
            brand = itemView.findViewById(R.id.brand);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);

        }
    }
}

