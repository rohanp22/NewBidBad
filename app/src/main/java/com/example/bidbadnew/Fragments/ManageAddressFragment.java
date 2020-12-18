package com.example.bidbadnew.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bidbadnew.Model.Address;
import com.example.bidbadnew.Model.AddressModel;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;
import com.example.bidbadnew.repositories.RetrofitClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.radiobutton.MaterialRadioButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageAddressFragment extends Fragment {

    MaterialRadioButton materialRadioButton1, materialRadioButton2, materialRadioButton3;
    MaterialCardView card1, card2, card3;
    String a1, a2, a3, a4;
    TextView addressField1, addressField2, addressField3;
    int addresscount = 0;
    ImageView overflow, overflow2, overflow3;
    Toolbar toolbar;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_manage_address, container, false);
        card1 = view.findViewById(R.id.card1);
        card2 = view.findViewById(R.id.card2);
        materialRadioButton1 = view.findViewById(R.id.radio1);
        materialRadioButton2 = view.findViewById(R.id.radio2);
        card3 = view.findViewById(R.id.card3);
        toolbar = view.findViewById(R.id.toolbar);
        materialRadioButton3 = view.findViewById(R.id.radio3);

        addressField1 = view.findViewById(R.id.address1);
        addressField2 = view.findViewById(R.id.address2);
        addressField3 = view.findViewById(R.id.address3);
        toolbar.inflateMenu(R.menu.manageaddressmenu);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.addaddress:
                    if(card1.getVisibility() == View.GONE){
                        AddressDialog.display(getChildFragmentManager(), 1);
                    } else if(card2.getVisibility() == View.GONE){
                        AddressDialog.display(getChildFragmentManager(), 2);
                    } else if(card3.getVisibility() == View.GONE){
                        AddressDialog.display(getChildFragmentManager(), 3);
                    } else {
                        Toast.makeText(getContext(), "No more addresses can be added", Toast.LENGTH_LONG).show();
                    }
                    break;
                }

                return false;
            }
        });

        Call<AddressModel> addressCall = RetrofitClient.getInstance().getMyApi().getMyAddresses(SharedPrefManager.getInstance(view.getContext()).getUser().getId());
        addressCall.enqueue(new Callback<AddressModel>() {
            @Override
            public void onResponse(Call<AddressModel> call, Response<AddressModel> response) {
                AddressModel addressModel = response.body();
                Address add = addressModel.getAddress().get(0);
                a1 = add.getAddress();
                a2 = add.getAddress2();
                a3 = add.getAddress3();
                //a4 = heroObject.getString("address4");

                if (add.getAddress() != null && !add.getAddress().equals("") && !add.getAddress().equals("null")) {
                    addressField1.setText(a1);
                    card1.setVisibility(View.VISIBLE);
                    addresscount++;
                }

                if (add.getAddress2() != null && !add.getAddress2().equals("") && !add.getAddress2().equals("null")) {
                    addressField2.setText(a2);
                    card2.setVisibility(View.VISIBLE);
                    addresscount++;
                }

                if (add.getAddress3() != null && !add.getAddress3().equals("") && !add.getAddress3().equals("null")) {
                    addressField3.setText(a3);
                    card3.setVisibility(View.VISIBLE);
                    addresscount++;
                }
            }

            @Override
            public void onFailure(Call<AddressModel> call, Throwable t) {

            }
        });

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialRadioButton1.setChecked(true);
                materialRadioButton2.setChecked(false);
                materialRadioButton3.setChecked(false);
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialRadioButton2.setChecked(true);
                materialRadioButton1.setChecked(false);
                materialRadioButton3.setChecked(false);
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialRadioButton3.setChecked(true);
                materialRadioButton2.setChecked(false);
                materialRadioButton1.setChecked(false);
            }
        });

        overflow = view.findViewById(R.id.overflow);
        overflow2 = view.findViewById(R.id.overflow2);
        overflow3 = view.findViewById(R.id.overflow3);

        overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(overflow, 1);
            }
        });

        overflow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(overflow2, 2);
            }
        });


        overflow3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(overflow3, 3);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Resume", "hello");
    }

    public void showPopup(ImageView overflow, int addressNumber) {
        PopupMenu popup = new PopupMenu(view.getContext(), overflow);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.actions, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.edit :
                        Toast.makeText(view.getContext(), "Edit" + addressNumber, Toast.LENGTH_SHORT).show();
                        AddressDialog.display(getChildFragmentManager(), addressNumber);
                        break;

                    case R.id.delete:
                        Toast.makeText(view.getContext(), "Delete" + addressNumber, Toast.LENGTH_SHORT).show();
                        Call<Void> addressCall = RetrofitClient.getInstance().getMyApi().addAddress("", addressNumber+"", SharedPrefManager.getInstance(getContext()).getUser().getId());
                        addressCall.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {

                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                        break;
                }
                return true;
            }
        });
    }

}