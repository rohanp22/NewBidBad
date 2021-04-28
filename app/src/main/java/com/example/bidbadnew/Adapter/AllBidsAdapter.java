package com.example.bidbadnew.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bidbadnew.Model.PastProducts;
import com.example.bidbadnew.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AllBidsAdapter extends RecyclerView.Adapter<AllBidsAdapter.BidHistoryViewHolder> {

    Context context;
    ArrayList<PastProducts> heroList;
    AllBidsAdapterListener allBidsAdapterListener;

    public AllBidsAdapter(Context context, ArrayList<PastProducts> heroList, AllBidsAdapterListener allBidsAdapterListener) {
        this.context = context;
        this.heroList = heroList;
        this.allBidsAdapterListener = allBidsAdapterListener;
    }

    @NonNull
    @Override
    public AllBidsAdapter.BidHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BidHistoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bid_history, parent, false));
    }

    public interface AllBidsAdapterListener{
        public void onItemClickListener(PastProducts pastProduct);
    }

    @SuppressLint("SetTextfI18n")
    @Override
    public void onBindViewHolder(@NonNull BidHistoryViewHolder holder, int position) {
        holder.bidHistoryTitle.setText(heroList.get(position).getTitle());
        try {
            @SuppressLint("SimpleDateFormat") String dt1 = new SimpleDateFormat("dd MMM yyyy HH:mm").format(new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a").parse(heroList.get(position).getEnd_date()));
            holder.bidHistoryStartDate.setText(dt1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.bidHistoryRank.setText(heroList.get(position).getWinner());
        holder.bidHistoryAmount.setText("₹"+ heroList.get(position).getBidamount());
        Glide.with(context)
                .load(heroList.get(position).getImage_url())
                .into(holder.bidHistoryImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allBidsAdapterListener.onItemClickListener(heroList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
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