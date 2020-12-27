package com.example.bidbadnew.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Login;
import com.example.bidbadnew.Others.SharedPrefManager;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!SharedPrefManager.getInstance(Splash.this).isLoggedIn()){
            startActivity(new Intent(Splash.this, Login.class));
            finish();
        }

        else{
            startActivity(new Intent(Splash.this, MainActivity.class));
            finish();
        }
    }
}
