package com.example.bidbadnew.Fragments;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bidbadnew.Activities.MainActivity;
import com.example.bidbadnew.Model.LoginResponse;
import com.example.bidbadnew.Model.User;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;
import com.example.bidbadnew.repositories.RetrofitClient;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;
    EditText mobile, password;
    TextView signup;
    MaterialButton login;
    ImageView check;
    TextView forgot;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        mobile = view.findViewById(R.id.loginMobile);
        password = view.findViewById(R.id.password_edit_text);
        signup = view.findViewById(R.id.signuptext);
        login = view.findViewById(R.id.next_button);
        check = view.findViewById(R.id.check);
        forgot = view.findViewById(R.id.forgotPasswordText);

        mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 10){
                    check.setVisibility(View.VISIBLE);
                } else {
                    check.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Login");
                if (mobile.getText().toString().length() != 10) {
                    mobile.setError("Enter a valid phone number");
                } else {
                    Call<LoginResponse> loginCall = RetrofitClient.getInstance().getMyApi().login("+91" + mobile.getText().toString(), password.getText().toString(), "login");
                    loginCall.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.body() != null) {
                                if (!response.body().getError()) {
                                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                                    if (response.body().getMessage().equals("Login successfully")) {
                                        User u = response.body().getUser();
                                        SharedPrefManager.getInstance(getContext()).userLogin(new User(u.getId(), u.getEmail(), u.getFirstname(), u.getMobile(), u.getGender(), u.getDob(), u.getJoinedon()));
                                        startActivity(new Intent(getActivity(), MainActivity.class));
                                        getActivity().finish();
                                    } else {
                                        forgot.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            t.getMessage();
                        }
                    });
                }
            }
        });

        TextView forgotPassword = view.findViewById(R.id.forgotPasswordText);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_forgotPassword);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_nav_login);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        // TODO: Use the ViewModel


    }

}