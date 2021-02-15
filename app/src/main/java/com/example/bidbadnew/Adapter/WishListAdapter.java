package com.example.bidbadnew.Adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bidbadnew.Model.WishListItem;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;
import com.example.bidbadnew.repositories.CurrentProductsRepo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListViewHolder> {

    List<WishListItem> wishListItems;
    Context context;
    long finalDiff;
    Date start;

    public WishListAdapter(List<WishListItem> wishListItems, Context context) {
        this.wishListItems = wishListItems;
        this.context = context;
    }

    @NonNull
    @Override
    public WishListAdapter.WishListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wishlist, parent, false);
        return new WishListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.WishListViewHolder holder, int position) {
        Glide.with(context)
                .load(wishListItems.get(position).getImageUrl())
                .into(holder.imageView);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yy hh:mm");
        try {
            Date date = simpleDateFormat.parse(wishListItems.get(position).getEndtime());
            String date1 = simpleDateFormat1.format(date);
            finalDiff = date.getTime() - System.currentTimeMillis();
            if(date.getTime() < System.currentTimeMillis()){
                holder.endsin.setText("Ended on : " + date1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.title.setText(wishListItems.get(position).getTitle());
        holder.price.setText("₹"+wishListItems.get(position).getSp());

        final long secondsInMilli = 1000;
        final long minutesInMilli = secondsInMilli * 60;
        final long hoursInMilli = minutesInMilli * 60;
        final long daysInMilli = hoursInMilli * 24;

        new CountDownTimer(finalDiff, 1000) {

            long dif = finalDiff;

            public void onTick(long millisUntilFinished) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

                try {
                    start = simpleDateFormat.parse(wishListItems.get(position).getEndtime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long different = start.getTime() - System.currentTimeMillis();

                if (different > 0) {

                    final long elapsedDays = different / daysInMilli;

                    final long elapsedHours = (different / hoursInMilli);
                    different = different % hoursInMilli;

                    final long elapsedMinutes = different / minutesInMilli;
                    different = different % minutesInMilli;

                    final long elapsedSeconds = different / secondsInMilli;

                    holder.endsin.setText("Ends in : "+elapsedHours + ":"+elapsedMinutes+ ":"+elapsedSeconds);
                }
            }

            public void onFinish() {

            }

        }.start();
    }

    @Override
    public int getItemCount() {
        return wishListItems.size();
    }

    public class WishListViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title, endsin, price;

        public WishListViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.bidHistoryImage);
            title = itemView.findViewById(R.id.bidHistoryTitle);
            endsin = itemView.findViewById(R.id.bidHistoryStartDate);
            price = itemView.findViewById(R.id.bidHistoryAmount);
        }
    }
}
