package com.dsa.gt;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.dsa.contacts.AppContacts;
import com.dsa.firebasedl.ContactsRDB;
import com.dsa.localdl.ContactsLDB;
import com.dsa.model.AppContact;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContactsRequestService extends Service {

    ChildEventListener contactRequestChildEventListener;
    AppContacts appContacts;
    public ContactsRequestService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        appContacts=new AppContacts(getApplicationContext());
        contactRequestChildEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                AppContact otherContact=dataSnapshot.getValue(AppContact.class);
                boolean notify=false;
                if(!appContacts.exists(otherContact.getUid()))
                    notify=true;
                appContacts.receiveContactRequest(otherContact);
                if(notify)
                    notifyApp(otherContact.getDisplayName());
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
        appContacts.setNewContactRequestListener(contactRequestChildEventListener);
    }

    private void notifyApp(String notifString) {

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int icon = R.drawable.common_full_open_on_phone;
        Context context = getApplicationContext();
        //Notification notification = new Notification(icon, "Firebase" + Math.random(), System.currentTimeMillis());

        //		notification.flags |= Notification.FLAG_AUTO_CANCEL;

        CharSequence contentTitle = "Contact Request" + Math.random();
        Intent notificationIntent = new Intent(context, ContactsActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        Notification notification= new Notification.Builder(context)
                .setSmallIcon(icon)
                .setTicker("New Contact Request")
                .setContentTitle(contentTitle)
                .setContentText(notifString)
                .setContentIntent(contentIntent)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)

                .build();
        mNotificationManager.notify(1, notification);
    }
}
