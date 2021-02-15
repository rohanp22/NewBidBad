package com.example.bidbadnew.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bidbadnew.Adapter.ProductDetailsAdapter;
import com.example.bidbadnew.Model.PastProducts;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.ProductDescription;
import com.example.bidbadnew.R;
import com.example.bidbadnew.repositories.RetrofitClient;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDescriptionHistory extends Fragment {

    TextView hr1, hr2, min1, min2, sec1, sec2, title, subtitle, started_at, entry, mrp, note1, note2;
    PastProducts current_product;
    ImageView imageView1, imageView2, imageView3;
    ViewPager viewPager;
    long diff = 0;
    MaterialToolbar toolbar;
    Date startDate1;
    Date startedAt;
    String imageurl1, imageurl2, imageurl3;
    boolean isBookmarked;
    Menu menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_description_history, container, false);

        getActivity().findViewById(R.id.bar).setVisibility(View.GONE);
        getActivity().findViewById(R.id.fabhome).setVisibility(View.GONE);

        current_product = ProductDescriptionHistoryArgs.fromBundle(getArguments()).getPastProduct();
        Log.d("Current product", current_product.getId() + "");

        imageView1 = view.findViewById(R.id.imageview);
        imageView2 = view.findViewById(R.id.image1);
        imageView3 = view.findViewById(R.id.image2);
        title = view.findViewById(R.id.title);
        subtitle = view.findViewById(R.id.sub);
        entry = view.findViewById(R.id.textView);
        mrp = view.findViewById(R.id.textView3);
        started_at = view.findViewById(R.id.startedat);
        note1 = view.findViewById(R.id.cardItem1);
        note2 = view.findViewById(R.id.cardItem2);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.product_description_menu);
        menu = toolbar.getMenu();

        Call<String> call = RetrofitClient.getInstance().getMyApi().isWishlist(SharedPrefManager.getInstance(view.getContext()).getUser().getId(), Integer.parseInt(current_product.getId()));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("Response", response.body());
                if(response.body().equals("1")){
                    menu.findItem(R.id.navigation_bookmark).setIcon(getResources().getDrawable(R.drawable.ic_baseline_bookmark_24));
                } else {
                    menu.findItem(R.id.navigation_bookmark).setIcon(getResources().getDrawable(R.drawable.ic_baseline_bookmark_border_24));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_bookmark:
                        isBookmarked = !isBookmarked;
                        if(isBookmarked) {
                            Call<Void> call = RetrofitClient.getInstance().getMyApi().addToWishlist(SharedPrefManager.getInstance(getContext()).getUser().getId(), Integer.parseInt(current_product.getId()));
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {

                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {

                                }
                            });
                            item.setIcon(getResources().getDrawable(R.drawable.ic_baseline_bookmark_24));
                        } else {
                            Call<Void> call = RetrofitClient.getInstance().getMyApi().deleteFromWishlist(SharedPrefManager.getInstance(getContext()).getUser().getId(), Integer.parseInt(current_product.getId()));
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    Log.d("Removed from wishlist", "wishlist");
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {

                                }
                            });
                            item.setIcon(getResources().getDrawable(R.drawable.ic_baseline_bookmark_border_24));
                        }
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });

        SimpleDateFormat startedAtFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM, hh:mm a");

        try {
            startedAt = startedAtFormat.parse(current_product.getEnd_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        title.setText(current_product.getTitle());
        mrp.setText("₹"+current_product.getMrp());
        started_at.setText(dateFormat.format(startedAt));
        subtitle.setText(current_product.getTitle());
        entry.setText(current_product.getSp());
        Glide.with(view)
                .load(current_product.getImage_url())
                .into(imageView1);

        Glide.with(view)
                .load(current_product.getImage_url2())
                .into(imageView2);

        Glide.with(view)
                .load(current_product.getImage_url3())
                .into(imageView3);

        imageurl1 = current_product.getImage_url();
        imageurl2 = current_product.getImage_url2();
        imageurl3 = current_product.getImage_url3();

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(view)
                        .load(imageurl2)
                        .into(imageView1);

                Glide.with(view)
                        .load(imageurl1)
                        .into(imageView2);

                String temp = imageurl1;
                imageurl1 = imageurl2;
                imageurl2 = temp;
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(view)
                        .load(imageurl3)
                        .into(imageView1);

                Glide.with(view)
                        .load(imageurl1)
                        .into(imageView3);

                String temp = imageurl1;
                imageurl1 = imageurl3;
                imageurl3 = temp;
            }
        });

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Product details"));
        tabLayout.addTab(tabLayout.newTab().setText("Ranking"));

        MyAdapter adapter = new MyAdapter(view.getContext(), getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        tabLayout.setSelectedTabIndicatorHeight((int) (3 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffbc00"));

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    class MyAdapter extends FragmentPagerAdapter {

        int totalTabs;

        public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
            super(fm);
            this.totalTabs = totalTabs;
        }

        // this is for fragment tabs
        @Override
        public Fragment getItem(int position){
            switch (position) {
                case 0:
                    return new ProductDetails("productdetails", current_product.getDescription());

                case 1:
                    return new ProductRankingFragment(current_product.getId());

                default:
                    return null;
            }
        }

        // this counts total number of tabs
        @Override
        public int getCount() {
            return totalTabs;
        }
    }
}