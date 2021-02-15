package com.example.bidbadnew.Adapter;

import android.content.Context;
import android.graphics.Shader;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.bidbadnew.ActionBottomDialogFragment;
import com.example.bidbadnew.Model.Current_Product;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.Others.Symbol;
import com.example.bidbadnew.ProductDescriptionDirections;
import com.example.bidbadnew.R;
import com.example.bidbadnew.repositories.CurrentProductsRepo;
import com.example.bidbadnew.ui.home.HomeFragmentDirections;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductAdapter1 extends RecyclerView.Adapter<ProductAdapter1.ProductAdapterViewHolder> {

    Context context;
    List<Current_Product> heroList = new ArrayList<>();
    Date startDate1;
    ViewPager2 viewPager2;
    FragmentManager fragmentManager;
    RecyclerView recyclerView;

    public ProductAdapter1(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.heroList = heroList;
        this.fragmentManager = fragmentManager;
    }

    public void updateList(List<Current_Product> list){
        this.heroList = list;
        notifyDataSetChanged();
    }

    public void setHeroList(List<Current_Product> heroList) {
        this.heroList = heroList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false);
        return new ProductAdapterViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final ProductAdapter1.ProductAdapterViewHolder holder, final int position) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        long diff = 0;
        try {
            startDate1 = simpleDateFormat.parse(heroList.get(position).getEndtime());
            diff = startDate1.getTime() - System.currentTimeMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.bidnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActionBottomDialogFragment addPhotoBottomDialogFragment =
                        ActionBottomDialogFragment.newInstance(heroList.get(position));
                addPhotoBottomDialogFragment.show(fragmentManager,
                        ActionBottomDialogFragment.TAG);
            }
        });

        Glide.with(context)
                .load(heroList.get(position).getImageUrl())
                .into(holder.imageView);

        Glide.with(context)
                .load(heroList.get(position).getImageUrl2())
                .into(holder.imageView2);

        Glide.with(context)
                .load(heroList.get(position).getImageUrl3())
                .into(holder.imageView3);

        holder.subtitle.setText(heroList.get(position).getCategory());
        holder.mrp.setText("MRP "+ Symbol.rupee + heroList.get(position).getMrp());
        holder.entry.setText(heroList.get(position).getSp());
        holder.title.setText(heroList.get(position).getTitle());

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

                    if (elapsedHours > 0) {
                        holder.hr1.setText(elapsedHours/10 + "");
                        holder.hr2.setText(elapsedHours%10 + "");
                        holder.min1.setText(elapsedMinutes/10 + "");
                        holder.min2.setText(elapsedMinutes%10 + "");
                        holder.sec1.setText(elapsedSeconds/10 + "");
                        holder.sec2.setText(elapsedSeconds%10 + "");
                    } else {
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
                switch (heroList.get(position).getCategory()) {
                    case "Electronics" : CurrentProductsRepo.getInstance().getElectronicsProductCategory();
                        break;
                    case "Appliances" : CurrentProductsRepo.getInstance().getAppliancesCategory();
                        break;
                    case "Accessories" : CurrentProductsRepo.getInstance().getAccessoriesCategory();
                        break;
                    case "Personal Care" : CurrentProductsRepo.getInstance().getPersonalcareProductCategory();
                        break;
                    case "Home & Furniture" : CurrentProductsRepo.getInstance().getHomeProductCategory();
                        break;
                    case "Fitness" : CurrentProductsRepo.getInstance().getFitnessProductCategory();
                        break;
                    case "Others" : CurrentProductsRepo.getInstance().getOthersCategory();
                        break;
                    case "Apparel" : CurrentProductsRepo.getInstance().getApparelProductCategory();
                        break;
                }
            }

        }.start();

    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }

    public class ProductAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView hr1, hr2, min1, min2, sec1, sec2, bidnow, subtitle, mrp, entry, title;
        ImageView imageView, imageView2, imageView3;
        ConstraintLayout constraintLayout;

        ProductAdapterViewHolder(View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.con);
            imageView = itemView.findViewById(R.id.image);
            hr1 = itemView.findViewById(R.id.hr1);
            hr2 = itemView.findViewById(R.id.hr2);
            imageView2 = itemView.findViewById(R.id.img2);
            imageView3 = itemView.findViewById(R.id.img3);
            min1 = itemView.findViewById(R.id.min1);
            title = itemView.findViewById(R.id.title);
            min2 = itemView.findViewById(R.id.min2);
            sec1 = itemView.findViewById(R.id.sec1);
            sec2 = itemView.findViewById(R.id.sec2);
            bidnow = itemView.findViewById(R.id.bidnowbtn);
            subtitle = itemView.findViewById(R.id.sub);
            mrp = itemView.findViewById(R.id.mrp);
            entry = itemView.findViewById(R.id.entrypricenumber);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavController navController = Navigation.findNavController((AppCompatActivity) context, R.id.nav_host_fragment);
                    navController.navigate(HomeFragmentDirections.actionNavigationHomeToNavigationProductDescription(heroList.get(getAdapterPosition())));
                }
            });
        }
    }
}