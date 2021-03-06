package com.example.bidbadnew.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bidbadnew.Model.OngoingItems;
import com.example.bidbadnew.Others.Symbol;
import com.example.bidbadnew.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OngoingBidsAdapter extends RecyclerView.Adapter<OngoingBidsAdapter.ViewHolder> {

    List<OngoingItems> cartList;
    Context mContext;
    Date startDate1;
    OngoingBidsAdapterListener ongoingBidsAdapterListener;

    public OngoingBidsAdapter(Context context, List<OngoingItems> heroList, OngoingBidsAdapterListener ongoingBidsAdapterListener) {
        mContext = context;
        this.cartList = heroList;
        this.ongoingBidsAdapterListener = ongoingBidsAdapterListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ongoing_item, parent, false);
        return new ViewHolder(view);
    }

    Bitmap icon;

    public interface OngoingBidsAdapterListener{
        public void onItemClickListener(OngoingItems pastProduct);

        void onShareButtonClick(OngoingItems onGoingItem);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ongoingBidsAdapterListener.onItemClickListener(cartList.get(position));
            }
        });

        holder.yourbid.setText(Symbol.rupee + cartList.get(position).getBidamount());

        holder.title.setText(cartList.get(position).getTitle());

        long diff = 0;

        final long secondsInMilli = 1000;
        final long minutesInMilli = secondsInMilli * 60;
        final long hoursInMilli = minutesInMilli * 60;
        final long daysInMilli = hoursInMilli * 24;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

        try {
            startDate1 = simpleDateFormat.parse(cartList.get(position).getEndtime());
            diff = startDate1.getTime() - System.currentTimeMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final long finalDiff = diff;

        new CountDownTimer(finalDiff, 1000) {

            public void onTick(long millisUntilFinished) {

                long different = 0;
                try {
                    different = simpleDateFormat.parse(cartList.get(position).getEndtime()).getTime() - System.currentTimeMillis();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (different > 0) {

                    final long elapsedDays = different / daysInMilli;

                    final long elapsedHours = different / hoursInMilli;
                    different = different % hoursInMilli;

                    final long elapsedMinutes = different / minutesInMilli;
                    different = different % minutesInMilli;

                    final long elapsedSeconds = different / secondsInMilli;

                    String curtime = elapsedHours + ":" + String.format("%02d", elapsedMinutes) + ":" + String.format("%02d", elapsedSeconds);

                    holder.date.setText(curtime);

                } else {

                }
            }

            public void onFinish() {

            }
        }.start();

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ongoingBidsAdapterListener.onShareButtonClick(cartList.get(position));
            }
        });

        Glide.with(mContext)
                .asBitmap()
                .load(cartList.get(position).getImageUrl())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        TextView date;
        TextView yourbid;
        ImageView share;

        public ViewHolder(View itemView) {
            super(itemView);
            yourbid = (TextView) itemView.findViewById(R.id.yourbid);
            title = (TextView) itemView.findViewById(R.id.title);
            image = (ImageView) itemView.findViewById(R.id.imageview);
            date = (TextView) itemView.findViewById(R.id.endsin);
            share = itemView.findViewById(R.id.sharebid);
        }
    }
}
