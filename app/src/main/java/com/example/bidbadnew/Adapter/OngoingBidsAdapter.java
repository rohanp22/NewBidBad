package com.example.bidbadnew.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bidbadnew.Model.MyBid;
import com.example.bidbadnew.Model.OngoingItems;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.Others.Symbol;
import com.example.bidbadnew.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

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

    public interface OngoingBidsAdapterListener{
        public void onItemClickListener(OngoingItems pastProduct);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

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

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

                try {
                    if (position != 0)
                        if (cartList.size() != 0) {
                            startDate1 = simpleDateFormat.parse(cartList.get(position).getEndtime());
                        }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long different = startDate1.getTime() - System.currentTimeMillis();

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
                /*Create an ACTION_SEND Intent*/
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                /*This will be the actual content you wish you share.*/
                String shareBody = "Hey! Bid using this link to get the bonus points http://easyvela.esy.es/newproduct?id1="+cartList.get(position).getCurrentid() + "&id2="+ SharedPrefManager.getInstance(mContext).getUser().getId();
                /*The type of the content is text, obviously.*/
                intent.setType("text/plain");
                /*Applying information Subject and Body.*/
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share bid");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                /*Fire!*/
                mContext.startActivity(Intent.createChooser(intent, "Share via"));
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
