package com.codepath.apps.mysimpletweet.models;

import android.util.Log;

import com.codepath.apps.mysimpletweet.MyTweetDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//User can view the tweets from their home timeline (1 point)
//		User should be displayed the username, name, and body for each tweet
//		User should be displayed the relative timestamp for each tweet "8m", "7h"
//		User can view more tweets as they scroll with infinite pagination

@Table(database = MyTweetDatabase.class)
public class Tweet extends BaseModel {

	private static final String TAG = "TweetModel";
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);

	@PrimaryKey
	@Column
	String id;

	@Column
	private String text;

	@Column
	private Date created_at;

	@ForeignKey(saveForeignKeyModel =  false)
	private User user;

	public Tweet() {
		super();
	}

	// Parse model from JSON
	public Tweet(JSONObject object){
		super();

		try {
			this.id = object.getString("id_str");
			this.text = object.getString("text");
			this.created_at = simpleDateFormat.parse(object.getString("created_at"));
			this.user = new User(object.getJSONObject("user"));
		} catch (JSONException | ParseException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

		for (int i=0; i < jsonArray.length(); i++) {
			JSONObject tweetJson = null;
			try {
				tweetJson = jsonArray.getJSONObject(i);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

			Tweet tweet = new Tweet(tweetJson);
			tweet.save();
			tweets.add(tweet);

			Log.v(TAG,tweetJson.toString());
			Log.d(TAG,tweet.toString());
		}

		return tweets;
	}


	// Record Finders
	public static Tweet byId(long id) {
		return new Select().from(Tweet.class).where(SampleModel_Table.id.eq(id)).querySingle();
	}

	public static List<Tweet> recentItems() {
		return new Select().from(Tweet.class).orderBy(SampleModel_Table.id, false).limit(300).queryList();
	}

	public String getText() {
		return text;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public User getUser() {
		return user;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Tweet{" +
				"id='" + id + '\'' +
				", text='" + text + '\'' +
				", created_at=" + created_at +
				", user=" + user +
				'}';
	}

	public String getId() {
		return id;
	}
}


/*
[
  {
    "coordinates": null,
    "truncated": false,
    "createdAt": "Tue Aug 28 21:16:23 +0000 2012",
    "favorited": false,
    "id_str": "240558470661799936",
    "in_reply_to_user_id_str": null,
    "entities": {
      "urls": [

      ],
      "hashtags": [

      ],
      "user_mentions": [

      ]
    },
    "text": "just another test",
    "contributors": null,
    "id": 240558470661799936,
    "retweet_count": 0,
    "in_reply_to_status_id_str": null,
    "geo": null,
    "retweeted": false,
    "in_reply_to_user_id": null,
    "place": null,
    "source": "OAuth Dancer Reborn",
    "user": {
      "name": "OAuth Dancer",
      "profile_sidebar_fill_color": "DDEEF6",
      "profile_background_tile": true,
      "profile_sidebar_border_color": "C0DEED",
      "profile_image_url": "http://a0.twimg.com/profile_images/730275945/oauth-dancer_normal.jpg",
      "createdAt": "Wed Mar 03 19:37:35 +0000 2010",
      "location": "San Francisco, CA",
      "follow_request_sent": false,
      "id_str": "119476949",
      "is_translator": false,
      "profile_link_color": "0084B4",
      "entities": {
        "url": {
          "urls": [
            {
              "expanded_url": null,
              "url": "http://bit.ly/oauth-dancer",
              "indices": [
                0,
                26
              ],
              "display_url": null
            }
          ]
        },
        "description": null
      },
      "default_profile": false,
      "url": "http://bit.ly/oauth-dancer",
      "contributors_enabled": false,
      "favourites_count": 7,
      "utc_offset": null,
      "profile_image_url_https": "https://si0.twimg.com/profile_images/730275945/oauth-dancer_normal.jpg",
      "id": 119476949,
      "listed_count": 1,
      "profile_use_background_image": true,
      "profile_text_color": "333333",
      "followers_count": 28,
      "lang": "en",
      "protected": false,
      "geo_enabled": true,
      "notifications": false,
      "description": "",
      "profile_background_color": "C0DEED",
      "verified": false,
      "time_zone": null,
      "profile_background_image_url_https": "https://si0.twimg.com/profile_background_images/80151733/oauth-dance.png",
      "statuses_count": 166,
      "profile_background_image_url": "http://a0.twimg.com/profile_background_images/80151733/oauth-dance.png",
      "default_profile_image": false,
      "friends_count": 14,
      "following": false,
      "show_all_inline_media": false,
      "screen_name": "oauth_dancer"
    },
    "in_reply_to_screen_name": null,
    "in_reply_to_status_id": null
  },
  {
    "coordinates": {
      "coordinates": [
        -122.25831,
        37.871609
      ],
      "type": "Point"
    },
    "truncated": false,
    "createdAt": "Tue Aug 28 21:08:15 +0000 2012",
    "favorited": false,
    "id_str": "240556426106372096",
    "in_reply_to_user_id_str": null,
    "entities": {
      "urls": [
        {
          "expanded_url": "http://blogs.ischool.berkeley.edu/i290-abdt-s12/",
          "url": "http://t.co/bfj7zkDJ",
          "indices": [
            79,
            99
          ],
          "display_url": "blogs.ischool.berkeley.edu/i290-abdt-s12/"
        }
      ],
      "hashtags": [

      ],
      "user_mentions": [
        {
          "name": "Cal",
          "id_str": "17445752",
          "id": 17445752,
          "indices": [
            60,
            64
          ],
          "screen_name": "Cal"
        },
        {
          "name": "Othman Laraki",
          "id_str": "20495814",
          "id": 20495814,
          "indices": [
            70,
            77
          ],
          "screen_name": "othman"
        }
      ]
    },
    "text": "lecturing at the \"analyzing big data with twitter\" class at @cal with @othman  http://t.co/bfj7zkDJ",
    "contributors": null,
    "id": 240556426106372096,
    "retweet_count": 3,
    "in_reply_to_status_id_str": null,
    "geo": {
      "coordinates": [
        37.871609,
        -122.25831
      ],
      "type": "Point"
    },
    "retweeted": false,
    "possibly_sensitive": false,
    "in_reply_to_user_id": null,
    "place": {
      "name": "Berkeley",
      "country_code": "US",
      "country": "United States",
      "attributes": {
      },
      "url": "http://api.twitter.com/1/geo/id/5ef5b7f391e30aff.json",
      "id": "5ef5b7f391e30aff",
      "bounding_box": {
        "coordinates": [
          [
            [
              -122.367781,
              37.835727
            ],
            [
              -122.234185,
              37.835727
            ],
            [
              -122.234185,
              37.905824
            ],
            [
              -122.367781,
              37.905824
            ]
          ]
        ],
        "type": "Polygon"
      },
      "full_name": "Berkeley, CA",
      "place_type": "city"
    },
    "source": "Safari on iOS",
    "user": {
      "name": "Raffi Krikorian",
      "profile_sidebar_fill_color": "DDEEF6",
      "profile_background_tile": false,
      "profile_sidebar_border_color": "C0DEED",
      "profile_image_url": "http://a0.twimg.com/profile_images/1270234259/raffi-headshot-casual_normal.png",
      "createdAt": "Sun Aug 19 14:24:06 +0000 2007",
      "location": "San Francisco, California",
      "follow_request_sent": false,
      "id_str": "8285392",
      "is_translator": false,
      "profile_link_color": "0084B4",
      "entities": {
        "url": {
          "urls": [
            {
              "expanded_url": "http://about.me/raffi.krikorian",
              "url": "http://t.co/eNmnM6q",
              "indices": [
                0,
                19
              ],
              "display_url": "about.me/raffi.krikorian"
            }
          ]
        },
        "description": {
          "urls": [

          ]
        }
      },
      "default_profile": true,
      "url": "http://t.co/eNmnM6q",
      "contributors_enabled": false,
      "favourites_count": 724,
      "utc_offset": -28800,
      "profile_image_url_https": "https://si0.twimg.com/profile_images/1270234259/raffi-headshot-casual_normal.png",
      "id": 8285392,
      "listed_count": 619,
      "profile_use_background_image": true,
      "profile_text_color": "333333",
      "followers_count": 18752,
      "lang": "en",
      "protected": false,
      "geo_enabled": true,
      "notifications": false,
      "description": "Director of @twittereng's Platform Services. I break things.",
      "profile_background_color": "C0DEED",
      "verified": false,
      "time_zone": "Pacific Time (US & Canada)",
      "profile_background_image_url_https": "https://si0.twimg.com/images/themes/theme1/bg.png",
      "statuses_count": 5007,
      "profile_background_image_url": "http://a0.twimg.com/images/themes/theme1/bg.png",
      "default_profile_image": false,
      "friends_count": 701,
      "following": true,
      "show_all_inline_media": true,
      "screen_name": "raffi"
    },
    "in_reply_to_screen_name": null,
    "in_reply_to_status_id": null
  },
  {
    "coordinates": null,
    "truncated": false,
    "createdAt": "Tue Aug 28 19:59:34 +0000 2012",
    "favorited": false,
    "id_str": "240539141056638977",
    "in_reply_to_user_id_str": null,
    "entities": {
      "urls": [

      ],
      "hashtags": [

      ],
      "user_mentions": [

      ]
    },
    "text": "You'd be right more often if you thought you were wrong.",
    "contributors": null,
    "id": 240539141056638977,
    "retweet_count": 1,
    "in_reply_to_status_id_str": null,
    "geo": null,
    "retweeted": false,
    "in_reply_to_user_id": null,
    "place": null,
    "source": "web",
    "user": {
      "name": "Taylor Singletary",
      "profile_sidebar_fill_color": "FBFBFB",
      "profile_background_tile": true,
      "profile_sidebar_border_color": "000000",
      "profile_image_url": "http://a0.twimg.com/profile_images/2546730059/f6a8zq58mg1hn0ha8vie_normal.jpeg",
      "createdAt": "Wed Mar 07 22:23:19 +0000 2007",
      "location": "San Francisco, CA",
      "follow_request_sent": false,
      "id_str": "819797",
      "is_translator": false,
      "profile_link_color": "c71818",
      "entities": {
        "url": {
          "urls": [
            {
              "expanded_url": "http://www.rebelmouse.com/episod/",
              "url": "http://t.co/Lxw7upbN",
              "indices": [
                0,
                20
              ],
              "display_url": "rebelmouse.com/episod/"
            }
          ]
        },
        "description": {
          "urls": [

          ]
        }
      },
      "default_profile": false,
      "url": "http://t.co/Lxw7upbN",
      "contributors_enabled": false,
      "favourites_count": 15990,
      "utc_offset": -28800,
      "profile_image_url_https": "https://si0.twimg.com/profile_images/2546730059/f6a8zq58mg1hn0ha8vie_normal.jpeg",
      "id": 819797,
      "listed_count": 340,
      "profile_use_background_image": true,
      "profile_text_color": "D20909",
      "followers_count": 7126,
      "lang": "en",
      "protected": false,
      "geo_enabled": true,
      "notifications": false,
      "description": "Reality Technician, Twitter API team, synthesizer enthusiast; a most excellent adventure in timelines. I know it's hard to believe in something you can't see.",
      "profile_background_color": "000000",
      "verified": false,
      "time_zone": "Pacific Time (US & Canada)",
      "profile_background_image_url_https": "https://si0.twimg.com/profile_background_images/643655842/hzfv12wini4q60zzrthg.png",
      "statuses_count": 18076,
      "profile_background_image_url": "http://a0.twimg.com/profile_background_images/643655842/hzfv12wini4q60zzrthg.png",
      "default_profile_image": false,
      "friends_count": 5444,
      "following": true,
      "show_all_inline_media": true,
      "screen_name": "episod"
    },
    "in_reply_to_screen_name": null,
    "in_reply_to_status_id": null
  }
]
 */