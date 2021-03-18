package com.example.bidbadnew.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.bidbadnew.R;
import com.example.bidbadnew.repositories.RetrofitClient;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends Fragment {

    MaterialButton materialButton;
    TextView repeatPassword, password;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().equals(repeatPassword.getText().toString()) && password.getText().toString().length() > 8){
                    Call call = RetrofitClient.getInstance().getMyApi().forgotpassword(phone, password.getText().toString());
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            Navigation.findNavController(view).navigate(R.id.action_changePassword_to_loginFragment);
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(view.getContext(), "Passoword not changed", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(view.getContext(), "Passoword don't match", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        password = view.findViewById(R.id.newPassword);
        repeatPassword = view.findViewById(R.id.repeatPassword);
        materialButton = view.findViewById(R.id.updatePassword);

        return view;
    }

    String phone;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            phone = getArguments().getString("mobile");
        }
    }
}
