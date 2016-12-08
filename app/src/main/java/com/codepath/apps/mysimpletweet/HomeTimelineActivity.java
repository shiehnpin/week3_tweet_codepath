package com.codepath.apps.mysimpletweet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweet.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class HomeTimelineActivity extends AppCompatActivity {

    private static final String TAG = "ActivityHomeTimeline";
    private RestClient client;
    private ListView lvHomeTimeline;
    private ArrayList<Tweet> tweetArrayList;
    private HomeTimelineArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_timeline);
        client  = TweetApplication.getRestClient();
        lvHomeTimeline = (ListView) findViewById(R.id.lvTimeline);
        tweetArrayList = new ArrayList<Tweet>();
        adapter = new HomeTimelineArrayAdapter(getBaseContext(),R.layout.item_tweet,tweetArrayList);
        lvHomeTimeline.setAdapter(adapter);
        lvHomeTimeline.setOnScrollListener(new EndlessScrollListener() {

            private long getMaxId(int totalItemsCount){
                try {
                    return Long.parseLong(adapter.getItem(totalItemsCount - 1).getId()) - 1;
                } catch (NullPointerException e){
                    return 0;
                }
            }

            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                client.getHomeTimeline(Constant.REQUEST_TWEETS_COUNT,getMaxId(totalItemsCount),new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        adapter.addAll(Tweet.fromJson(response));
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                    }
                });
                return true;
            }
        });

        //Todo Workaround
        if(Tweet.countItems() == 0) {
            populateTimeline(Constant.REQUEST_TWEETS_COUNT);
        }else{
            adapter.addAll(Tweet.recentItems());
        }

    }

    private void populateTimeline(int count) {
        populateTimeline(count,null);
    }


    private void populateTimeline(int count, Long max_id){
        client.getHomeTimeline(count, max_id ,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Tweet.fromJson(response);
                adapter.addAll(Tweet.recentItems());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_timeline,menu);
        return true;
    }

    private class HomeTimelineArrayAdapter extends ArrayAdapter<Tweet>{
        public HomeTimelineArrayAdapter(Context context, int textViewResourceId, List<Tweet> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Tweet tweet = getItem(position);
            ViewHolder holder;

            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet,parent,false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }

            holder = (ViewHolder) convertView.getTag();
            holder.txCreatedAt.setText(Utlity.toFriendlyTimestamp(tweet.getCreated_at()));
            holder.txContent.setText(tweet.getText());
            holder.txScreenName.setText(tweet.getUser().getScreen_name());
            holder.txName.setText(String.format("@%s", tweet.getUser().getName()));
            Picasso.with(getContext()).load(tweet.getUser().getProfile_image_url_https()).noFade().fit().into(holder.ivProfileImage);
            return convertView;
        }

        private class ViewHolder {
            ImageView ivProfileImage;
            TextView txName;
            TextView txScreenName;
            TextView txContent;
            TextView txCreatedAt;

            public ViewHolder(View v) {
                ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImg);
                txName = (TextView) v.findViewById(R.id.txName);
                txScreenName = (TextView) v.findViewById(R.id.txScreenName);
                txCreatedAt = (TextView) v.findViewById(R.id.txCreatedAt);
                txContent = (TextView) v.findViewById(R.id.txContent);
            }
        }
    }
}
