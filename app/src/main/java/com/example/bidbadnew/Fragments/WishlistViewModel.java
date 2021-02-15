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
    private MutableLiveData<List<WishListItem>> wishListPast;

    public void init(int id){
        if (wishList != null) {
            return;
        }
        wishListRepo = WishListRepo.getInstance();
        wishList = wishListRepo.getWishList(id);
        wishListPast = wishListRepo.getWishListPast(id);
    }

    public MutableLiveData<List<WishListItem>> getMyBids() {
        return wishList;
    }

    public MutableLiveData<List<WishListItem>> getWishListPast() {
        return wishListPast;
    }
}