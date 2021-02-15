package com.example.bidbadnew.Fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.bidbadnew.Activities.MainActivity;
import com.example.bidbadnew.Model.SignupResponse;
import com.example.bidbadnew.Model.User;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;
import com.example.bidbadnew.repositories.RetrofitClient;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalDetails extends Fragment {

    String name, password, email, phone, dob, gender;
    RadioGroup radioSexGroup;
    RadioButton radioSexButton;
    DatePicker datePicker;
    MaterialButton signup;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_details, container, false);
        radioSexGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        datePicker = view.findViewById(R.id.datepicker);

        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dob = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                Log.d("DOB", dob);
            }
        });

        radioSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioSexButton = (RadioButton) view.findViewById(checkedId);
                gender = radioSexButton.getText().toString();
                Log.d("GENDER", gender);
            }
        });

        signup = view.findViewById(R.id.next_button);
        name = PersonalDetailsArgs.fromBundle(getArguments()).getName();
        email = PersonalDetailsArgs.fromBundle(getArguments()).getEmail();
        phone = PersonalDetailsArgs.fromBundle(getArguments()).getMobile();
        password = PersonalDetailsArgs.fromBundle(getArguments()).getPassword();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<SignupResponse> signupResponseCall = RetrofitClient.getInstance().getMyApi().signup(phone, name, "signup", email, password, dob, gender);
                signupResponseCall.enqueue(new Callback<SignupResponse>() {
                    @Override
                    public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                        System.out.println("Server response" + response.body().getError());
                        if (!response.body().getError()) {
                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                            User u = response.body().getUser();
                            SharedPrefManager.getInstance(getContext()).userLogin(new User(u.getId(), u.getEmail(), u.getFirstname(), u.getMobile(), u.getGender(), u.getDob(), u.getJoinedon()));
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<SignupResponse> call, Throwable t) {

                    }
                });
            }
        });

        return view;
    }
}