package com.codepath.apps.mysimpletweet;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = MyTweetDatabase.NAME, version = MyTweetDatabase.VERSION)
public class MyTweetDatabase {

    public static final String NAME = "TweetDatabase";

    public static final int VERSION = 1;
}
