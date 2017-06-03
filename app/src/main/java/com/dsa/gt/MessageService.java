package com.dsa.gt;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

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
    public MessageService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

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

    }
}
