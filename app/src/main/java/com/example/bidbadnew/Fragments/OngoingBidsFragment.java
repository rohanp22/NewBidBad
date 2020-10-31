package com.example.bidbadnew.Fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bidbadnew.Adapter.OngoingBidsAdapter;
import com.example.bidbadnew.Model.CartItems;
import com.example.bidbadnew.Model.OngoingItems;
import com.example.bidbadnew.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class OngoingBidsFragment extends Fragment {

    private RecyclerView cartList;
    private OngoingViewModel ongoingViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ongoingViewModel = new ViewModelProvider(this).get(OngoingViewModel.class);
        ongoingViewModel.init();
        View view = inflater.inflate(R.layout.fragment_my_bids, container, false);
        cartList = view.findViewById(R.id.myBidsList);

        ongoingViewModel.getCurrent_productsmutable().observe(getViewLifecycleOwner(), new Observer<List<OngoingItems>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<OngoingItems> ongoingItems) {
                ongoingItems.sort(new sortTime());
                OngoingBidsAdapter walletAdapter = new OngoingBidsAdapter(view.getContext(), ongoingItems);
                cartList.setAdapter(walletAdapter);
                cartList.setLayoutManager(new LinearLayoutManager(view.getContext()));
                view.findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    class sortTime implements Comparator<OngoingItems> {

        public int compare(OngoingItems a, OngoingItems b) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
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
