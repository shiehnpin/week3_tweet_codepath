package com.codepath.apps.mysimpletweet.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.List;

/**
 * Created by i_enpinghsieh on 2017/2/27.
 */

@Parcel(analyze = {Media.class})
@Table(database = MyTweetDatabase.class)
public class Media extends BaseModel {


    /**
     * id : 266031293949698048
     * id_str : 266031293949698048
     * indices : [17,37]
     * media_url : http://pbs.twimg.com/media/A7EiDWcCYAAZT1D.jpg
     * media_url_https : https://pbs.twimg.com/media/A7EiDWcCYAAZT1D.jpg
     * url : http://t.co/bAJE6Vom
     * display_url : pic.twitter.com/bAJE6Vom
     * expanded_url : http://twitter.com/BarackObama/status/266031293945503744/photo/1
     * type : photo
     */

    @PrimaryKey
    @Column
    Long id;

    @Column
    String id_str;

    @Column
    String media_url;

    @Column
    String media_url_https;
    @Column
    String url;
    @Column
    String display_url;
    @Column
    String expanded_url;
    @Column
    String type;
    @Column
    String source_status_id_str;

    public Media(JSONObject medJson) {

        try {
            id = medJson.getLong("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            id_str = medJson.getString("id_str");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            media_url = medJson.getString("media_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            media_url_https = medJson.getString("media_url_http");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            url = medJson.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            display_url = medJson.getString("display_url") ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            expanded_url = medJson.getString("expanded_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            type = medJson.getString("type")  ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            source_status_id_str = medJson.getString("source_status_id_str");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Media() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getId_str() {
        return id_str;
    }

    public void setId_str(String id_str) {
        this.id_str = id_str;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public String getMedia_url_https() {
        return media_url_https;
    }

    public void setMedia_url_https(String media_url_https) {
        this.media_url_https = media_url_https;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDisplay_url() {
        return display_url;
    }

    public void setDisplay_url(String display_url) {
        this.display_url = display_url;
    }

    public String getExpanded_url() {
        return expanded_url;
    }

    public void setExpanded_url(String expanded_url) {
        this.expanded_url = expanded_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource_status_id_str() {
        return source_status_id_str;
    }

    public void setSource_status_id_str(String source_status_id_str) {
        this.source_status_id_str = source_status_id_str;
    }
}
