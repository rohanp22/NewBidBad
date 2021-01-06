package com.example.bidbadnew.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bidbadnew.Model.WishListItem;
import com.example.bidbadnew.R;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListViewHolder> {

    List<WishListItem> wishListItems;
    Context context;

    public WishListAdapter(List<WishListItem> wishListItems, Context context) {
        this.wishListItems = wishListItems;
        this.context = context;
    }

    @NonNull
    @Override
    public WishListAdapter.WishListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_bids, parent, false);
        return new WishListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.WishListViewHolder holder, int position) {
        Glide.with(context)
                .load(wishListItems.get(position).getImageUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return wishListItems.size();
    }

    public class WishListViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public WishListViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.bidHistoryImage);
        }
    }
}
