package com.example.bidbadnew.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.bidbadnew.Model.Current_Product;
import com.example.bidbadnew.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProductsAdapter extends PagerAdapter {

    private List<Current_Product> currentProducts;
    private LayoutInflater layoutInflater;
    private Context context;
    ViewPager viewPager;
    View mCurrentView;
    Date startDate1;
    TextView hr1, hr2, min1, min2, sec1, sec2;

    public ProductsAdapter(List<Current_Product> currentProducts, Context context, ViewPager viewPager) {
        this.currentProducts = currentProducts;
        this.context = context;
        this.viewPager = viewPager;
    }

    @Override
    public int getCount() {
        return currentProducts.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.demo, container, false);
        ImageView imageView;

        imageView = view.findViewById(R.id.image);
        hr1 = view.findViewById(R.id.hr1);
        hr2 = view.findViewById(R.id.hr2);
        min1 = view.findViewById(R.id.min1);//        ImageView imageView;
//
//        imageView = view.findViewById(R.id.image);
//        hr1 = view.findViewById(R.id.hr1);
//        hr2 = view.findViewById(R.id.hr2);
//        min1 = view.findViewById(R.id.min1);
//        min2 = view.findViewById(R.id.min2);
//        sec1 = view.findViewById(R.id.sec1);
//        sec2 = view.findViewById(R.id.sec2);
//        Glide.with(context)
//                .load(currentProducts.get(position).getImageUrl())
//                .into(imageView);
//
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NavController navController = Navigation.findNavController((AppCompatActivity) context, R.id.nav_host_fragment);
//                navController.navigate(R.id.action_navigation_home_to_productDescription);
//            }
//        });
//
//        container.addView(view, 0);
//
//
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
//
//        long diff = 0;
//        try {
//            startDate1 = simpleDateFormat.parse(currentProducts.get(position).getEndtime());
//            diff = startDate1.getTime() - System.currentTimeMillis();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        final long secondsInMilli = 1000;
//        final long minutesInMilli = secondsInMilli * 60;
//        final long hoursInMilli = minutesInMilli * 60;
//        final long daysInMilli = hoursInMilli * 24;
//
//        final long finalDiff = diff;
//
//        new CountDownTimer(finalDiff, 1000) {
//
//            long dif = finalDiff;
//
//            public void onTick(long millisUntilFinished) {
//
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
//
//                try {
//                    startDate1 = simpleDateFormat.parse(currentProducts.get(position).getEndtime());
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//                long different = startDate1.getTime() - System.currentTimeMillis();
//
//                if (different > 0) {
//
//                    final long elapsedDays = different / daysInMilli;
//
//                    final long elapsedHours = different / hoursInMilli;
//                    different = different % hoursInMilli;
//
//                    final long elapsedMinutes = different / minutesInMilli;
//                    different = different % minutesInMilli;
//
//                    final long elapsedSeconds = different / secondsInMilli;
//
//                    String curtime;
//
//                    if (elapsedHours > 0) {
//                        curtime = elapsedHours + "hr " + String.format("%02d", elapsedMinutes) + "min";
//                        hr1.setText(elapsedHours/100 + "");
//                        hr2.setText(elapsedHours%10 + "");
//                        min1.setText(elapsedMinutes/10 + "");
//                        min2.setText(elapsedMinutes%10 + "");
//                        sec1.setText(elapsedSeconds/10 + "");
//                        sec2.setText(elapsedSeconds%10 + "");
//                    } else {
//                        curtime = String.format("%02d", elapsedMinutes) + " m " + String.format("%02d", elapsedSeconds) + " sec";
//                        hr1.setText(elapsedHours/10 + "");
//                        hr2.setText(elapsedHours%10 + "");
//                        min1.setText(elapsedMinutes/10 + "");
//                        min2.setText(elapsedMinutes%10 + "");
//                        sec1.setText(elapsedSeconds/10 + "");
//                        sec2.setText(elapsedSeconds%10 + "");
//                    }
//                }
//            }
//
//            public void onFinish() {
//
//            }
//
//        }.start();
        min2 = view.findViewById(R.id.min2);
        sec1 = view.findViewById(R.id.sec1);
        sec2 = view.findViewById(R.id.sec2);
        Glide.with(context)
                .load(currentProducts.get(position).getImageUrl())
                .into(imageView);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController((AppCompatActivity) context, R.id.nav_host_fragment);
                navController.navigate(R.id.action_navigation_home_to_productDescription);
            }
        });

        container.addView(view, 0);


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

        long diff = 0;
        try {
            startDate1 = simpleDateFormat.parse(currentProducts.get(position).getEndtime());
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
                    startDate1 = simpleDateFormat.parse(currentProducts.get(position).getEndtime());
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
                        hr1.setText(elapsedHours/100 + "");
                        hr2.setText(elapsedHours%10 + "");
                        min1.setText(elapsedMinutes/10 + "");
                        min2.setText(elapsedMinutes%10 + "");
                        sec1.setText(elapsedSeconds/10 + "");
                        sec2.setText(elapsedSeconds%10 + "");
                    } else {
                        curtime = String.format("%02d", elapsedMinutes) + " m " + String.format("%02d", elapsedSeconds) + " sec";
                        hr1.setText(elapsedHours/10 + "");
                        hr2.setText(elapsedHours%10 + "");
                        min1.setText(elapsedMinutes/10 + "");
                        min2.setText(elapsedMinutes%10 + "");
                        sec1.setText(elapsedSeconds/10 + "");
                        sec2.setText(elapsedSeconds%10 + "");
                    }
                }
            }

            public void onFinish() {

            }

        }.start();

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}