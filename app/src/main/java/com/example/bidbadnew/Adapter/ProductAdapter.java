package com.example.bidbadnew.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bidbadnew.Model.Current_Product;
import com.example.bidbadnew.Others.CenterZoomLayoutManager;
import com.example.bidbadnew.Others.Symbol;
import com.example.bidbadnew.R;
import com.example.bidbadnew.repositories.CurrentProductsRepo;
import com.example.bidbadnew.ui.home.HomeFragmentDirections;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductAdapterViewHolder> {

    Context context;
    List<Current_Product> heroList = new ArrayList<>();
    Date startDate1;
    FragmentManager fragmentManager;


    public ProductAdapter(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.heroList = heroList;
    }

    public void updateList(List<Current_Product> list) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout_new, parent, false);
        context = view.getContext();
        return new ProductAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapterViewHolder holder, int position) {
        if(position == 0){
            holder.materialCardView.setStrokeColor(context.getResources().getColor(R.color.colorSecondary));
        }
        holder.title.setText(heroList.get(position).getTitle());
        holder.mrp.setText("MRP: " + heroList.get(position).getMrp());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        long diff = 0;
        try {
            startDate1 = simpleDateFormat.parse(heroList.get(position).getEndtime());
            diff = startDate1.getTime() - System.currentTimeMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(Integer.parseInt(heroList.get(position).getSp()) > 0) {
            holder.entry.setText("" + heroList.get(position).getSp());
        } else {
            holder.entry.setText("Free bid");
        }

        holder.bidnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putSerializable("Current_product", heroList.get(position));
                Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_actionBottomDialogFragment, b);
//                ActionBottomDialogFragment addPhotoBottomDialogFragment =
//                        ActionBottomDialogFragment.newInstance(heroList.get(position), itemClickListener);
//                addPhotoBottomDialogFragment.show(fragmentManager,
//                        ActionBottomDialogFragment.TAG);
            }
        });

        Glide.with(context)
                .load(heroList.get(position).getImageUrl())
                .into(holder.imageView);

        final long secondsInMilli = 1000;
        final long minutesInMilli = secondsInMilli * 60;
        final long hoursInMilli = minutesInMilli * 60;
        final long daysInMilli = hoursInMilli * 24;

        final long finalDiff = diff;

        new CountDownTimer(finalDiff, 1000) {

            long dif = finalDiff;

            public void onTick(long millisUntilFinished) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

                long different = 0;
                try {
                    different = simpleDateFormat.parse(heroList.get(position).getEndtime()).getTime() - System.currentTimeMillis();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (different > 0) {

                    final long elapsedDays = different / daysInMilli;
                    different = different % daysInMilli;

                    final long elapsedHours = different / hoursInMilli;
                    different = different % hoursInMilli;

                    final long elapsedMinutes = different / minutesInMilli;
                    different = different % minutesInMilli;

                    final long elapsedSeconds = different / secondsInMilli;

                    holder.days.setText(String.format("%02d", elapsedDays));
                    holder.hr.setText(String.format("%02d", elapsedHours));
                    holder.min.setText(String.format("%02d", elapsedMinutes));
                    holder.sec.setText(String.format("%02d", elapsedSeconds));

//                    if (elapsedHours > 0) {
//                        holder.hr1.setText(elapsedHours / 10 + "");
//                        holder.hr2.setText(elapsedHours % 10 + "");
//                        holder.min1.setText(elapsedMinutes / 10 + "");
//                        holder.min2.setText(elapsedMinutes % 10 + "");
//                        holder.sec1.setText(elapsedSeconds / 10 + "");
//                        holder.sec2.setText(elapsedSeconds % 10 + "");
//                    } else {
//                        holder.hr1.setText(elapsedHours / 10 + "");
//                        holder.hr2.setText(elapsedHours % 10 + "");
//                        holder.min1.setText(elapsedMinutes / 10 + "");
//                        holder.min2.setText(elapsedMinutes % 10 + "");
//                        holder.sec1.setText(elapsedSeconds / 10 + "");
//                        holder.sec2.setText(elapsedSeconds % 10 + "");
//                    }
                }
            }

            public void onFinish() {
                switch (heroList.get(position).getCategory()) {
                    case "Electronics":
                        CurrentProductsRepo.getInstance().getElectronicsProductCategory();
                        break;
                    case "Appliances":
                        CurrentProductsRepo.getInstance().getAppliancesCategory();
                        break;
                    case "Accessories":
                        CurrentProductsRepo.getInstance().getAccessoriesCategory();
                        break;
                    case "Personal Care":
                        CurrentProductsRepo.getInstance().getPersonalcareProductCategory();
                        break;
                    case "Home & Furniture":
                        CurrentProductsRepo.getInstance().getHomeProductCategory();
                        break;
                    case "Fitness":
                        CurrentProductsRepo.getInstance().getFitnessProductCategory();
                        break;
                    case "Others":
                        CurrentProductsRepo.getInstance().getOthersCategory();
                        break;
                    case "Apparel":
                        CurrentProductsRepo.getInstance().getApparelProductCategory();
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

        TextView mrp, title, hr, days, min, sec, entry;
        MaterialCardView materialCardView;
        ConstraintLayout bidnow;
        ImageView imageView;
        ConstraintLayout constraintLayout;

        public ProductAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
            mrp = itemView.findViewById(R.id.mrp);
            materialCardView = itemView.findViewById(R.id.cardView);
            days = itemView.findViewById(R.id.days);
            hr = itemView.findViewById(R.id.hours);
            sec = itemView.findViewById(R.id.seconds);
            min = itemView.findViewById(R.id.min);
            title = itemView.findViewById(R.id.title);
            bidnow = itemView.findViewById(R.id.bidnowbtn);
            imageView = itemView.findViewById(R.id.imageView);
            entry = itemView.findViewById(R.id.textView2);

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
