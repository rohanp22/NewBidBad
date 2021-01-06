package com.example.bidbadnew.Fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bidbadnew.Model.Order;
import com.example.bidbadnew.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderSummary extends Fragment {

    private OrderSummaryViewModel mViewModel;
    TextView title, address, orderid, date, status, datestatus, total, shippingcharges, subtotal;
    ImageView imageView;
    Date startedAt;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_summary_fragment, container, false);
        mViewModel = new ViewModelProvider(this).get(OrderSummaryViewModel.class);
        Order order = (Order) getArguments().getSerializable("order");
        title = view.findViewById(R.id.summaryOrderTitle);
        address = view.findViewById(R.id.summaryOrderAddress);
        orderid = view.findViewById(R.id.summaryOrderId);
        date = view.findViewById(R.id.summaryOrderDate);
        datestatus = view.findViewById(R.id.summaryOrderTrackingDate);
        status = view.findViewById(R.id.summaryOrderTrackingText);
        subtotal = view.findViewById(R.id.summaryOrderMerchandiseAmount);
        shippingcharges = view.findViewById(R.id.summaryOrderShippingAmount);
        total = view.findViewById(R.id.summaryOrderShippingTotalAmount);
        imageView = view.findViewById(R.id.summaryOrderImage);
        Glide.with(view.getContext())
                .load(order.getImageUrl())
                .into(imageView);

        SimpleDateFormat startedAtFormat = new SimpleDateFormat("MMMM dd, yyyy, h:mm a");
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM, hh:mm a");

        try {
            startedAt = startedAtFormat.parse(order.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        date.setText(dateFormat.format(startedAt));
        address.setText(order.getAddress());
        status.setText(order.getStatus());
        subtotal.setText(order.getBidamount());
        total.setText(order.getBidamount());
        shippingcharges.setText("50");
        orderid.setText("Order id: "+order.getOid());

        title.setText(order.getTitle());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

}