package com.example.bidbadnew.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Login;
import com.example.bidbadnew.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

import java.util.concurrent.TimeUnit;

public class ForgotPasswordEnterOTP extends Fragment {

    private FirebaseAuth mAuth;
    String verificationCode;
    String phone;
    View view;
    FloatingActionButton fab;
    ProgressDialog progressDialog;
    TextView seconds, phoneNo;

    @Override                // do Stuff
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_forgot_password_enter_o_t_p, container, false);
        final EditText otp = view.findViewById(R.id.otp);
        fab = view.findViewById(R.id.submit);
        phoneNo = view.findViewById(R.id.mobileno);
        progressDialog = new ProgressDialog(view.getContext());
        seconds = view.findViewById(R.id.seconds);
        progressDialog.setTitle("Verifying OTP");
        progressDialog.setMessage("Wait until we verify OTP");

        phoneNo.setText(phone);

        CountDownTimer countDownTimer = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                seconds.setText(millisUntilFinished/1000 + " seconds");
            }

            @Override
            public void onFinish() {

            }
        }.start();

//        MaterialButton submit = view.findViewById(R.id.submit);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp1 = otp.getText().toString();
                if(verificationCode != null) {
                    progressDialog.show();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp1);
                    SigninWithPhone(credential);
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();

        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                //SigninWithPhone(credential);
                Log.d("TAG", "onVerificationCompleted:" + credential);
                String code = credential.getSmsCode();
                if (code != null) {
                    otp.setText(code);
                }

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("TAG", "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("TAG", "onCodeSent:" + verificationId);
                verificationCode = verificationId;
                // Save verification ID and resending token so we can use them later
                Log.d("Verfication code", verificationId);

                // ...
            }
        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(getActivity())                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

        return view;
    }

    private void SigninWithPhone(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Bundle b = new Bundle();
                            b.putString("mobile", phone);
                            Navigation.findNavController((Login) getActivity(), R.id.nav_login_fragment).navigate(R.id.action_forgotPasswordEnterOTP_to_changePassword, b);
                        } else {
                            Toast.makeText(getActivity(),"Incorrect OTP",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            phone = "+91"+getArguments().getString("mobile");
            Log.d("mobile", getArguments().getString("mobile"));
        }
    }

}