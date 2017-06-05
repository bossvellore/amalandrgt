package com.dsa.gt;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by amalroshand on 04/06/17.
 */

public class DateHelper {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    public static long getCurrentUnixTimeStamp()
    {
        return System.currentTimeMillis() / 1000L;
    }
}
