package com.example.bidbadnew.Fragments;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.bidbadnew.Adapter.OrderAdapter;
import com.example.bidbadnew.Model.Current_Product;
import com.example.bidbadnew.Model.Order;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;
import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class OrdersFragment extends Fragment {

    private OrdersViewModel mViewModel;
    View view;

    public static OrdersFragment newInstance() {
        return new OrdersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getActivity().findViewById(R.id.bar).setVisibility(View.GONE);
        getActivity().findViewById(R.id.fabhome).setVisibility(View.GONE);

        view = inflater.inflate(R.layout.orders_fragment, container, false);
        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });
        pastList = (RecyclerView) view.findViewById(R.id.my_order_recyclerview);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(OrdersViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.init(SharedPrefManager.getInstance(view.getContext()).getUser().getId());
        mViewModel.getOrder().observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    orders.sort(new sortTime());
                }
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), RecyclerView.VERTICAL);
                dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
                pastList.addItemDecoration(dividerItemDecoration);
                pastList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                pastList.setAdapter(new OrderAdapter(view.getContext(), orders));
            }
        });
    }

    public ArrayList<Order> pastItems;
    RecyclerView pastList;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    class sortTime implements Comparator<Order> {
        public int compare(Order a, Order b) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy h:m a");
            //May 24, 2020, 7:49 pm
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