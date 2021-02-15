package com.example.bidbadnew.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bidbadnew.Model.Balance;
import com.example.bidbadnew.Model.Order;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;
import com.example.bidbadnew.repositories.RetrofitClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMoneyFragment extends Fragment {

    TextInputEditText editTextAmount;
    MaterialButton a100, a200, a500;
    TextInputLayout textInputLayout;
    TextView balance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_money, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });
        balance = view.findViewById(R.id.balance);

        Call<Balance> call = RetrofitClient.getInstance().getMyApi().getBalance(SharedPrefManager.getInstance(view.getContext()).getUser().getId());
        call.enqueue(new Callback<Balance>() {
            @Override
            public void onResponse(Call<Balance> call, Response<Balance> response) {
                if (response.body() != null)
                    balance.setText("₹" + response.body().getBalance());
            }

            @Override
            public void onFailure(Call<Balance> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

        MaterialButton addmoney = view.findViewById(R.id.addmoney);
        editTextAmount = view.findViewById(R.id.amount);
        a100 = view.findViewById(R.id.a100);
        a200 = view.findViewById(R.id.a200);
        a500 = view.findViewById(R.id.a500);
        textInputLayout = view.findViewById(R.id.input1);

        a100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextAmount.setText("100");
            }
        });

        a200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextAmount.setText("200");
            }
        });

        a500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextAmount.setText("500");
            }
        });

        editTextAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!editTextAmount.getText().toString().equals("")) {
                    if (Integer.parseInt(editTextAmount.getText().toString()) < 100) {
                        textInputLayout.setError("Amount should be greater than 100");
                    } else {
                        textInputLayout.setErrorEnabled(false);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextAmount.getText().toString().equals("")) {
                    if (Integer.parseInt(editTextAmount.getText().toString()) < 100) {
                        textInputLayout.setError("Amount should be greater than 100");
                    } else {
                        Bundle b = new Bundle();
                        b.putInt("amount", Integer.parseInt(editTextAmount.getText().toString()));
                        Navigation.findNavController(view).navigate(R.id.action_addMoneyFragment_to_selectPaymentMethod, b);
                    }
                } else {
                    textInputLayout.setError("Enter a valid amount");
                }
            }
        });

        return view;
    }
}