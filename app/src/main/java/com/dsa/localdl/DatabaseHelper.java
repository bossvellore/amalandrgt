package com.dsa.localdl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by amalroshand on 01/06/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "gt.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AppDatabaseContract.ContactsTable.TABLE_NAME + " (" +
                    AppDatabaseContract.ContactsTable.COL_UID + " TEXT PRIMARY KEY," +
                    AppDatabaseContract.ContactsTable.COL_DISPLAY_NAME + " TEXT," +
                    AppDatabaseContract.ContactsTable.COL_PHOTO_URL + " TEXT," +
                    AppDatabaseContract.ContactsTable.COL_MY_STATUS + " TEXT," +
                    AppDatabaseContract.ContactsTable.COL_OTHER_STATUS +" TEXT,"+
                    AppDatabaseContract.ContactsTable.COL_TIME_STAMP +" TEXT);";

    private static final String SQL_CREATE_MESSAGES =
            "CREATE TABLE " + AppDatabaseContract.MessageTable.TABLE_NAME + " (" +
                    AppDatabaseContract.MessageTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    AppDatabaseContract.MessageTable.COL_SENDER_MESSAGE_ID + " INTEGER," +
                    AppDatabaseContract.MessageTable.COL_KEY + " TEXT NULL," +
                    AppDatabaseContract.MessageTable.COL_CONTACT_UID + " TEXT," +
                    AppDatabaseContract.MessageTable.COL_CONTACT_DISPLAY_NAME + " TEXT," +
                    AppDatabaseContract.MessageTable.COL_MAIL_BOX + " TEXT," +
                    AppDatabaseContract.MessageTable.COL_MSG_TEXT + " TEXT," +
                    AppDatabaseContract.MessageTable.COL_MSG_TYPE + " TEXT," +
                    AppDatabaseContract.MessageTable.COL_TIME_STAMP + " TEXT);";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_MESSAGES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
}
