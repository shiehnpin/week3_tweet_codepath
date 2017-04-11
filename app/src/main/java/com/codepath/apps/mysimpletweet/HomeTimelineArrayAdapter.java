package com.codepath.apps.mysimpletweet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codepath.apps.mysimpletweet.models.Media;
import com.codepath.apps.mysimpletweet.models.Tweet;
import com.codepath.apps.mysimpletweet.models.User;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

/**
 * Created by i_enpinghsieh on 2017/2/25.
 */

public class HomeTimelineArrayAdapter extends RecyclerView.Adapter<HomeTimelineArrayAdapter.TweetViewHolder> {

    ArrayList<Tweet> list = new ArrayList<>();

    public Tweet getItem(int pos) {
        return list.get(pos);
    }

    public void addAll(List<Tweet> tweets) {
        list.addAll(tweets);
        notifyItemRangeInserted(list.size(),tweets.size());
    }

    public void insert(Tweet tweet, int pos) {
        list.add(pos,tweet);
        notifyItemInserted(pos);
    }

    static class TweetViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfileImage;
        TextView txName;
        TextView txScreenName;
        TextView txContent;
        TextView txCreatedAt;
        LinearLayout llMedia;


        public TweetViewHolder(View v) {
            super(v);

            ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImg);
            txName = (TextView) v.findViewById(R.id.txName);
            txScreenName = (TextView) v.findViewById(R.id.txScreenName);
            txCreatedAt = (TextView) v.findViewById(R.id.txCreatedAt);
            txContent = (TextView) v.findViewById(R.id.txContent);

            llMedia = (LinearLayout) v.findViewById(R.id.ll_media);
        }
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet,parent,false);
        return new TweetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TweetViewHolder holder, int position) {
        Tweet tweet = getItem(position);
        try {
            User user = new User(new JSONObject(tweet.getUser()));
            holder.txScreenName.setText(String.format("@%s",user.getScreen_name()));
            holder.txName.setText(user.getName());
            Picasso.with(getContext()).load(user.getProfile_image_url_https()).noFade().fit().into(holder.ivProfileImage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.txCreatedAt.setText(Utility.getRelativeTimeAgo(tweet.getCreated_at()));
        try {
            holder.txContent.setText(URLDecoder.decode(tweet.getText(),"utf8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.llMedia.removeAllViews();
        if(tweet.getEntities()!=null && tweet.getEntities().getMedia()!=null){
            for(Media media:tweet.getEntities().getMedia()){
                ImageView imageView = new ImageView(getContext());
                Picasso.with(getContext()).load(media.getMedia_url()).into(imageView);
                holder.llMedia.addView(imageView);
            }
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
