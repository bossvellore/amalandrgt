package com.dsa.localdl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dsa.chat.MailBox;
import com.dsa.model.AppContact;
import com.dsa.model.AppMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amalroshand on 02/06/17.
 */

public class MessageLDB extends DatabaseHelper{
    public MessageLDB(Context context)
    {
        super(context);
    }

    public void insert(AppMessage message)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(AppDatabaseContract.MessageTable.COL_KEY, message.getKey());
        values.put(AppDatabaseContract.MessageTable.COL_CONTACT_UID, message.getContactUid());
        values.put(AppDatabaseContract.MessageTable.COL_MAIL_BOX, message.getMailBox().toString());
        values.put(AppDatabaseContract.MessageTable.COL_MSG_TEXT, message.getMsgText().toString());
        values.put(AppDatabaseContract.MessageTable.COL_TIME_STAMP, message.getTimeStamp().toString());
        // Insert the new row, returning the primary key value of the new row
        db.insert(AppDatabaseContract.MessageTable.TABLE_NAME, null, values);
    }

    public void update(AppMessage message)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        //values.put(AppDatabaseContract.MessageTable.COL_KEY, message.getKey());
        values.put(AppDatabaseContract.MessageTable.COL_CONTACT_UID, message.getContactUid());
        values.put(AppDatabaseContract.MessageTable.COL_MAIL_BOX, message.getMailBox().toString());
        values.put(AppDatabaseContract.MessageTable.COL_MSG_TEXT, message.getMsgText().toString());
        values.put(AppDatabaseContract.MessageTable.COL_TIME_STAMP, message.getTimeStamp().toString());
        // Insert the new row, returning the primary key value of the new row
        db.update(AppDatabaseContract.MessageTable.TABLE_NAME, values,
                AppDatabaseContract.MessageTable.COL_KEY+"=?", new String[]{message.getKey()});
    }

    public boolean exists(String key)
    {
        String[] columns=new String[]{
                AppDatabaseContract.MessageTable.COL_KEY
        };
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(AppDatabaseContract.MessageTable.TABLE_NAME,columns,
                AppDatabaseContract.MessageTable.COL_KEY + "=?", new String[]{key}, null, null, null );
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }

    public AppMessage getLastMessage()
    {
        String[] columns=new String[]{
                AppDatabaseContract.MessageTable.COL_KEY,
                AppDatabaseContract.MessageTable.COL_MSG_TEXT,
                AppDatabaseContract.MessageTable.COL_MAIL_BOX,
                AppDatabaseContract.MessageTable.COL_CONTACT_UID,
                AppDatabaseContract.MessageTable.COL_TIME_STAMP
        };
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(AppDatabaseContract.MessageTable.TABLE_NAME,columns,null,null,null,null, AppDatabaseContract.MessageTable.COL_TIME_STAMP + " DESC","1");
        if(cursor.moveToFirst()) {
            return readCursor(cursor);
        }else{
            return null;
        }

    }
    public List<AppMessage> getMessagesFrom(String contactUid)
    {
        List<AppMessage> messageList=new ArrayList<AppMessage>();

        String selectQuery = "SELECT * FROM "+AppDatabaseContract.MessageTable.TABLE_NAME+
                " WHERE "+ AppDatabaseContract.MessageTable.COL_CONTACT_UID+" ='"+contactUid+"'" +
                "ORDER BY "+ AppDatabaseContract.MessageTable.COL_TIME_STAMP+" ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                messageList.add(readCursor(cursor));
            } while (cursor.moveToNext());
        }

        return messageList;
    }

    private AppMessage readCursor(Cursor cursor)
    {
        AppMessage message = new AppMessage();
        message.setKey(cursor.getString(cursor.getColumnIndex(AppDatabaseContract.MessageTable.COL_KEY)));
        message.setContactUid(cursor.getString(cursor.getColumnIndex(AppDatabaseContract.MessageTable.COL_CONTACT_UID)));
        message.setMailBox(MailBox.valueOf(cursor.getString(cursor.getColumnIndex(AppDatabaseContract.MessageTable.COL_MAIL_BOX))));
        message.setMsgText(cursor.getString(cursor.getColumnIndex(AppDatabaseContract.MessageTable.COL_MSG_TEXT)));
        message.setTimeStamp(cursor.getString(cursor.getColumnIndex(AppDatabaseContract.MessageTable.COL_TIME_STAMP)));
        return message;
    }

    public List<AppContact> all() {
        List<AppContact> contactList = new ArrayList<AppContact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + AppDatabaseContract.ContactsTable.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AppContact contact = new AppContact();
                contact.setDisplayName(cursor.getString(cursor.getColumnIndex(AppDatabaseContract.ContactsTable.COL_DISPLAY_NAME)));
                contact.setPhotoUrl(cursor.getString(cursor.getColumnIndex(AppDatabaseContract.ContactsTable.COL_PHOTO_URL)));
                contact.setUid(cursor.getString(cursor.getColumnIndex(AppDatabaseContract.ContactsTable.COL_UID)));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }
}
