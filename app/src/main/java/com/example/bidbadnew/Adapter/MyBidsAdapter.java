package com.example.bidbadnew.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bidbadnew.Model.MyBid;
import com.example.bidbadnew.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class MyBidsAdapter extends RecyclerView.Adapter<MyBidsAdapter.BidHistoryViewHolder> {

    Context context;
    List<MyBid> heroList;

    public MyBidsAdapter(Context context, List<MyBid> heroList) {
        this.context = context;
        this.heroList = heroList;
    }

    @NonNull
    @Override
    public MyBidsAdapter.BidHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BidHistoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_bids, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyBidsAdapter.BidHistoryViewHolder holder, final int position) {
        Glide.with(context)
                .load(heroList.get(position).getImageUrl())
                .into(holder.bidHistoryImage);
        holder.bidHistoryAmount.setText("₹"+heroList.get(position).getSp());
        holder.bidHistoryTitle.setText(heroList.get(position).getTitle());
        holder.bidHistoryStartDate.setText(heroList.get(position).getEndtime());

        try {
            @SuppressLint("SimpleDateFormat") String dt1 = new SimpleDateFormat("dd MMM yyyy HH:mm").format(new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a").parse(heroList.get(position).getEndtime()));
            holder.bidHistoryStartDate.setText(dt1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        Log.d("Size", heroList.size() + "");
        return heroList.size();
    }

    static class BidHistoryViewHolder extends RecyclerView.ViewHolder {
        ImageView bidHistoryImage;
        TextView bidHistoryTitle;
        TextView bidHistoryStartDate;
        TextView bidHistoryAmount;
        TextView bidHistoryRank;

        BidHistoryViewHolder(View itemView) {
            super(itemView);
            bidHistoryImage = itemView.findViewById(R.id.bidHistoryImage);
            bidHistoryAmount = itemView.findViewById(R.id.bidHistoryAmount);
            bidHistoryTitle = itemView.findViewById(R.id.bidHistoryTitle);
            bidHistoryStartDate = itemView.findViewById(R.id.bidHistoryStartDate);
            bidHistoryRank = itemView.findViewById(R.id.bidHistoryRank);
        }
    }
}