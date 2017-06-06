package com.dsa.model;

import com.dsa.chat.MailBox;

import java.util.Map;

/**
 * Created by amalroshand on 02/06/17.
 */

public class AppMessage {
    public static final String REFERENCE="messages";
    public static final String CHILD_TIME_STAMP="timeStamp";
    private String key;
    private String msgText;
    private String contactUid;
    private MailBox mailBox;
    private Object timeStamp;
    //private Map<String,String> timestamp;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String text) {
        this.msgText = text;
    }

    public String getContactUid() {
        return contactUid;
    }

    public void setContactUid(String contactUid) {
        this.contactUid = contactUid;
    }

    public MailBox getMailBox() {
        return mailBox;
    }

    public void setMailBox(MailBox mailBox) {
        this.mailBox = mailBox;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }
}
