package com.example.bidbadnew.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bidbadnew.R;
import com.google.android.material.button.MaterialButton;

public class ForgotPassword extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        TextView mobile = view.findViewById(R.id.loginMobile);
        MaterialButton button = view.findViewById(R.id.next_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                if(mobile.getText().toString().length() == 10) {
                    b.putString("mobile", mobile.getText().toString());
                    Navigation.findNavController(view).navigate(R.id.action_forgotPassword_to_forgotPasswordEnterOTP, b);
                } else {
                    mobile.setError("Enter a valid mobile number");
                }
            }
        });

        return view;
    }
}