package com.example.bidbadnew.Fragments;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.bidbadnew.Model.MyBid;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;
import com.example.bidbadnew.repositories.RetrofitClient;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDescriptionHistoryMyBids extends Fragment {

    private static final int RC_STORAGE = 1;
    TextView hr1, hr2, min1, min2, sec1, sec2, title, subtitle, started_at, entry, mrp, note1, note2, endedon;
    MyBid current_product;
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
        View view = inflater.inflate(R.layout.fragment_product_description_history_mybids, container, false);

        getActivity().findViewById(R.id.bar).setVisibility(View.GONE);
        getActivity().findViewById(R.id.fabhome).setVisibility(View.GONE);

        current_product = ProductDescriptionHistoryMyBidsArgs.fromBundle(getArguments()).getMyBid();
        Log.d("Current product", current_product.getId() + "");

        imageView1 = view.findViewById(R.id.imageview);
        imageView2 = view.findViewById(R.id.image1);
        imageView3 = view.findViewById(R.id.image2);
        title = view.findViewById(R.id.title);
        subtitle = view.findViewById(R.id.sub);
        entry = view.findViewById(R.id.textView);
        mrp = view.findViewById(R.id.textView3);
        endedon = view.findViewById(R.id.endedon);
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
                    case R.id.menu_item_share:
                        if (verifyPermissions())
                            shareBid();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        SimpleDateFormat startedAtFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM, hh:mm a");

        try {
            startedAt = startedAtFormat.parse(current_product.getEndtime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            startedAt = startedAtFormat.parse(current_product.getStartDate());
            endedon.setText(dateFormat.format(startedAtFormat.parse(current_product.getEndtime())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        title.setText(current_product.getTitle());
        mrp.setText("₹"+current_product.getMrp());
        started_at.setText(dateFormat.format(startedAt));
        subtitle.setText(current_product.getTitle());
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

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        tabLayout.addTab(tabLayout.newTab().setText("Ranking"));
        tabLayout.addTab(tabLayout.newTab().setText("Product details"));

        MyAdapter adapter = new MyAdapter(view.getContext(), getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

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
            Log.d("Current product id : ", current_product.getId());
            switch (position) {
                case 0:
                    return new ProductRankingFragment(current_product.getPastId());

                case 1:
                    return new ProductDetails("productdetails", current_product.getDescription());

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_STORAGE && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            shareBid();
        }
    }

    private boolean verifyPermissions() {
        if (!(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, RC_STORAGE);
            return false;
        }
        return true;
    }

    private void shareBid() {
        final Bitmap[] icon = new Bitmap[1];
        Glide.with(requireContext())
                .asBitmap()
                .load(current_product.getImageUrl())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        icon[0] = resource;
                        Intent intent = new Intent(android.content.Intent.ACTION_SEND);

                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("image/jpeg");

                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, "title");
                        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                        Uri uri = requireContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                values);

                        OutputStream outstream;
                        try {
                            outstream = requireContext().getContentResolver().openOutputStream(uri);
                            icon[0].compress(Bitmap.CompressFormat.JPEG, 100, outstream);
                            outstream.close();
                        } catch (Exception e) {
                            System.err.println(e.toString());
                        }

                        intent.putExtra(Intent.EXTRA_STREAM, uri);
                        //startActivity(Intent.createChooser(share, "Share Image"));

                        /*Create an ACTION_SEND Intent*/
                        /*This will be the actual content you wish you share.*/
                        String shareBody = "Hey! Bid using this link to get the bonus points http://easyvela.esy.es/newproduct?id1=" + current_product.getProductid() + "&id2=" + SharedPrefManager.getInstance(requireContext()).getUser().getId();
                        /*The type of the content is text, obviously.*/
                        intent.setType("text/plain");
                        /*Applying information Subject and Body.*/
                        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share bid");
                        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        /*Fire!*/
                        requireContext().startActivity(Intent.createChooser(intent, "Share via"));
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }
}
