package com.dsa.gt;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.dsa.chat.ChatManager;
import com.dsa.chat.MailBox;
import com.dsa.contacts.AppContacts;
import com.dsa.firebasedl.ContactsRDB;
import com.dsa.firebasedl.MessageRDB;
import com.dsa.localdl.MessageLDB;
import com.dsa.model.AppMessage;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;

public class MessageService extends Service {

    DatabaseReference reference= MessageRDB.getInstance().getMyInboxReference();
    ChildEventListener messageChildEventListener;
    AppMessage message;
    LocalBroadcastManager localBroadcastManager;
    MessageLDB messageLDB;
    public MessageService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        localBroadcastManager=LocalBroadcastManager.getInstance(getApplicationContext());
        messageLDB=new MessageLDB(getApplicationContext());
        messageChildEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                message = dataSnapshot.getValue(AppMessage.class);
                saveToLocalDB(message);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        reference.addChildEventListener(messageChildEventListener);
    }

    private void saveToLocalDB(AppMessage message)
    {
        message.setMailBox(MailBox.IN);
        //long timeStamp = Long.valueOf(message.getTimestamp().toString());
        //String date = DateHelper.dateFormat.format(timeStamp);
        //message.setTimestamp(date);
        messageLDB.insert(message);
        notifyApp(message);
    }

    private void notifyApp(AppMessage message)
    {
        Intent intent = new Intent();
        intent.setAction(IntentAction.INCOMING_MESSAGE);
        intent.putExtra(IntentExtra.MSG_FROM_UID,message.getContactUid());
        intent.putExtra(IntentExtra.MSG_MAIL_BOX, MailBox.IN.toString());
        intent.putExtra(IntentExtra.MSG_TEXT, message.getMsgText());
        intent.putExtra(IntentExtra.MSG_TIME_STAMP, message.getTimestamp().toString());
        localBroadcastManager.sendBroadcast(intent);
    }
}
