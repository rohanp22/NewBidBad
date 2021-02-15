package com.example.bidbadnew.Fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bidbadnew.Adapter.WishListAdapter;
import com.example.bidbadnew.Model.WishListItem;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;

import java.util.List;

public class Wishlist extends Fragment {

    private WishlistViewModel mViewModel;
    RecyclerView recyclerView, recyclerViewPast;

    public static Wishlist newInstance() {
        return new Wishlist();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getActivity().findViewById(R.id.bar).setVisibility(View.GONE);
        getActivity().findViewById(R.id.fabhome).setVisibility(View.GONE);

        View view = inflater.inflate(R.layout.wishlist_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerViewPast = view.findViewById(R.id.recyclerViewPast);
        mViewModel = new ViewModelProvider(this).get(WishlistViewModel.class);
        mViewModel.init(SharedPrefManager.getInstance(view.getContext()).getUser().getId());
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerViewPast.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mViewModel.getMyBids().observe(getViewLifecycleOwner(), new Observer<List<WishListItem>>() {
            @Override
            public void onChanged(List<WishListItem> wishListItems) {
                if(wishListItems != null) {
                    recyclerView.setAdapter(new WishListAdapter(wishListItems, view.getContext()));
                }
            }
        });

        mViewModel.getWishListPast().observe(getViewLifecycleOwner(), new Observer<List<WishListItem>>() {
            @Override
            public void onChanged(List<WishListItem> wishListItems) {
                if(wishListItems != null) {
                    recyclerViewPast.setAdapter(new WishListAdapter(wishListItems, view.getContext()));
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

}