package com.dsa.model;

import com.dsa.chat.MailBox;

import java.sql.Timestamp;

/**
 * Created by amalroshand on 02/06/17.
 */

public class AppMessage {
    private String key;
    private String text;
    private String contactUid;
    private MailBox mailBox;
    private String timestamp;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
