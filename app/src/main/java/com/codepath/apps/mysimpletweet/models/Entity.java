package com.codepath.apps.mysimpletweet.models;

import android.util.Log;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by i_enpinghsieh on 2017/2/27.
 */
@Table(database = MyTweetDatabase.class)
@Parcel(analyze = {Entity.class})
public class Entity extends BaseModel {


    @PrimaryKey
    String source_status_id_str;

    List<Media> media;


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "media")
    public List<Media> getMedia() {
        if (media == null || media.isEmpty()) {
            media = SQLite.select()
                    .from(Media.class)
                    .where(Media_Table.source_status_id_str.eq(source_status_id_str))
                    .queryList();
        }
        return media;
    }

    public Entity() {
    }

    public Entity(JSONObject object,String source_status_id_str) {
        super();

        try {
            this.source_status_id_str = source_status_id_str;
            this.media = fromJson(object.getJSONArray("media"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Media> fromJson(JSONArray jsonArray) {
        ArrayList<Media> medias = new ArrayList<>(jsonArray.length());

        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject medJson = null;
            try {
                medJson = jsonArray.getJSONObject(i);
                Media media = new Media(medJson);
                media.save();
                medias.add(media);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return medias;
    }

    public void setMedia(ArrayList<Media> media) {
        this.media = media;
    }

    public String getSource_status_id_str() {
        return source_status_id_str;
    }

    public void setSource_status_id_str(String source_status_id_str) {
        this.source_status_id_str = source_status_id_str;
    }
}
