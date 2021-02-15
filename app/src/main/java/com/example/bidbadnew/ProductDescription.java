package com.example.bidbadnew;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bidbadnew.Fragments.AllBidsFragment;
import com.example.bidbadnew.Fragments.MyBidsFragment;
import com.example.bidbadnew.Fragments.OngoingBidsFragment;
import com.example.bidbadnew.Fragments.ProductDetails;
import com.example.bidbadnew.Model.Current_Product;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.repositories.RetrofitClient;
import com.example.bidbadnew.ui.home.HomeFragmentDirections;
import com.example.bidbadnew.ui.results.ResultsFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDescription extends Fragment {

    TextView hr1, hr2, min1, min2, sec1, sec2, title, subtitle, started_at, entry, mrp, note1, note2;
    Current_Product current_product;
    ImageView imageView1, imageView2, imageView3;
    ViewPager viewPager;
    long diff = 0;
    MaterialToolbar toolbar;
    Date startDate1;
    Menu menu;
    Date startedAt;
    String imageurl1, imageurl2, imageurl3;
    boolean isBookmarked;
    MaterialButton bidnowbtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_description2, container, false);

        getActivity().findViewById(R.id.bar).setVisibility(View.GONE);
        getActivity().findViewById(R.id.fabhome).setVisibility(View.GONE);

        current_product = ProductDescriptionArgs.fromBundle(getArguments()).getProduct();
        Log.d("Current product", current_product.getCurrentid() + "");

        imageView1 = view.findViewById(R.id.imageview);
        bidnowbtn = view.findViewById(R.id.bidnowbtn);
        imageView2 = view.findViewById(R.id.image1);
        imageView3 = view.findViewById(R.id.image2);
        hr1 = view.findViewById(R.id.hr1);
        hr2 = view.findViewById(R.id.hr2);
        min1 = view.findViewById(R.id.min1);
        min2 = view.findViewById(R.id.min2);
        sec1 = view.findViewById(R.id.sec1);
        sec2 = view.findViewById(R.id.sec2);
        title = view.findViewById(R.id.title);
        subtitle = view.findViewById(R.id.sub);
        entry = view.findViewById(R.id.textView);
        mrp = view.findViewById(R.id.textView3);
        started_at = view.findViewById(R.id.startedat);
        note1 = view.findViewById(R.id.cardItem1);
        note2 = view.findViewById(R.id.cardItem2);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.product_description_menu);

        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        menu = toolbar.getMenu();

        Call<String> call = RetrofitClient.getInstance().getMyApi().isWishlist(SharedPrefManager.getInstance(view.getContext()).getUser().getId(), Integer.parseInt(current_product.getCurrentid()));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("Response", response.body());
                if(response.body().equals("1")){
                    isBookmarked = true;
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

        bidnowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionBottomDialogFragment addPhotoBottomDialogFragment =
                        ActionBottomDialogFragment.newInstance(current_product);
                addPhotoBottomDialogFragment.show(getChildFragmentManager(),
                        ActionBottomDialogFragment.TAG);
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_bookmark:
                        isBookmarked = !isBookmarked;
                        if(isBookmarked) {
                            Call<Void> call = RetrofitClient.getInstance().getMyApi().addToWishlist(SharedPrefManager.getInstance(getContext()).getUser().getId(), Integer.parseInt(current_product.getCurrentid()));
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    Snackbar.make(view, "Product added to wishlist", Snackbar.LENGTH_SHORT)
                                            .show();
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {

                                }
                            });
                            item.setIcon(getResources().getDrawable(R.drawable.ic_baseline_bookmark_24));
                        } else {
                            Call<Void> call = RetrofitClient.getInstance().getMyApi().deleteFromWishlist(SharedPrefManager.getInstance(getContext()).getUser().getId(), Integer.parseInt(current_product.getCurrentid()));
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    Snackbar.make(view, "Product removed from wishlist", Snackbar.LENGTH_SHORT)
                                            .show();
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
            startedAt = startedAtFormat.parse(current_product.getStartDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        title.setText(current_product.getTitle());
        mrp.setText("₹"+current_product.getMrp());
        started_at.setText(dateFormat.format(startedAt));
        subtitle.setText(current_product.getSubtitle());
        entry.setText(current_product.getSp());
        Glide.with(view)
                .load(current_product.getImageUrl())
                .into(imageView1);

        Glide.with(view)
                .load(current_product.getImageUrl2())
                .into(imageView2);

        Glide.with(view)
                .load(current_product.getImageUrl3())
                .into(imageView3);

         imageurl1 = current_product.getImageUrl();
         imageurl2 = current_product.getImageUrl2();
         imageurl3 = current_product.getImageUrl3();

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

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        try {
            startDate1 = simpleDateFormat.parse(current_product.getEndtime());
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

            public void onTick(long millisUntilFinished) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

                try {
                    startDate1 = simpleDateFormat.parse(current_product.getEndtime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long different = startDate1.getTime() - System.currentTimeMillis();
                Log.d("Difference", different + "");

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
                        hr1.setText(elapsedHours/10 + "");
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

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Product details"));
        tabLayout.addTab(tabLayout.newTab().setText("Delivery details"));

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
                    return new ProductDetails("deliverydetails", current_product.getDescription());

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