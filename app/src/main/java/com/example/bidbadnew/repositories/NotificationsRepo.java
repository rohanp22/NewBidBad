package com.example.bidbadnew.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.bidbadnew.Model.MyBid;
import com.example.bidbadnew.Model.MyBids;
import com.example.bidbadnew.Model.NotificationModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsRepo {

    public static NotificationsRepo notificationsRepo;

    public static NotificationsRepo getInstance(){
        if(notificationsRepo == null){
            notificationsRepo = new NotificationsRepo();
        }
        return notificationsRepo;
    }

    public MutableLiveData<List<NotificationModel>> getMyNotifications(int id) {
        MutableLiveData<List<NotificationModel>> myNotifs = new MutableLiveData<>();
        Call<List<NotificationModel>> call = RetrofitClient.getInstance().getMyApi().getNotifications(id);
        call.enqueue(new Callback<List<NotificationModel>>() {
            @Override
            public void onResponse(Call<List<NotificationModel>> call, Response<List<NotificationModel>> response) {
                myNotifs.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<NotificationModel>> call, Throwable t) {

            }
        });

        return myNotifs;
    }

}
