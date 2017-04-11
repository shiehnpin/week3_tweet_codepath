package com.codepath.apps.mysimpletweet;

import android.text.format.DateUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Chjeng-Lun SHIEH on 2016/12/5.
 */
public class Utility {
    public static String getRelativeTimeAgo(Date rawJsonDate) {

        String relativeDate = "";

        long dateMillis = rawJsonDate.getTime();
        relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();

        return relativeDate;
    }
}
