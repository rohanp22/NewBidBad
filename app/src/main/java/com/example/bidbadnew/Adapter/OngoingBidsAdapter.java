package com.example.bidbadnew.Adapter;

import android.content.Context;
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
import com.example.bidbadnew.Model.OngoingItems;
import com.example.bidbadnew.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OngoingBidsAdapter extends RecyclerView.Adapter<OngoingBidsAdapter.ViewHolder> {

    List<OngoingItems> cartList;
    Context mContext;
    Date startDate1;

    public OngoingBidsAdapter(Context context, List<OngoingItems> heroList) {
        mContext = context;
        this.cartList = heroList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        Log.d("Onbind", "onbind");

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.amount.setText("Rs. " + cartList.get(position).getBidamount());

        holder.title.setText(cartList.get(position).getTitle());

        holder.startDate.setText(cartList.get(position).getStartDate());

        holder.bidEntry.setText(cartList.get(position).getSp());

        holder.mrp.setText("MRP: " + "Rs." + cartList.get(position).getMrp());

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

                    String curtime = elapsedHours + "hr " + String.format("%02d", elapsedMinutes) + "m " + String.format("%02d", elapsedSeconds) + "s";

                    holder.date.setText(curtime);

                } else {

                }
            }

            public void onFinish() {

            }
        }.start();

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
        TextView amount;
        TextView date;
        TextView startDate;
        TextView bidEntry;
        TextView mrp;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.ticket_product_title);
            amount = (TextView) itemView.findViewById(R.id.ticket_bid_amount);
            image = (ImageView) itemView.findViewById(R.id.ticket_image);
            date = (TextView) itemView.findViewById(R.id.ticket_timer);
            startDate = (TextView) itemView.findViewById(R.id.ticket_start_date);
            bidEntry = (TextView) itemView.findViewById(R.id.ticket_bid_entry);
            mrp = (TextView) itemView.findViewById(R.id.ticket_mrp);
        }
    }
}
