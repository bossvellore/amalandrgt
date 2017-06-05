package com.dsa.model;

import com.dsa.chat.MailBox;

import java.util.Map;

/**
 * Created by amalroshand on 02/06/17.
 */

public class AppMessage {
    private String key;
    private String msgText;
    private String contactUid;
    private MailBox mailBox;
    private Object timestamp;
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

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }
}
