package com.example.bidbadnew.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("joinedon")
    @Expose
    private String joinedon;

    public User(int id, String email, String firstname, String mobile, String dob, String gender, String joinedon) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.mobile = mobile;
        this.gender = gender;
        this.dob = dob;
        this.joinedon = joinedon;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getJoinedon() {
        return joinedon;
    }
}