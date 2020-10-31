package com.example.bidbadnew.ui.home;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bidbadnew.Adapter.CategoriesAdapter;
import com.example.bidbadnew.Adapter.ProductAdapter1;
import com.example.bidbadnew.Adapter.ProductsAdapter;
import com.example.bidbadnew.Model.Current_Product;
import com.example.bidbadnew.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public ViewPager2 viewPager2;
    RecyclerView recyclerView, explore;
    ProductsAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        explore = view.findViewById(R.id.explore);

        ArrayList<String> categories = new ArrayList<>();
        categories.add("Kitchen");
        categories.add("Electronics");
        categories.add("Home");
        categories.add("Sports");
        categories.add("Kitchen");
        categories.add("Electronics");
        categories.add("Home");
        categories.add("Sports");

        viewPager2 = view.findViewById(R.id.viewPager);

        CategoriesAdapter adapter1 = new CategoriesAdapter(categories, view.getContext());
        explore.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        explore.setAdapter(adapter1);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @SuppressLint("UseCompatLoadingForColorStateLists")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                View v = snapHelper.findSnapView(linearLayoutManager);
                assert v != null;
                int pos = linearLayoutManager.getPosition(v);

                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(pos);
                assert viewHolder != null;
                ConstraintLayout rl1 = viewHolder.itemView.findViewById(R.id.con);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    rl1.animate().setDuration(10).scaleX(1).scaleY(1).setInterpolator(new AccelerateInterpolator()).start();
                    rl1.findViewById(R.id.cardview).setBackgroundTintList(getResources().getColorStateList(R.color.tintcolor));
                } else {
                    rl1.animate().setDuration(10).scaleX(0.9f).scaleY(0.9f).setInterpolator(new AccelerateInterpolator()).start();
                    rl1.findViewById(R.id.cardview).setBackgroundTintList(getResources().getColorStateList(R.color.tintcolor1));
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        homeViewModel.init();
        homeViewModel.getCurrent_productsmutable().observe(getViewLifecycleOwner(), new Observer<List<Current_Product>>() {
            @SuppressLint("UseCompatLoadingForColorStateLists")
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<Current_Product> current_products) {
                current_products.sort(new sortTime());
                if (adapter == null) {
                    viewPager2.setClipChildren(false);
                    viewPager2.setClipToPadding(false);
                    viewPager2.setOffscreenPageLimit(3);
                    viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

                    viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                        }

                        @Override
                        public void onPageSelected(int position) {
                            super.onPageSelected(position);
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {
                            super.onPageScrollStateChanged(state);
                        }
                    });

                    CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
                    compositePageTransformer.addTransformer(new MarginPageTransformer(40));

                    compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
                        @Override
                        public void transformPage(@NonNull View page, float position) {
                            float r = 1 - Math.abs(position);
                            page.setScaleY(0.85f + r * 0.15f);
                        }
                    });
                    viewPager2.setPageTransformer(compositePageTransformer);

                    viewPager2.setAdapter(new ProductAdapter1(view.getContext(), current_products, viewPager2));
//                    recyclerView.setAdapter(new ProductAdapter1(view.getContext(), current_products));
//                    recyclerView.setLayoutManager(linearLayoutManager);
//                    recyclerView.setPadding(90, 0, 90, 0);

//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(0);
//                            ConstraintLayout rl1 = viewHolder.itemView.findViewById(R.id.con);
//                            rl1.animate().scaleY(1).scaleX(1).setDuration(10).setInterpolator(new AccelerateInterpolator()).start();
//                            rl1.findViewById(R.id.cardview).setBackgroundTintList(getResources().getColorStateList(R.color.tintcolor));
//                        }
//                    }, 100);

//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(0);
//                            ConstraintLayout rl1 = viewHolder.itemView.findViewById(R.id.con);
//                            rl1.animate().scaleY(1).scaleX(1).setDuration(10).setInterpolator(new AccelerateInterpolator()).start();
//                            rl1.findViewById(R.id.cardview).setBackgroundTintList(getResources().getColorStateList(R.color.tintcolor));
//                        }
//                    }, 100);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        getActivity().findViewById(R.id.bar).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.fabhome).setVisibility(View.VISIBLE);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    class sortTime implements Comparator<Current_Product> {
        public int compare(Current_Product a, Current_Product b) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
            Date a1 = null, b1 = null;
            try {
                a1 = simpleDateFormat.parse(a.getEndtime());
                b1 = simpleDateFormat.parse(b.getEndtime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return a1.compareTo(b1);
        }
    }
}