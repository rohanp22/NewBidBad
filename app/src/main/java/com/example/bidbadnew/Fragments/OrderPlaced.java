package com.example.bidbadnew.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.airbnb.lottie.LottieAnimationView;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;
import com.example.bidbadnew.repositories.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderPlaced extends Fragment {

    LottieAnimationView lottieAnimationView;
    ProgressBar progressBar;
    Button continueBidding;
    String address;
    int productid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_placed, container, false);
        lottieAnimationView = view.findViewById(R.id.animation);
        progressBar = view.findViewById(R.id.progressBar);
        address = OrderPlacedArgs.fromBundle(getArguments()).getAddress();
        productid = OrderPlacedArgs.fromBundle(getArguments()).getProductid();
        continueBidding = view.findViewById(R.id.continueBidding);

        continueBidding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        Call call = RetrofitClient.getInstance().getMyApi().placeOrder(address, productid, "Order placed", SharedPrefManager.getInstance(view.getContext()).getUser().getId());
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                progressBar.setVisibility(View.GONE);
                lottieAnimationView.playAnimation();
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

        return view;
    }
}