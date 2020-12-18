package com.example.bidbadnew.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bidbadnew.R;

import java.util.ArrayList;

public class ProductDetailsAdapter extends RecyclerView.Adapter<ProductDetailsAdapter.ProductDetailsViewHolder> {

    Context context;
    ArrayList<String> details;

    public ProductDetailsAdapter(ArrayList<String> details, Context context){
        this.details = details;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductDetailsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bullet_points, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductDetailsViewHolder holder, int position) {
        holder.text.setText(details.get(position));
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class ProductDetailsViewHolder extends RecyclerView.ViewHolder{

        TextView text;

        public ProductDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
        }
    }
}
