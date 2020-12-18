package com.example.bidbadnew.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bidbadnew.Model.WonItem;
import com.example.bidbadnew.Others.Symbol;
import com.example.bidbadnew.R;

public class PlaceOrderFragment extends Fragment {

    TextView subTotal, shippingCost, discount, total;
    Button placeOrder;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.bar).setVisibility(View.GONE);
        getActivity().findViewById(R.id.fabhome).setVisibility(View.GONE);
        View view = inflater.inflate(R.layout.fragment_place_order, container, false);

        placeOrder = view.findViewById(R.id.placeOrderButton);
        subTotal = view.findViewById(R.id.orderDeliverySubtotal);
        shippingCost = view.findViewById(R.id.orderDeliveryShippingFees);
        discount = view.findViewById(R.id.orderDeliveryDiscount);
        total = view.findViewById(R.id.orderDeliveryTotal);

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_placeOrderFragment_to_selectAddressFragment2);
            }
        });

        WonItem wonItem = (WonItem) getArguments().getSerializable("item");
        wonItem.getBidamount();
        total.setText(Symbol.rupee + (Integer.parseInt(wonItem.getBidamount())+50));
        subTotal.setText(Symbol.rupee + wonItem.getBidamount());
        shippingCost.setText(Symbol.rupee + "50");
        discount.setText(Symbol.rupee + "0");

        return view;
    }
}