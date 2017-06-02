package com.dsa.localdl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dsa.model.AppContact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amalroshand on 01/06/17.
 */

public class ContactsLDB extends DatabaseHelper {

    public ContactsLDB(Context context)
    {
        super(context);
    }

    public void insert(AppContact contact)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(AppDatabaseContract.ContactsTable.COL_UID, contact.getUid());
        values.put(AppDatabaseContract.ContactsTable.COL_DISPLAY_NAME, contact.getDisplayName());
        values.put(AppDatabaseContract.ContactsTable.COL_PHOTO_URL, contact.getPhotoUrl());
        values.put(AppDatabaseContract.ContactsTable.COL_MY_STATUS, contact.getMyStatus().toString());
        values.put(AppDatabaseContract.ContactsTable.COL_OTHER_STATUS, contact.getOtherStatus().toString());
        // Insert the new row, returning the primary key value of the new row
        db.insert(AppDatabaseContract.ContactsTable.TABLE_NAME, null, values);
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
