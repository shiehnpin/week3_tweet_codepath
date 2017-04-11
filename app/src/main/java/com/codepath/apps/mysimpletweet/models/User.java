package com.codepath.apps.mysimpletweet.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ManyToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by Chjeng-Lun SHIEH on 2016/12/4.
 */
@Table(database = MyTweetDatabase.class)
@Parcel(analyze={User.class})   // add Parceler annotation here
public class User extends BaseModel {

    @PrimaryKey
    @Column
    String id;

    @Column
    String name;

    @Column
    String screen_name;

    @Column
    String profile_image_url_https;


    public User(){
        super();
    }

    public User(JSONObject object) {
        try {
            this.id = object.getString("id_str");
            this.name = object.getString("name");
            this.screen_name = object.getString("screen_name");
            this.profile_image_url_https = object.getString("profile_image_url_https");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public String getProfile_image_url_https() {
        return profile_image_url_https;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public void setProfile_image_url_https(String profile_image_url_https) {
        this.profile_image_url_https = profile_image_url_https;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", screen_name='" + screen_name + '\'' +
                ", profile_image_url_https='" + profile_image_url_https + '\'' +
                '}';
    }
}
