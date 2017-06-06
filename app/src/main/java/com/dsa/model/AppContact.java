package com.dsa.model;

import com.dsa.contacts.AppContactStatus;

/**
 * Created by amalroshand on 01/06/17.
 */

public class AppContact {
    public static final String REFERENCE="contacts";
    public static final String CHILD_DISPLAY_NAME="displayName";
    public static final String CHILD_TIME_STAMP="timeStamp";

    private String uid;
    private String displayName;
    private String photoUrl;
    private AppContactStatus myStatus;
    private AppContactStatus otherStatus;
    private Object timeStamp;

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public AppContactStatus getMyStatus() {
        return myStatus;
    }

    public void setMyStatus(AppContactStatus myStatus) {
        this.myStatus = myStatus;
    }

    public AppContactStatus getOtherStatus() {
        return otherStatus;
    }

    public void setOtherStatus(AppContactStatus otherStatus) {
        this.otherStatus = otherStatus;
    }
}
