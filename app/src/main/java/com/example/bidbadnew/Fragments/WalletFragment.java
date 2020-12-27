package com.example.bidbadnew.Fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bidbadnew.Adapter.WalletAdapter;
import com.example.bidbadnew.Model.Balance;
import com.example.bidbadnew.Model.Transaction;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;
import com.example.bidbadnew.repositories.RetrofitClient;
import com.google.android.material.appbar.MaterialToolbar;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletFragment extends Fragment {

    private WalletViewModel mViewModel;
    TextView balance, freebids, bonus;
    RecyclerView recyclerview;

    public static WalletFragment newInstance() {
        return new WalletFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getActivity().findViewById(R.id.fabhome).setVisibility(View.GONE);
        getActivity().findViewById(R.id.bar).setVisibility(View.GONE);

        View view =  inflater.inflate(R.layout.wallet_fragment, container, false);

        balance = view.findViewById(R.id.balance);
        freebids = view.findViewById(R.id.freebidscount);
        bonus = view.findViewById(R.id.bonuspointscount);
        recyclerview = view.findViewById(R.id.walletTransactions);
        mViewModel = new ViewModelProvider(this).get(WalletViewModel.class);
        mViewModel.init(SharedPrefManager.getInstance(view.getContext()).getUser().getId());
        recyclerview.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mViewModel.getMyWonItems().observe(getViewLifecycleOwner(), new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                recyclerview.setAdapter(new WalletAdapter(view.getContext(), transactions));
            }
        });

        Call<Balance> call = RetrofitClient.getInstance().getMyApi().getBalance(SharedPrefManager.getInstance(view.getContext()).getUser().getId());
        call.enqueue(new Callback<Balance>() {
            @Override
            public void onResponse(Call<Balance> call, Response<Balance> response) {
                balance.setText(new DecimalFormat("##,##,##0").format(Integer.parseInt(response.body().getBalance())));
            }

            @Override
            public void onFailure(Call<Balance> call, Throwable t) {

            }
        });

        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });
        view.findViewById(R.id.addmoney).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_walletFragment_to_addMoneyFragment);
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