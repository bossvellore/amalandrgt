package com.dsa.gt;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.dsa.chat.MailBox;
import com.dsa.contacts.AppContacts;
import com.dsa.firebasedl.ContactsRDB;
import com.dsa.firebasedl.MessageRDB;
import com.dsa.model.AppMessage;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class MessageService extends Service {

    DatabaseReference reference= MessageRDB.getInstance().getMyInboxReference();
    ChildEventListener messageChildEventListener;
    AppMessage message;
    LocalBroadcastManager localBroadcastManager;
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
        messageChildEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                message = dataSnapshot.getValue(AppMessage.class);
                notifyApp(message);
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

    private void notifyApp(AppMessage message)
    {
        Intent intent = new Intent();
        intent.setAction(IntentAction.INCOMING_MESSAGE);
        intent.putExtra(IntentExtra.MSG_FROM_UID,message.getContactUid());
        intent.putExtra(IntentExtra.MSG_MAIL_BOX, MailBox.IN.toString());
        intent.putExtra(IntentExtra.MSG_TEXT, message.getMsgText());
        intent.putExtra(IntentExtra.MSG_TIME_STAMP, message.getTimestamp());
        localBroadcastManager.sendBroadcast(intent);
    }
}
