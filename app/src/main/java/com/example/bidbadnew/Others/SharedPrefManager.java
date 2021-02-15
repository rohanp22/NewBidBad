package com.example.bidbadnew.Others;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.Login;
import com.example.bidbadnew.Activities.MainActivity;
import com.example.bidbadnew.Model.User;

//here for this class we are using a singleton pattern

public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "bidbadsharedpref";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_FIRSTNAME = "keyfirstname";
    private static final String KEY_ID = "keyid";
    private static final String KEY_MOBILE = "keymobile";
    private static final String KEY_GENDER = "keygender";
    private static final String KEY_DOB = "keydob";
    private static final String KEY_JOINED = "keyjoined";
    String myList = "myList";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ID, user.getId()+"");
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_FIRSTNAME, user.getFirstname());
        editor.putString(KEY_MOBILE, user.getMobile());
        editor.putString(KEY_GENDER, user.getGender());
        editor.putString(KEY_DOB, user.getDob());
        editor.putString(KEY_JOINED, user.getJoinedon());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ID, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                Integer.parseInt(sharedPreferences.getString(KEY_ID, null)),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_FIRSTNAME, null),
                sharedPreferences.getString(KEY_MOBILE, null),
                sharedPreferences.getString(KEY_GENDER, null),
                sharedPreferences.getString(KEY_DOB, null),
                sharedPreferences.getString(KEY_JOINED, "2019/01/01")
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent i = new Intent(mCtx, Login.class);
        // set the new task and clear flags
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mCtx.startActivity(i);
    }
}