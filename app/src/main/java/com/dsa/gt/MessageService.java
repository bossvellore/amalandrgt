package com.dsa.gt;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
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
        AppMessage lastMessage=messageLDB.getLastMessage();
        messageChildEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                message = dataSnapshot.getValue(AppMessage.class);
                message.setKey(dataSnapshot.getKey());
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
        if(lastMessage != null) {
            if(lastMessage.getTimeStamp() != null)
            reference.orderByChild("timestamp")
                    .startAt(Long.valueOf(lastMessage.getTimeStamp().toString()) + 1)
                    .addChildEventListener(messageChildEventListener);
            else
                reference.addChildEventListener(messageChildEventListener);
        }else{
            reference.addChildEventListener(messageChildEventListener);
        }
    }

    private void saveToLocalDB(AppMessage message)
    {
        message.setMailBox(MailBox.IN);
        if(!messageLDB.exists(message.getKey())) {
            messageLDB.insert(message);
            MessageRDB.getInstance().delete(message.getKey());
            notifyApp(message);
        }else{
            MessageRDB.getInstance().delete(message.getKey());
        }
    }

    private void notifyApp(AppMessage message)
    {
        notifyApp(message.getMsgText());
        Intent intent = new Intent();
        intent.setAction(IntentAction.INCOMING_MESSAGE);
        intent.putExtra(IntentExtra.MSG_FROM_UID,message.getContactUid());
        intent.putExtra(IntentExtra.MSG_MAIL_BOX, MailBox.IN.toString());
        intent.putExtra(IntentExtra.MSG_TEXT, message.getMsgText());
        intent.putExtra(IntentExtra.MSG_TIME_STAMP, message.getTimeStamp().toString());
        localBroadcastManager.sendBroadcast(intent);
    }

    private void notifyApp(String notifString) {

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int icon = R.drawable.common_full_open_on_phone;
        Context context = getApplicationContext();
        //Notification notification = new Notification(icon, "Firebase" + Math.random(), System.currentTimeMillis());

        //		notification.flags |= Notification.FLAG_AUTO_CANCEL;

        CharSequence contentTitle = "New Messages";
        Intent notificationIntent = new Intent(context, ContactsActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        Notification notification= new Notification.Builder(context)
                .setSmallIcon(icon)
                .setTicker("New Message")
                .setContentTitle(contentTitle)
                .setContentText(notifString)
                .setContentIntent(contentIntent)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)

                .build();
        mNotificationManager.notify(1, notification);
    }
}
