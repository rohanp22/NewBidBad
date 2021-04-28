package com.example.bidbadnew.Fragments;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bidbadnew.Adapter.WalletAdapter;
import com.example.bidbadnew.Model.Balance;
import com.example.bidbadnew.Model.FreeBid;
import com.example.bidbadnew.Model.PastProducts;
import com.example.bidbadnew.Model.Transaction;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;
import com.example.bidbadnew.repositories.RetrofitClient;
import com.google.android.material.appbar.MaterialToolbar;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletFragment extends Fragment {

    private WalletViewModel mViewModel;
    TextView balance, freebids, bonus;
    RecyclerView recyclerview;
    TextView viewall;

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
        viewall = view.findViewById(R.id.viewalltext);
        bonus = view.findViewById(R.id.rewardcount);
        recyclerview = view.findViewById(R.id.walletTransactions);
        mViewModel = new ViewModelProvider(this).get(WalletViewModel.class);
        mViewModel.init(SharedPrefManager.getInstance(view.getContext()).getUser().getId());
        recyclerview.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mViewModel.getMyWonItems().observe(getViewLifecycleOwner(), new Observer<List<Transaction>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<Transaction> transactions) {
                if(transactions != null) {
                    transactions.sort(new sortTime());
                    recyclerview.setAdapter(new WalletAdapter(view.getContext(), transactions, transactions.size() < 4 ? transactions.size() : 4));
                }
            }
        });

        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_walletFragment_to_viewAllTransactions);
            }
        });

        Call<Balance> call = RetrofitClient.getInstance().getMyApi().getBalance(SharedPrefManager.getInstance(view.getContext()).getUser().getId());
        call.enqueue(new Callback<Balance>() {
            @Override
            public void onResponse(Call<Balance> call, Response<Balance> response) {
                if(response.body() != null) {
                    balance.setText(new DecimalFormat("##,##,##0").format(Integer.parseInt(response.body().getBalance())));
                }
            }

            @Override
            public void onFailure(Call<Balance> call, Throwable t) {

            }
        });

        Call<List<FreeBid>> call1 = RetrofitClient.getInstance().getMyApi().getFreeBids(SharedPrefManager.getInstance(getContext()).getUser().getId());
        call1.enqueue(new Callback<List<FreeBid>>() {
            @Override
            public void onResponse(Call<List<FreeBid>> call, Response<List<FreeBid>> response) {
                if(response.body() != null)
                    freebids.setText(response.body().size()+"");
            }

            @Override
            public void onFailure(Call<List<FreeBid>> call, Throwable t) {

            }
        });

        Call<String> rewardPointsCall = RetrofitClient.getInstance().getMyApi().getRewardPoints(SharedPrefManager.getInstance(getContext()).getUser().getId());
        rewardPointsCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body() != null) {
                    Log.d("Response", response.body().toString() + " ");
                    bonus.setText(response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
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

    class sortTime implements Comparator<Transaction> {
        // Used for sorting in ascending order of
        // roll number
        public int compare(Transaction a, Transaction b) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM d, yyyy, h:mm a");
            Date a1 = null, b1 = null;
            try {
                a1 = simpleDateFormat.parse(a.getDate());
                b1 = simpleDateFormat.parse(b.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return b1.compareTo(a1);
        }
    }

}