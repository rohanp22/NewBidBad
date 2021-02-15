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

import com.example.bidbadnew.Adapter.WalletAdapter;
import com.example.bidbadnew.Model.Transaction;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class ViewAllTransactions extends Fragment {

    private ViewAllTransactionsViewModel mViewModel;
    RecyclerView recyclerView;
    MaterialToolbar materialToolbar;

    public static ViewAllTransactions newInstance() {
        return new ViewAllTransactions();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_all_transactions_fragment, container,
                false);
        recyclerView = view.findViewById(R.id.recyclerView);
        mViewModel = new ViewModelProvider(this).get(ViewAllTransactionsViewModel.class);

        materialToolbar = view.findViewById(R.id.toolbar);
        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });
        mViewModel.init(SharedPrefManager.getInstance(view.getContext()).getUser().getId());
        mViewModel.getMyWonItems().observe(getViewLifecycleOwner(), new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                if(transactions != null) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    recyclerView.setAdapter(new WalletAdapter(view.getContext(), transactions, transactions.size()));
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