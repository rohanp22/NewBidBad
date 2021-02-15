package com.example.bidbadnew.Fragments;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bidbadnew.Model.Transaction;
import com.example.bidbadnew.repositories.TransactionsRepo;

import java.util.List;

public class ViewAllTransactionsViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<List<Transaction>> wonItems;
    int id;

    public void init(int id){
        this.id = id;
        if(wonItems != null){
            return;
        }
        TransactionsRepo wonItemsRepo = TransactionsRepo.getInstance();
        wonItems = wonItemsRepo.getTransactions(id);
    }

    public MutableLiveData<List<Transaction>> getMyWonItems() {
        return wonItems;
    }
}