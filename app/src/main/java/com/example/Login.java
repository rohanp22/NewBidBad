package com.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;

import android.content.Intent;
import android.os.Bundle;

import com.example.bidbadnew.Activities.MainActivity;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;

public class Login extends AppCompatActivity {

    public NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); //For night mode theme

        setContentView(R.layout.activity_login);
    }
}
