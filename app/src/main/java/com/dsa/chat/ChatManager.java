package com.dsa.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.dsa.firebasedl.MessageRDB;
import com.dsa.gt.DateHelper;
import com.dsa.gt.IntentAction;
import com.dsa.gt.IntentExtra;
import com.dsa.localdl.MessageLDB;
import com.dsa.model.AppMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ServerValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by amalroshand on 02/06/17.
 */

public class ChatManager extends BroadcastReceiver {
    Context context;
    MessageLDB messageLDB;
    List<AppMessage> messageList;
    String contactUid;
    ChatListener chatListener;
    LocalBroadcastManager localBroadcastManager;


    public ChatManager(Context context, String ContactUid)
    {
        this.context=context;
        this.contactUid = ContactUid;
        messageLDB=new MessageLDB(context);
        messageList=messageLDB.getMessagesFrom(contactUid);
        localBroadcastManager=LocalBroadcastManager.getInstance(context);
        IntentFilter messageIntentFilter = new IntentFilter(IntentAction.INCOMING_MESSAGE);
        localBroadcastManager.registerReceiver(this, messageIntentFilter);
    }

    private void fireOnChatEvent()
    {
        if(chatListener!=null)
            chatListener.onChat();
    }

    public long saveToLocalDB(AppMessage message)
    {
        long insertId=0;
        message.setContactUid(contactUid);
        message.setMailBox(MailBox.OUT);
        message.setTimeStamp(DateHelper.getCurrentUnixTimeStamp());
        switch (message.getMessageType())
        {
            case CONTACT_REQUEST:
                insertId = messageLDB.insert(message);
                this.messageList.add(message);
                fireOnChatEvent();
                break;
            case TEXT_CHAT:
                insertId = messageLDB.insert(message);
                this.messageList.add(message);
                fireOnChatEvent();
                break;
        }
        return insertId;
    }


    public void send(AppMessage message)
    {
        long _ID=saveToLocalDB(message);

        AppMessage _message=new AppMessage(message);
        _message.set_ID(_ID);
        // set the sender contact
        _message.setContactUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        _message.setTimeStamp(ServerValue.TIMESTAMP);

        MessageRDB messageRDB=MessageRDB.getInstance();
        switch (_message.getMessageType())
        {
            case CONTACT_REQUEST:
                messageRDB.send(_message, contactUid);
                break;
            case TEXT_CHAT:
                messageRDB.send(_message, contactUid);
                break;
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String contact = intent.getStringExtra(IntentExtra.MSG_FROM_UID);
        if(this.contactUid.equals(intent.getStringExtra(IntentExtra.MSG_FROM_UID) ) ){

            switch (MessageType.valueOf(intent.getStringExtra(IntentExtra.MSG_TYPE)))
            {
                case CONTACT_REQUEST:

                    break;
                case TEXT_CHAT:
                    AppMessage message = new AppMessage();
                    message.setMsgText(intent.getStringExtra(IntentExtra.MSG_TEXT));
                    message.setMailBox(MailBox.valueOf(intent.getStringExtra(IntentExtra.MSG_MAIL_BOX)));
                    message.setContactUid(intent.getStringExtra(IntentExtra.MSG_FROM_UID));
                    message.setTimeStamp(intent.getStringExtra(IntentExtra.MSG_TIME_STAMP));
                    messageList.add(message);
                    fireOnChatEvent();
                    break;
            }

        }
    }

    public void setChatListener(ChatListener chatListener) {
        this.chatListener = chatListener;
    }

    public List<AppMessage> getMessageList() {
        return messageList;
    }
}
