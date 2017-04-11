package com.codepath.apps.mysimpletweet;

import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.mysimpletweet.models.Tweet;
import com.codepath.apps.mysimpletweet.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class HomeTimelineActivity extends AppCompatActivity implements OnTweetSuccessListener{

    private static final String TAG = "ActivityHomeTimeline";
    private RestClient client;
    private RecyclerView lvHomeTimeline;
    private HomeTimelineArrayAdapter adapter;
    private SwipeRefreshLayout swipeContainer;
    private User user;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_timeline);
        lvHomeTimeline = (RecyclerView) findViewById(R.id.rl_timeline);
        adapter = new HomeTimelineArrayAdapter();

        client  = TweetApplication.getRestClient();

        layoutManager = new LinearLayoutManager(getBaseContext());
        lvHomeTimeline.setLayoutManager(layoutManager);
        lvHomeTimeline.setAdapter(adapter);
        lvHomeTimeline.addOnScrollListener(new EndlessRecyclerViewScrollListener( layoutManager) {

            private long getMaxId(int totalItemsCount){
                try {
                    return Long.parseLong(adapter.getItem(totalItemsCount - 1).getId()) - 1;
                } catch (NullPointerException e){
                    return 0;
                }
            }

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                client.getHomeTimeline(25,getMaxId(totalItemsCount),new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        adapter.addAll(Tweet.fromJson(response));
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Toast.makeText(getBaseContext(),"Fail!!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateTimeline(25);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        client.getAccountSetting(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String screenName = null;
                try {
                    screenName = response.getString("screen_name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                client.getUserShowByScreenName(screenName,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        user = new User(response);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

            }

        });

        //Todo Workaround
        if(Tweet.countItems() == 0) {
            populateTimeline(25);
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

                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getBaseContext(),"Fail!!",Toast.LENGTH_SHORT).show();
                swipeContainer.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_timeline,menu);
        return true;
    }


    private void showComposeDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ComposeFragment composeFragment = ComposeFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putParcelable("user",Parcels.wrap(user));
        composeFragment.setArguments(bundle);
        composeFragment.show(fragmentManager,"frag_compose");
    }

    @Override
    public void onCreateSuccess(String id) {
        adapter.insert(Tweet.byId(id),0);
        layoutManager.scrollToPosition(0);
    }

    public void btnCompose(View view) {
        if(user!=null) {
            showComposeDialog();
        }
    }
}
