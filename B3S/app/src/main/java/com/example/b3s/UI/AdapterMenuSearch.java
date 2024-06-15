package com.example.b3s.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b3s.R;

import java.util.List;

public class AdapterMenuSearch extends RecyclerView.Adapter<AdapterMenuSearch.ViewHolder> {

    private List<String> menuItems;
    private Context context;
    private int selectedItem = 0;
    private OnMenuItemSelectedListener listener;

    public AdapterMenuSearch(Context context, List<String> menuItems, OnMenuItemSelectedListener listener) {
        this.context = context;
        this.menuItems = menuItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String menuItem = menuItems.get(position);
        holder.menuItemTextView.setText(menuItem);

        if (position == selectedItem){
            holder.constraint_menu_item.setBackgroundResource(R.drawable.menu_item_background_focus);
            holder.menuItemTextView.setTextColor(context.getColor(R.color.white));
        }else {
            holder.constraint_menu_item.setBackgroundResource(R.drawable.menu_item_background);
            holder.menuItemTextView.setTextColor(context.getColor(R.color.black));
        }
        // Add click listener to each item
        holder.itemView.setOnClickListener(v -> {
            // Update selected item
            int clickedPosition = holder.getAdapterPosition();
            selectedItem = clickedPosition;
            String selectedItemValue = menuItems.get(clickedPosition);
            listener.onMenuItemSelected(selectedItemValue);
            //Log.e("a", Communication.catg);
            // Notify adapter that data set has changed
            notifyDataSetChanged();
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView menuItemTextView;
        public ConstraintLayout constraint_menu_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuItemTextView = itemView.findViewById(R.id.value);
            constraint_menu_item = itemView.findViewById(R.id.constraint_menu_item);
        }
    }
}

