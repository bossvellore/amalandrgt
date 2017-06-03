package com.dsa.localdl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
        values.put(AppDatabaseContract.MessageTable.COL_TIME_STAMP, message.getTimestamp().toString());
        // Insert the new row, returning the primary key value of the new row
        db.insert(AppDatabaseContract.MessageTable.TABLE_NAME, null, values);
    }

    public List<AppMessage> getMessagesFrom(String contactUid)
    {
        List<AppMessage> messageList=new ArrayList<AppMessage>();
        return messageList;
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
