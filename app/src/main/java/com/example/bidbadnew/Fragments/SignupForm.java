package com.example.bidbadnew.Fragments;

import androidx.core.text.TextUtilsCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bidbadnew.Activities.MainActivity;
import com.example.bidbadnew.Model.SignupResponse;
import com.example.bidbadnew.Model.User;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;
import com.example.bidbadnew.repositories.RetrofitClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupForm extends Fragment {

    private SignupFormViewModel mViewModel;
    TextInputEditText email, password, repeatpassword, name;
    TextInputLayout emailt, passwordt, repeatpasswordt, namet;
    MaterialButton next;
    String phone;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_form_fragment, container, false);
        phone = SignupFormArgs.fromBundle(getArguments()).getMobile();
        email = view.findViewById(R.id.loginEmail);
        name = view.findViewById(R.id.loginName);
        password = view.findViewById(R.id.password_edit_text);
        repeatpassword = view.findViewById(R.id.password_edit_text2);

        emailt = view.findViewById(R.id.textinput2);
        namet = view.findViewById(R.id.textinput3);
        passwordt = view.findViewById(R.id.password_text_input);
        repeatpasswordt = view.findViewById(R.id.password_text_input2);

        next = view.findViewById(R.id.next_button);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SignupFormDirections.
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(email.getText().toString()) || !Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    email.setError("Enter a valid email");
                } else if(TextUtils.isEmpty(name.getText().toString())){
                    name.setError("Enter your name");
                }
                else if(password.getText().length() < 8){
                    password.setError("Password must not be less than 8 characters");
                } else if(!password.getText().toString().equals(repeatpassword.getText().toString())){
                    passwordt.setError("Passwords don't match");
                } else {
                    Navigation.findNavController(view).navigate(SignupFormDirections.actionSignupFormToPersonalDetails(phone, email.getText().toString(), password.getText().toString(), name.getText().toString()));

//                    Call<SignupResponse> signupResponseCall = RetrofitClient.getInstance().getMyApi().signup(phone, name.getText().toString(), "signup", email.getText().toString(), password.getText().toString());
//                    signupResponseCall.enqueue(new Callback<SignupResponse>() {
//                        @Override
//                        public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
//                            if (!response.body().getError()) {
//                                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
//                                User u = response.body().getUser();
//                                SharedPrefManager.getInstance(getContext()).userLogin(new User(u.getId(), u.getEmail(), u.getFirstname(), u.getMobile()));
//                                startActivity(new Intent(getActivity(), MainActivity.class));
//                                getActivity().finish();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<SignupResponse> call, Throwable t) {
//
//                        }
//                    });
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SignupFormViewModel.class);
        // TODO: Use the ViewModel
    }

}