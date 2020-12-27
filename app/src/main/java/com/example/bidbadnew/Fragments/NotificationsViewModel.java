package com.example.bidbadnew.Fragments;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bidbadnew.Model.NotificationModel;
import com.example.bidbadnew.repositories.NotificationsRepo;

import java.util.List;

public class NotificationsViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<List<NotificationModel>> notificationItems;

    public void init(int id){
        if(notificationItems != null){
            return;
        }
        NotificationsRepo notificationsRepo = NotificationsRepo.getInstance();
        notificationItems = notificationsRepo.getMyNotifications(id);
    }

    public MutableLiveData<List<NotificationModel>> getNotifications() {
        return notificationItems;
    }

}