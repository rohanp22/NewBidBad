package com.example.bidbadnew.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bidbadnew.Fragments.MyBidsFragment;
import com.example.bidbadnew.Model.MyBid;
import com.example.bidbadnew.Model.PastProducts;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;
import com.google.android.material.card.MaterialCardView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyBidsAdapter extends RecyclerView.Adapter<MyBidsAdapter.BidHistoryViewHolder> {

    Context context;
    List<MyBid> heroList;
    MyBidsAdapterListener myBidsAdapterListener;

    public MyBidsAdapter(Context context, List<MyBid> heroList, MyBidsAdapterListener myBidsAdapterListener) {
        this.context = context;
        this.heroList = heroList;
        this.myBidsAdapterListener = myBidsAdapterListener;
    }

    public interface MyBidsAdapterListener{
        public void onItemClickListener(MyBid pastProduct);
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
        holder.bidHistoryAmount.setText("₹"+heroList.get(position).getBidamount());
        holder.bidHistoryTitle.setText(heroList.get(position).getTitle());
 //       holder.bidHistoryStartDate.setText(heroList.get(position).getEndtime());
        Log.d("Id", heroList.get(position).getId());
        Log.d("IDs", heroList.get(position).getIds());
        Log.d("Compare" , Integer.parseInt(heroList.get(position).getId()) + "  "+ SharedPrefManager.getInstance(context).getUser().getId());
        if(Integer.parseInt(heroList.get(position).getIds()) == SharedPrefManager.getInstance(context).getUser().getId()) {

//            holder.youWon.setVisibility(View.VISIBLE);
//            holder.youLost.setVisibility(View.GONE);
        } else {
//            holder.youLost.setVisibility(View.VISIBLE);
//            holder.youWon.setVisibility(View.GONE);
        }
        Log.d("Won by : ", heroList.get(position).getFirstname());

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy, h:m a");
            Date date = simpleDateFormat.parse(heroList.get(position).getDate());
            Log.d("Time ", date.getTime() + "");
            holder.subTitle.setText(getDisplayableTime(date.getTime()));
            Log.d("TIme", getDisplayableTime(date.getTime()) + " ");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBidsAdapterListener.onItemClickListener(heroList.get(position));
            }
        });

    }

    public static String getDisplayableTime(long delta)
    {
        long difference=0;
        Long mDate = java.lang.System.currentTimeMillis();

        if(mDate > delta)
        {
            difference= mDate - delta;
            final long seconds = difference/1000;
            final long minutes = seconds/60;
            final long hours = minutes/60;
            final long days = hours/24;
            final long months = days/31;
            final long years = days/365;

            if (seconds < 0)
            {
                return "not yet";
            }
            else if (seconds < 60)
            {
                return seconds == 1 ? "1 second ago" : seconds + " seconds ago";
            }
            else if (seconds < 120)
            {
                return "a minute ago";
            }
            else if (seconds < 2700) // 45 * 60
            {
                return minutes + " minutes ago";
            }
            else if (seconds < 5400) // 90 * 60
            {
                return "an hour ago";
            }
            else if (seconds < 86400) // 24 * 60 * 60
            {
                return hours + " hours ago";
            }
            else if (seconds < 172800) // 48 * 60 * 60
            {
                return "yesterday";
            }
            else if (seconds < 2592000) // 30 * 24 * 60 * 60
            {
                return days + " days ago";
            }
            else if (seconds < 31104000) // 12 * 30 * 24 * 60 * 60
            {

                return months <= 1 ? "1 month ago" : months + " months ago";
            }
            else
            {

                return years <= 1 ? "1 year ago" : years + " years ago";
            }
        }
        return null;
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
        MaterialCardView youWon, youLost;
        TextView subTitle;

        BidHistoryViewHolder(View itemView) {
            super(itemView);
            bidHistoryImage = itemView.findViewById(R.id.bidHistoryImage);
            bidHistoryAmount = itemView.findViewById(R.id.bidHistoryAmount);
            bidHistoryTitle = itemView.findViewById(R.id.bidHistoryTitle);
            bidHistoryStartDate = itemView.findViewById(R.id.bidHistoryStartDate);
            bidHistoryRank = itemView.findViewById(R.id.bidHistoryRank);
            subTitle = itemView.findViewById(R.id.subtitle);
        }
    }
}