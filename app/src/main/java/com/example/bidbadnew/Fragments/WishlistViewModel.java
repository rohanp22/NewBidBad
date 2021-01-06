package com.example.bidbadnew.Fragments;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bidbadnew.Model.MyBid;
import com.example.bidbadnew.Model.WishListItem;
import com.example.bidbadnew.repositories.MyBidsRepo;
import com.example.bidbadnew.repositories.WishListRepo;

import java.util.List;

public class WishlistViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    WishListRepo wishListRepo;
    private MutableLiveData<List<WishListItem>> wishList;

    public void init(int id){
        if (wishList != null) {
            return;
        }
        wishListRepo = WishListRepo.getInstance();
        wishList = wishListRepo.getWishList(id);
    }

    public MutableLiveData<List<WishListItem>> getMyBids() {
        return wishList;
    }

}