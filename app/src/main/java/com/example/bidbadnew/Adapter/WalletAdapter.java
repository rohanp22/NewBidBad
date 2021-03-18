package com.example.bidbadnew.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bidbadnew.Model.Transaction;
import com.example.bidbadnew.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static java.lang.Math.abs;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {

    List<Transaction> walletTransactions;
    Context context;
    int size;

    public WalletAdapter(Context context, List<Transaction> walletTransactions, int size) {
        this.context = context;
        this.size = size;
        Log.d("Sizewallet", String.valueOf(walletTransactions.size()));
        Log.d("Size", String.valueOf(size));
        this.walletTransactions = walletTransactions;
    }

    @NonNull
    @Override
    public WalletAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.transaction_layout, viewGroup, false);

        return new WalletAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletAdapter.ViewHolder viewHolder, int i) {
        if (Integer.parseInt(walletTransactions.get(i).getAmount()) > 0) {
            viewHolder.amount.setText("+ ₹" + walletTransactions.get(i).getAmount());
            viewHolder.amount.setTextColor(Color.parseColor("#3b864f"));
        } else {
            viewHolder.amount.setText("- ₹" + abs(Integer.parseInt(walletTransactions.get(i).getAmount())));
            viewHolder.amount.setTextColor(Color.parseColor("#ad1615"));
        }
        viewHolder.orderid.setText("Transaction id - " + walletTransactions.get(i).getOrderid());

        String pattern = "d MMM yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("MMMM d, yyyy, h:mm a");
        Date date = null;
        try {
            date = simpleDateFormat2.parse(walletTransactions.get(i).getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date);
        String dt = simpleDateFormat.format(date);
        Log.d("dt", dt);
        String da = getDisplayableTime(date.getTime());
        if (da == null) {
            viewHolder.date.setText(dt);
        } else {
            viewHolder.date.setText(da);
        }

        //viewHolder.date.setText(walletTransactions.get(i).getDate());
        viewHolder.type.setText(walletTransactions.get(i).getType());

        Glide.with(context)
                .asBitmap()
                .load(walletTransactions.get(i).getImageUrl())
                .into(viewHolder.c);
    }

    public String getDisplayableTime(long delta) {
        long difference = 0;
        Long mDate = java.lang.System.currentTimeMillis();

        if (mDate > delta) {
            difference = mDate - delta;
            final long seconds = difference / 1000;
            final long minutes = seconds / 60;
            final long hours = minutes / 60;
            final long days = hours / 24;
            final long months = days / 31;
            final long years = days / 365;

            if (seconds < 0) {
                return "not yet";
            } else if (seconds < 60) {
                return seconds == 1 ? "one second ago" : seconds + " seconds ago";
            } else if (seconds < 120) {
                return "a minute ago";
            } else if (seconds < 2700) // 45 * 60
            {
                return minutes + " minutes ago";
            } else if (seconds < 5400) // 90 * 60
            {
                return "an hour ago";
            } else if (seconds < 86400) // 24 * 60 * 60
            {
                return hours + " hours ago";
            } else if (seconds < 172800) // 48 * 60 * 60
            {
                return "yesterday";
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        if(walletTransactions.size() < 4){
            return walletTransactions.size();
        } else
            return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView amount;
        TextView type;
        TextView date;
        TextView orderid;
        ImageView c;

        public ViewHolder(View itemView) {
            super(itemView);
            c = itemView.findViewById(R.id.imageTransaction);
            amount = itemView.findViewById(R.id.value);
            date = itemView.findViewById(R.id.date);
            type = itemView.findViewById(R.id.transactionDescription);
            orderid = itemView.findViewById(R.id.orderId);
        }
    }
}

class sortTime2 implements Comparator<Transaction> {
    public int compare(Transaction a, Transaction b) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM d,yyyy, h:mm a");
        Date a1 = null, b1 = null;
        try {
            a1 = simpleDateFormat.parse(a.getDate());
            b1 = simpleDateFormat.parse(b.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return b1.compareTo(a1);
    }
}