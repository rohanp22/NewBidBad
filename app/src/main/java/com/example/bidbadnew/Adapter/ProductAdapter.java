package com.example.bidbadnew.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bidbadnew.Model.Current_Product;
import com.example.bidbadnew.Others.CenterZoomLayoutManager;
import com.example.bidbadnew.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductAdapterViewHolder> {

    Context context;
    RecyclerView recyclerView;
    List<Current_Product> heroList = new ArrayList<>();
    CenterZoomLayoutManager centerZoomLayoutManager;


    public ProductAdapter(Context context, List<Current_Product> heroList, RecyclerView recyclerView, CenterZoomLayoutManager centerZoomLayoutManager) {
        this.context = context;
        this.heroList = heroList;
        this.recyclerView = recyclerView;
        this.centerZoomLayoutManager = centerZoomLayoutManager;
    }

    @NonNull
    @Override
    public ProductAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout_new, parent, false);
        context = view.getContext();
        return new ProductAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapterViewHolder holder, int position) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if(position == 0){
            holder.materialCardView.setStrokeColor(context.getResources().getColor(R.color.colorSecondary));
        }

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ProductAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView mrp;
        TextView time;
        MaterialCardView materialCardView;
        ConstraintLayout constraintLayout;

        public ProductAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
            mrp = itemView.findViewById(R.id.mrp);
            time = itemView.findViewById(R.id.time);
            materialCardView = itemView.findViewById(R.id.cardView);
        }
    }
}
