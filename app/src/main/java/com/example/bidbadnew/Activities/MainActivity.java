package com.example.bidbadnew.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bidbadnew.ActionBottomDialogFragment;
import com.example.bidbadnew.Fragments.NoConnectionDialogFragment;
import com.example.bidbadnew.Others.SharedPrefManager;
import com.example.bidbadnew.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity implements ActionBottomDialogFragment.ItemClickListener {
    public NavController navController;
    public ImageView personDot, resultsDot;
    Activity activity;
    public static int height, width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        String token = task.getResult().getToken();
                        Log.d("Token", token);
                        sendToken(token);
                    }
                });

        activity = this;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); //For night mode theme
        setContentView(R.layout.activity_main);
        navController = Navigation.findNavController(activity, R.id.nav_host_fragment);
        resultsDot = findViewById(R.id.resultsDot);
        personDot = findViewById(R.id.personDot);

        BottomAppBar navView = findViewById(R.id.bar);
        FloatingActionButton fab = findViewById(R.id.fabhome);
        fab.setVisibility(View.VISIBLE);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_black_24dp));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_global_navigation_home);
                resultsDot.setVisibility(View.INVISIBLE);
                personDot.setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.linear1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_global_navigation_dashboard);
                resultsDot.setVisibility(View.VISIBLE);
                personDot.setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.linear2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_global_navigation_notifications);
                resultsDot.setVisibility(View.INVISIBLE);
                personDot.setVisibility(View.VISIBLE);
            }
        });
    }

    void sendToken(String token) {
        int id = SharedPrefManager.getInstance(MainActivity.this).getUser().getId();
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, "http://easyvela.esy.es/AndroidAPI/updateToken.php?id=" + id + "&token=" + token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        navController.navigate(R.id.action_global_noConnectionDialogFragment);
                    }
                });
        RequestQueue requestQueue2 = Volley.newRequestQueue(MainActivity.this);
        requestQueue2.add(stringRequest2);
    }

    @Override
    public void onItemClick(String item) {

    }

    public void showNoConnection(){
        NoConnectionDialogFragment addPhotoBottomDialogFragment =
                NoConnectionDialogFragment.newInstance();
        addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                NoConnectionDialogFragment.TAG);
        addPhotoBottomDialogFragment.setCancelable(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}