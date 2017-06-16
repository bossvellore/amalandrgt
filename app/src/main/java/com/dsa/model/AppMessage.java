package com.dsa.model;

import com.dsa.chat.MailBox;
import com.dsa.chat.MessageType;

import java.util.Map;

/**
 * Created by amalroshand on 02/06/17.
 */

public class AppMessage {
    public static final String REFERENCE="messages";
    public static final String CHILD_TIME_STAMP="timeStamp";
    private long _ID;
    private long senderMsgId;
    private String key;
    private String msgText;
    private String contactUid;
    private String contactDisplayName;
    private MailBox mailBox;
    private MessageType messageType;
    private Object message;
    private Object timeStamp;

    public AppMessage(){}

    public AppMessage(AppMessage message){
        this._ID=message.get_ID();
        this.senderMsgId=message.getSenderMsgId();
        this.key=message.getKey();
        this.msgText=message.getMsgText();
        this.contactUid=message.getContactUid();
        this.contactDisplayName = message.getContactDisplayName();
        this.mailBox=message.getMailBox();
        this.messageType=message.getMessageType();
        this.message=message.getMessage();
        this.timeStamp=message.getTimeStamp();
    }

    public String getContactDisplayName() {
        return contactDisplayName;
    }

    public void setContactDisplayName(String contactDisplayName) {
        this.contactDisplayName = contactDisplayName;
    }

    public long get_ID() {
        return _ID;
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
    }

    public long getSenderMsgId() {
        return senderMsgId;
    }

    public void setSenderMsgId(long senderMsgId) {
        this.senderMsgId = senderMsgId;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

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
