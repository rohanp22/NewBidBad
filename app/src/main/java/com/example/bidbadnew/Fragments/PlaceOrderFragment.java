package com.example.bidbadnew.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.bidbadnew.Activities.MainActivity;
import com.example.bidbadnew.Model.Balance;
import com.example.bidbadnew.Model.WonItem;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.Others.Symbol;
import com.example.bidbadnew.R;
import com.example.bidbadnew.repositories.CurrentProductsRepo;
import com.example.bidbadnew.repositories.RetrofitClient;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceOrderFragment extends Fragment {

    TextView subTotal, balance, shippingCost, discount, total, address, title;
    ImageView imageView;
    Button placeOrder, selectAddress;
    WonItem wonItem;
    int id;
    int bal;
    String addressText;
    PlaceOrderViewModel placeOrderViewModel;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        requireActivity().findViewById(R.id.bar).setVisibility(View.GONE);
        requireActivity().findViewById(R.id.fabhome).setVisibility(View.GONE);
        View view = inflater.inflate(R.layout.fragment_place_order, container, false);
        wonItem = PlaceOrderFragmentArgs.fromBundle(getArguments()).getItem();

        imageView = view.findViewById(R.id.imageview);
        title = view.findViewById(R.id.title);
        placeOrder = view.findViewById(R.id.placeOrderButton);
        address = view.findViewById(R.id.address);
        subTotal = view.findViewById(R.id.orderDeliverySubtotal);
        selectAddress = view.findViewById(R.id.selectAddressButton);
        shippingCost = view.findViewById(R.id.orderDeliveryShippingFees);
        discount = view.findViewById(R.id.orderDeliveryDiscount);
        total = view.findViewById(R.id.orderDeliveryTotal);
        balance = view.findViewById(R.id.balance);

        wonItem.getBidamount();
        total.setText((Integer.parseInt(wonItem.getBidamount()) + 50) + "");
        subTotal.setText(wonItem.getBidamount());
        shippingCost.setText("50");
        discount.setText("0");

        Glide.with(view.getContext())
                .load(wonItem.getImageUrl())
                .into(imageView);
        title.setText(wonItem.getTitle());

        Call<String> call1 = RetrofitClient.getInstance().getMyApi().getDefaultAddress(SharedPrefManager.getInstance(getContext()).getUser().getId());
        call1.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call1, Response<String> response) {
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());
                        addressText = response.body();
                        address.setText(response.body());
                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call1, Throwable t) {

            }
        });

//        try {
//            addressText = getArguments().getString("address");
//            if(addressText != null) {
//                address.setText("Deliver to: \n" + addressText);
//            }
//        } catch (Exception e){
//
//        }
        Call<Balance> call = RetrofitClient.getInstance().getMyApi().getBalance(SharedPrefManager.getInstance(view.getContext()).getUser().getId());
        call.enqueue(new Callback<Balance>() {
            @Override
            public void onResponse(@NotNull Call<Balance> call, @NotNull Response<Balance> response) {
                assert response.body() != null;
                balance.setText("₹" + new DecimalFormat("##,##,##0").format(Integer.parseInt(response.body().getBalance())));
            }

            @Override
            public void onFailure(@NotNull Call<Balance> call, Throwable t) {
                Log.d("Error", Objects.requireNonNull(t.getMessage()));
            }
        });

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!addressText.isEmpty()) {
                    id = SharedPrefManager.getInstance(view.getContext()).getUser().getId();
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://easyvela.esy.es/AndroidAPI/checkbalance.php?id=" + id,
                            new com.android.volley.Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject obj = new JSONObject(response);
                                        bal = Integer.parseInt(obj.getString("balance"));
                                        if (bal > (Integer.parseInt(wonItem.getBidamount()) + 50)) {
                                            StringRequest stringRequest2 = new StringRequest(Request.Method.GET, "http://easyvela.esy.es/AndroidAPI/updatewallet.php?id=" + id + "&value=" + (Integer.parseInt(wonItem.getBidamount()) + 50) * -1 + "&type=Paid to Place order for " + wonItem.getTitle() + "&image_url=" + wonItem.getImageUrl(),
                                                    new com.android.volley.Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            Navigation.findNavController(view).navigate(PlaceOrderFragmentDirections.actionPlaceOrderFragmentToOrderPlaced(Integer.parseInt(wonItem.getPastId()), addressText, wonItem.getBidamount()));
                                                        }
                                                    },
                                                    new com.android.volley.Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

                                            RequestQueue requestQueue2 = Volley.newRequestQueue(getActivity());
                                            requestQueue2.add(stringRequest2);
                                        } else {
                                            Toast.makeText(view.getContext(), "Insuffecient Balance", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new com.android.volley.Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });

                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    requestQueue.add(stringRequest);
                } else {
                    Toast.makeText(getContext(), "Enter a address", Toast.LENGTH_LONG);
                }
            }
        });

        selectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putSerializable("item", wonItem);
                Navigation.findNavController(view).navigate(R.id.action_placeOrderFragment_to_selectAddressFragment2, b);
            }
        });

        return view;
    }
}