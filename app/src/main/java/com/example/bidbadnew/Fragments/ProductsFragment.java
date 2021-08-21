package com.example.bidbadnew.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.bidbadnew.Activities.MainActivity;
import com.example.bidbadnew.Adapter.ProductAdapter;
import com.example.bidbadnew.Adapter.ProductAdapter1;
import com.example.bidbadnew.Model.Current_Product;
import com.example.bidbadnew.Others.CenterZoomLayoutManager;
import com.example.bidbadnew.Others.ProductLayoutDecoration;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;
import com.example.bidbadnew.repositories.CurrentProductsRepo;
import com.google.android.material.card.MaterialCardView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ProductsFragment extends Fragment {

    String category;
    ProductsViewModel productsViewModel;
    RecyclerView recyclerView;
    ProductAdapter adapter;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe);
        recyclerView = view.findViewById(R.id.recyclerView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productsViewModel.refreshProduct(position, SharedPrefManager.getInstance(view.getContext()).getUser().getId());
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        new PagerSnapHelper().attachToRecyclerView(recyclerView);
        adapter = new ProductAdapter(view.getContext(), getChildFragmentManager());
        productsViewModel.refreshProduct(position, SharedPrefManager.getInstance(view.getContext()).getUser().getId());

        CenterZoomLayoutManager centerZoomLayoutManager = new CenterZoomLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(centerZoomLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.getLayoutManager().scrollToPosition(0);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                recyclerView.scrollBy(0, 1000);
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        if(newState == RecyclerView.SCROLL_STATE_IDLE){
//                            int position = centerZoomLayoutManager.findFirstVisibleItemPosition();
//                            MaterialCardView nextSliderDetail = null;
//                            Log.d("Position", position + "");
//                            MaterialCardView previousSliderDetail = null;
//                            if(position > 1) {
//                                previousSliderDetail = centerZoomLayoutManager.findViewByPosition(position - 1).findViewById(R.id.cardView);
//                                previousSliderDetail.setStrokeColor(getResources().getColor(R.color.colorPrimaryLight));
//                            }
//                            MaterialCardView constraintLayout = centerZoomLayoutManager.findViewByPosition(position).findViewById(R.id.cardView);
//                            constraintLayout.setStrokeColor(getResources().getColor(R.color.colorSecondary));
//                            FrameLayout constraintLayoutParent = (FrameLayout) centerZoomLayoutManager.findViewByPosition(position + 1);
//                            if(constraintLayoutParent != null){
//                                nextSliderDetail = constraintLayoutParent.findViewById(R.id.cardView);
//                            }
//                            if(previousSliderDetail != null){
//                                previousSliderDetail.setStrokeColor(getResources().getColor(R.color.colorPrimaryLight));
//                            }
//                            if(nextSliderDetail != null){
//                                nextSliderDetail.setStrokeColor(getResources().getColor(R.color.colorPrimaryLight));
//                            }
                        }
                    }
                });
            }
        });


        switch (position) {
            case 0:
                productsViewModel.electronicsProductCategory.observe(getViewLifecycleOwner(), new Observer<List<Current_Product>>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onChanged(List<Current_Product> current_products) {
                        current_products.sort(new sortTime());
                        adapter = new ProductAdapter(view.getContext(), getChildFragmentManager());
                        adapter.setHeroList(current_products);
                        recyclerView.setAdapter(adapter);
                    }
                });
                break;
            case 1:
                productsViewModel.appliancesCategory.observe(getViewLifecycleOwner(), new Observer<List<Current_Product>>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onChanged(List<Current_Product> current_products) {
                        current_products.sort(new sortTime());
                        adapter = new ProductAdapter(view.getContext(), getChildFragmentManager());
                        adapter.setHeroList(current_products);
                        recyclerView.setAdapter(adapter);
                    }
                });
                break;
            case 2:
                productsViewModel.accessoriesCategory.observe(getViewLifecycleOwner(), new Observer<List<Current_Product>>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onChanged(List<Current_Product> current_products) {
                        current_products.sort(new sortTime());
                        adapter = new ProductAdapter(view.getContext(), getChildFragmentManager());
                        adapter.setHeroList(current_products);
                        recyclerView.setAdapter(adapter);
                    }
                });
                break;
            case 3:
                productsViewModel.personalcareProductCategory.observe(getViewLifecycleOwner(), new Observer<List<Current_Product>>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onChanged(List<Current_Product> current_products) {
                        current_products.sort(new sortTime());
                        adapter = new ProductAdapter(view.getContext(), getChildFragmentManager());
                        adapter.setHeroList(current_products);
                        recyclerView.setAdapter(adapter);
                    }
                });
                break;
            case 4:
                productsViewModel.homeProductCategory.observe(getViewLifecycleOwner(), new Observer<List<Current_Product>>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onChanged(List<Current_Product> current_products) {
                        current_products.sort(new sortTime());
                        adapter = new ProductAdapter(view.getContext(), getChildFragmentManager());
                        adapter.setHeroList(current_products);
                        recyclerView.setAdapter(adapter);

                    }
                });
                break;
            case 5:
                productsViewModel.fitnessProductCategory.observe(getViewLifecycleOwner(), new Observer<List<Current_Product>>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onChanged(List<Current_Product> current_products) {
                        current_products.sort(new sortTime());
                        adapter = new ProductAdapter(view.getContext(), getChildFragmentManager());
                        adapter.setHeroList(current_products);
                        recyclerView.setAdapter(adapter);
                    }
                });
                break;
            case 6:
                productsViewModel.othersCategory.observe(getViewLifecycleOwner(), new Observer<List<Current_Product>>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onChanged(List<Current_Product> current_products) {
                        current_products.sort(new sortTime());
                        adapter = new ProductAdapter(view.getContext(), getChildFragmentManager());
                        adapter.setHeroList(current_products);
                        recyclerView.setAdapter(adapter);
                    }
                });
                break;
            case 7:
                productsViewModel.apparelProductCategory.observe(getViewLifecycleOwner(), new Observer<List<Current_Product>>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onChanged(List<Current_Product> current_products) {
                        current_products.sort(new sortTime());
                        adapter = new ProductAdapter(view.getContext(), getChildFragmentManager());
                        adapter.setHeroList(current_products);
                        recyclerView.setAdapter(adapter);
                    }
                });
                break;
            case 8:
                productsViewModel.freeBidsCategory.observe(getViewLifecycleOwner(), new Observer<List<Current_Product>>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onChanged(List<Current_Product> current_products) {
                        current_products.sort(new sortTime());
                        adapter = new ProductAdapter(view.getContext(), getChildFragmentManager());
                        adapter.setHeroList(current_products);
                        recyclerView.setAdapter(adapter);
                    }
                });
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        productsViewModel = new ViewModelProvider(this).get(ProductsViewModel.class);

        return view;
    }

    int position = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("position", 0);
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
