package com.example.bidbadnew.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bidbadnew.Model.Order;
import com.example.bidbadnew.R;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.BidHistoryViewHolder> {

    Context context;
    List<Order> heroList;

    public OrderAdapter(Context context, List<Order> heroList){
        this.context = context;
        this.heroList = heroList;
    }

    @NonNull
    @Override
    public OrderAdapter.BidHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderAdapter.BidHistoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BidHistoryViewHolder holder, int position) {
        holder.orderId.setText("Orderid : " + heroList.get(position).getOid());
        holder.orderTitle.setText(heroList.get(position).getTitle());
        holder.orderPlaceDate.setText(heroList.get(position).getDate());

        Glide.with(context)
                .load(heroList.get(position).getImageUrl())
                .into(holder.orderImage);
    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }

    class BidHistoryViewHolder extends RecyclerView.ViewHolder {
        ImageView orderImage;
        TextView orderTitle;
        TextView orderPlaceDate;
        TextView orderId;

        BidHistoryViewHolder(View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.summaryOrderId);
            orderImage = itemView.findViewById(R.id.summaryOrderImage);
            orderTitle = itemView.findViewById(R.id.summaryOrderTitle);
            //orderAmount = itemView.findViewById(R.id.bidHistoryTitle);
            orderPlaceDate = itemView.findViewById(R.id.summaryOrderDate);
        }
    }
}
