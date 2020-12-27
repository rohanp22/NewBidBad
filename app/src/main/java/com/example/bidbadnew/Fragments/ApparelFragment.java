package com.example.bidbadnew.Fragments;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bidbadnew.Adapter.ProductAdapter1;
import com.example.bidbadnew.Model.Current_Product;
import com.example.bidbadnew.Others.CenterZoomLayoutManager;
import com.example.bidbadnew.Others.ProductLayoutDecoration;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ApparelFragment extends Fragment {

    private ApparelViewModel mViewModel;
    ProductAdapter1 adapter;
    RecyclerView viewPager2;

    public static ApparelFragment newInstance() {
        return new ApparelFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.apparel_fragment, container, false);

        viewPager2 = view.findViewById(R.id.recyclerView);
        viewPager2.addItemDecoration(new ProductLayoutDecoration());
        viewPager2.requestDisallowInterceptTouchEvent(true);
        new PagerSnapHelper().attachToRecyclerView(viewPager2);

        mViewModel = new ViewModelProvider(this).get(ApparelViewModel.class);

        mViewModel.init(SharedPrefManager.getInstance(view.getContext()).getUser().getId());
        mViewModel.getCurrent_productsmutable().observe(getViewLifecycleOwner(), new Observer<List<Current_Product>>() {
            @SuppressLint("UseCompatLoadingForColorStateLists")
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<Current_Product> current_products) {
                current_products.sort(new sortTime());
                adapter = new ProductAdapter1(view.getContext(), current_products, getChildFragmentManager());

                viewPager2.setClipToPadding(false);
                CenterZoomLayoutManager centerZoomLayoutManager = new CenterZoomLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
                viewPager2.setLayoutManager(centerZoomLayoutManager);
                viewPager2.setAdapter(adapter);
                viewPager2.scrollTo(100, 0);
                centerZoomLayoutManager.scrollHorizontallyBy(50, null, null);

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
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