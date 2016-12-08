package com.codepath.apps.mysimpletweet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Chjeng-Lun SHIEH on 2016/12/5.
 */
public class Utlity {
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M月d日",Locale.getDefault());
    static Date now = new Date();

    //TODO should use i18n
    public static String toFriendlyTimestamp(Date created_at) {
        long different = new Date().getTime() - created_at.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        if(elapsedDays >= 1){
            //Return Date;
            return simpleDateFormat.format(created_at);
        }else if(elapsedHours >= 1){
            //Return n小時
            return elapsedHours+"小時";
        }else if(elapsedMinutes >= 1){
            //Return n分
            return elapsedMinutes+"分";
        }else if(elapsedSeconds >= 1){
            //Return n秒
            return elapsedSeconds+"秒";
        }
        return "???";
    }
}
