package com.dsa.localdl;

import android.media.tv.TvContract;
import android.provider.BaseColumns;

/**
 * Created by amalroshand on 01/06/17.
 */

public final class AppDatabaseContract {
    private AppDatabaseContract(){}

    public static class ContactsTable implements BaseColumns{
        public static final String TABLE_NAME = "contacts";
        public static final String COL_UID = "uid";
        public static final String COL_DISPLAY_NAME = "displayName";
        public static final String COL_PHOTO_URL = "photoUrl";
        public static final String COL_MY_STATUS = "myStatus";
        public static final String COL_OTHER_STATUS = "otherStatus";
    }

    public static class MessageTable implements BaseColumns{
        public static final String TABLE_NAME = "messages";
        public static final String COL_KEY = "key";
        public static final String COL_TEXT = "text";
        public static final String COL_CONTACT_UID = "contactUid";
        public static final String COL_MAIL_BOX = "mailBox";
        public static final String COL_TIME_STAMP = "timeStamp";
    }
    private void test(){

    }
}
