package com.example.bidbadnew.Api;

import com.example.bidbadnew.Model.AddressModel;
import com.example.bidbadnew.Model.Balance;
import com.example.bidbadnew.Model.Current_Products;
import com.example.bidbadnew.Model.LoginResponse;
import com.example.bidbadnew.Model.MyBids;
import com.example.bidbadnew.Model.OngoingItems;
import com.example.bidbadnew.Model.Order;
import com.example.bidbadnew.Model.SignupResponse;
import com.example.bidbadnew.Model.WonItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    Call<Current_Products> getCurrentProducts(@Query("id") int id);

    @GET("currentproducts.php/")
    Call<Current_Products> getCurrentProducts(@Query("id") int id, @Query("category") String category);

    @GET("getaddresses.php/")
    Call<AddressModel> getMyAddresses(@Query("id") int id);

    @GET("getmybids.php/")
    Call<MyBids> getMyBids(@Query("id") int id);

    @GET("getmyongoingbids.php/")
    Call<List<OngoingItems>> getMyOngoingBids(@Query("id") int id);

    @GET("getwonitems.php/")
    Call<List<WonItem>> getMyWins(@Query("id") int id);

    @GET("orders.php/")
    Call<List<Order>> getMyOrders(@Query("id") int id);

    @GET("checkbalance.php/")
    Call<Balance> getBalance(@Query("id") int id);

    @GET("updatewallet.php/")
    Call<String> updateBalance(@Query("id") int id, @Query("type") String type, @Query("value") String value, @Query("image_url") String image_url);

    @FormUrlEncoded
    @POST("addaddress.php/")
    Call<Void> addAddress(@Field("address") String address, @Field("addressno") String addressNumber, @Query("id") int id);

    @FormUrlEncoded
    @POST("Login/Api.php/")
    Call<LoginResponse> login(@Field("mobile") String mobile, @Field("password") String password, @Query("apicall") String apicall);

    @FormUrlEncoded
    @POST("Login/Api.php/")
    Call<SignupResponse> signup(@Field("mobile") String mobile, @Field("firstname") String name, @Query("apicall") String apicall, @Field("email") String email, @Field("password") String password);

    @GET("isuserregistered.php/")
    Call<Integer> isUserRegistered(@Query("mobile") String mobile);
}