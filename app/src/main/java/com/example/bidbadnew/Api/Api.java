package com.example.bidbadnew.Api;

import com.example.bidbadnew.Model.Current_Products;
import com.example.bidbadnew.Model.MyBids;
import com.example.bidbadnew.Model.OngoingItems;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    String BASE_URL = "http://easyvela.esy.es/AndroidAPI/";

    /**
     * The return type is important here
     * The class structure that you've defined in Call<T>
     * should exactly match with your json response
     * If you are not using another api, and using the same as mine
     * then no need to worry, but if you have your own API, make sure
     * you change the return type appropriately
     **/
    @GET("currentproducts.php/")
    Call<Current_Products> getCurrentProducts();

    @GET("getmybids.php/")
    Call<MyBids> getMyBids(@Query("id") int id);

    @GET("getmyongoingbids.php/")
    Call<List<OngoingItems>> getMyOngoingBids(@Query("id") int id);

}