package com.example.bidbadnew.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.Login;
import com.example.bidbadnew.R;
import com.example.bidbadnew.repositories.RetrofitClient;

import java.util.concurrent.RecursiveTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupFragment extends Fragment {

    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_signup, container, false);
        final EditText phone = view.findViewById(R.id.phoneno);

        view.findViewById(R.id.sendotp).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                if (phone.getText().length() != 10) {
                    phone.setError("Enter a valid phone no");
                } else {
                    Call<Integer> integerCall = RetrofitClient.getInstance().getMyApi().isUserRegistered("+91"+phone.getText().toString());
                    integerCall.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if(response.body() == 0){
                                Navigation.findNavController(view).navigate(SignupFragmentDirections.actionNavLoginToNavOtp("+91"+phone.getText().toString()));
                            } else {
                                Toast.makeText(view.getContext(), "User already registered", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {

                        }
                    });
                }
            }
        });

        return view;
    }
}