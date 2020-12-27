package com.example.bidbadnew.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.bidbadnew.Model.MyBid;
import com.example.bidbadnew.Model.MyBids;
import com.example.bidbadnew.Model.Transaction;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionsRepo {

    public static TransactionsRepo transactionsRepo;

    public static TransactionsRepo getInstance(){
        if(transactionsRepo == null){
            transactionsRepo = new TransactionsRepo();
        }
        return transactionsRepo;
    }

    public static List<Transaction> myBidsList;

    public MutableLiveData<List<Transaction>> getTransactions(int id) {
        MutableLiveData<List<Transaction>> myBids = new MutableLiveData<>();
        Call<List<Transaction>> call = RetrofitClient.getInstance().getMyApi().getTransactions(id);
        call.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                myBidsList = response.body();
                myBids.postValue(myBidsList);
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {

            }
        });
        return myBids;
    }

}
