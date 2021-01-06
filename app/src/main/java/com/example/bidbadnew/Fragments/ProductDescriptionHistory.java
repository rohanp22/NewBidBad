package com.example.bidbadnew.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bidbadnew.Adapter.AllBidsAdapter;
import com.example.bidbadnew.Model.PastProducts;
import com.example.bidbadnew.R;

public class ProductDescriptionHistory extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_product_description_history, container, false);

        return view;
    }
}