package com.example.bidbadnew.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bidbadnew.Adapter.ProductDetailsAdapter;
import com.example.bidbadnew.R;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class ProductDetails extends Fragment {

    RecyclerView recyclerView;
    String tabName;
    ArrayList<String> details;
    String des;

    public ProductDetails(String tabName, String description){
        this.tabName = tabName;
        this.des = description;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);

        recyclerView = view.findViewById(R.id.productDetailsRecyclerview);

        ArrayList<String> productDetails = new ArrayList<>();
        ArrayList<String> deliveryDetails = new ArrayList<>();

        details = new ArrayList<>();
        // Inflate the layout for this fragment

        StringTokenizer defaultTokenizer = new StringTokenizer(des, "\n");

        System.out.println("Total number of tokens found : " + defaultTokenizer.countTokens());

        while (defaultTokenizer.hasMoreTokens())
        {
            String t = defaultTokenizer.nextToken();
            details.add(t);
            Log.d("TOken", t);
        }

        productDetails.add("This product height is 20cms.");
        productDetails.add("Warranty of 2 months");
        productDetails.add("Extra bass");

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        if(tabName.equals("productdetails")) {
            recyclerView.setAdapter(new ProductDetailsAdapter(details, view.getContext()));
        } else {
            recyclerView.setAdapter(new ProductDetailsAdapter(productDetails, view.getContext()));
        }
        return view;
    }
}