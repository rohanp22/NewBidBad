package com.example.bidbadnew.Fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bidbadnew.Adapter.MyBidsAdapter;
import com.example.bidbadnew.Model.MyBid;
import com.example.bidbadnew.Model.MyBids;
import com.example.bidbadnew.Model.PastProducts;
import com.example.bidbadnew.R;
import com.example.bidbadnew.Others.SharedPrefManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MyBidsFragment extends Fragment implements MyBidsAdapter.MyBidsAdapterListener {

    RecyclerView cartList;
    String bid;
    MyBidsAdapter adapterall;
    MyBidsViewModel myBidsViewModel;

    MyBidsFragmentListener myBidsFragmentListener;

    public MyBidsFragment(MyBidsFragmentListener allBidsFragmentListener){
        this.myBidsFragmentListener = allBidsFragmentListener;
    }

    @Override
    public void onItemClickListener(MyBid pastProduct) {
        myBidsFragmentListener.onItemClick(pastProduct);
    }

    public interface MyBidsFragmentListener {
        public void onItemClick(MyBid pastProducts);
    }

    public MyBidsFragment(String bid) {
        this.bid = bid;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.bar).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.fabhome).setVisibility(View.VISIBLE);
        myBidsViewModel = new ViewModelProvider(this).get(MyBidsViewModel.class);
        final View view = inflater.inflate(R.layout.bid_history_fragments, container, false);

        myBidsViewModel.init(SharedPrefManager.getInstance(view.getContext()).getUser().getId());

        cartList = view.findViewById(R.id.bidHistoryRecyclerView);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), RecyclerView.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        cartList.addItemDecoration(dividerItemDecoration);
        cartList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        Context context;
        List<MyBid> heroList;

        myBidsViewModel.getMyBids().observe(getViewLifecycleOwner(), new Observer<List<MyBid>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<MyBid> myBids) {
                myBids.sort(new sortTime());
                adapterall = new MyBidsAdapter(getContext(), myBids, MyBidsFragment.this);
                cartList.setAdapter(adapterall);
            }
        });

        return view;
    }

    class sortTime implements Comparator<MyBid> {
        // Used for sorting in ascending order of
        // roll number
        public int compare(MyBid a, MyBid b) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
            Date a1 = null, b1 = null;
            try {
                a1 = simpleDateFormat.parse(a.getEndtime());
                b1 = simpleDateFormat.parse(b.getEndtime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return b1.compareTo(a1);
        }
    }
}