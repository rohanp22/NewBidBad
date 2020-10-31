package com.example.bidbadnew.Adapter;

import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bidbadnew.Model.Current_Product;
import com.example.bidbadnew.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProductAdapter1 extends RecyclerView.Adapter<ProductAdapter1.ProductAdapterViewHolder> {

    Context context;
    List<Current_Product> heroList;
    Date startDate1;
    ViewPager2 viewPager2;

    public ProductAdapter1(Context context, List<Current_Product> heroList, ViewPager2 viewPager2) {
        this.context = context;
        this.heroList = heroList;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public ProductAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductAdapterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.demo, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final ProductAdapter1.ProductAdapterViewHolder holder, final int position) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        if(position == viewPager2.getCurrentItem()){
            holder.constraintLayout.setBackgroundTintList(context.getResources().getColorStateList(R.color.tintcolor));
        } else {
            holder.constraintLayout.setBackgroundTintList(context.getResources().getColorStateList(R.color.tintcolor1));
        }
        long diff = 0;
        try {
            startDate1 = simpleDateFormat.parse(heroList.get(position).getEndtime());
            diff = startDate1.getTime() - System.currentTimeMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final long secondsInMilli = 1000;
        final long minutesInMilli = secondsInMilli * 60;
        final long hoursInMilli = minutesInMilli * 60;
        final long daysInMilli = hoursInMilli * 24;

        final long finalDiff = diff;

        new CountDownTimer(finalDiff, 1000) {

            long dif = finalDiff;

            public void onTick(long millisUntilFinished) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

                try {
                    startDate1 = simpleDateFormat.parse(heroList.get(position).getEndtime());
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

                    String curtime;

                    if (elapsedHours > 0) {
                        curtime = elapsedHours + "hr " + String.format("%02d", elapsedMinutes) + "min";
                        holder.hr1.setText(elapsedHours/100 + "");
                        holder.hr2.setText(elapsedHours%10 + "");
                        holder.min1.setText(elapsedMinutes/10 + "");
                        holder.min2.setText(elapsedMinutes%10 + "");
                        holder.sec1.setText(elapsedSeconds/10 + "");
                        holder.sec2.setText(elapsedSeconds%10 + "");
                    } else {
                        curtime = String.format("%02d", elapsedMinutes) + " m " + String.format("%02d", elapsedSeconds) + " sec";
                        holder.hr1.setText(elapsedHours/10 + "");
                        holder.hr2.setText(elapsedHours%10 + "");
                        holder.min1.setText(elapsedMinutes/10 + "");
                        holder.min2.setText(elapsedMinutes%10 + "");
                        holder.sec1.setText(elapsedSeconds/10 + "");
                        holder.sec2.setText(elapsedSeconds%10 + "");
                    }
                }
            }

            public void onFinish() {

            }

        }.start();

    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }

    public class ProductAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView hr1, hr2, min1, min2, sec1, sec2;
        ConstraintLayout constraintLayout;

        ProductAdapterViewHolder(View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.con);
            hr1 = itemView.findViewById(R.id.hr1);
            hr2 = itemView.findViewById(R.id.hr2);
            min1 = itemView.findViewById(R.id.min1);
            min2 = itemView.findViewById(R.id.min2);
            sec1 = itemView.findViewById(R.id.sec1);
            sec2 = itemView.findViewById(R.id.sec2);
        }
    }
}