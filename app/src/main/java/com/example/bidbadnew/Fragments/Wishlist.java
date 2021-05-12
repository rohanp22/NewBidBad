package com.example.bidbadnew.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bidbadnew.Adapter.WishListAdapter;
import com.example.bidbadnew.Model.WishListItem;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;
import com.google.android.material.appbar.MaterialToolbar;

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
        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        mViewModel.getMyBids().observe(getViewLifecycleOwner(), new Observer<List<WishListItem>>() {
            @Override
            public void onChanged(List<WishListItem> wishListItems) {
                if(wishListItems != null) {
                    if (wishListItems.isEmpty()) {
                        view.findViewById(R.id.text).setVisibility(View.GONE);
                    }
                    recyclerView.setAdapter(new WishListAdapter(wishListItems, view.getContext()));
                }
            }
        });

        mViewModel.getWishListPast().observe(getViewLifecycleOwner(), new Observer<List<WishListItem>>() {
            @Override
            public void onChanged(List<WishListItem> wishListItems) {
                if(wishListItems != null) {
                    if (wishListItems.isEmpty()) {
                        view.findViewById(R.id.text2).setVisibility(View.GONE);
                    }
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