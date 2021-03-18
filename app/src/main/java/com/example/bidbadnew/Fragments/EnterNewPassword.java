package com.example.bidbadnew.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bidbadnew.R;
import com.example.bidbadnew.repositories.RetrofitClient;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterNewPassword extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_enter_new_password, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView password = view.findViewById(R.id.newPassword);
        TextView repeatPassword = view.findViewById(R.id.repeatPassword);
        MaterialButton materialButton = view.findViewById(R.id.updatePassword);

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().equals(repeatPassword.getText().toString()) && password.getText().toString().length() > 8){
                    Call call = RetrofitClient.getInstance().getMyApi().forgotpassword(phone, password.getText().toString());
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            Navigation.findNavController(view).navigate(R.id.action_enterNewPassword_to_loginFragment);
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Toast.makeText(view.getContext(), "Passoword not changed", Toast.LENGTH_LONG);
                        }
                    });
                } else {
                    Toast.makeText(view.getContext(), "Passoword don't match", Toast.LENGTH_LONG);
                }
            }
        });
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