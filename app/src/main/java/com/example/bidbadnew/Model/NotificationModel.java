package com.example.bidbadnew.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationModel {

    @SerializedName("notifid")
    @Expose
    private String notifid;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("notificationtitle")
    @Expose
    private String notificationtitle;
    @SerializedName("notificationbody")
    @Expose
    private String notificationbody;
    @SerializedName("time")
    @Expose
    private String time;

    public String getNotifid() {
        return notifid;
    }

    public void setNotifid(String notifid) {
        this.notifid = notifid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNotificationtitle() {
        return notificationtitle;
    }

    public void setNotificationtitle(String notificationtitle) {
        this.notificationtitle = notificationtitle;
    }

    public String getNotificationbody() {
        return notificationbody;
    }

    public void setNotificationbody(String notificationbody) {
        this.notificationbody = notificationbody;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
