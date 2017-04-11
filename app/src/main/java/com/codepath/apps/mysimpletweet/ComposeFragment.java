package com.codepath.apps.mysimpletweet;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweet.models.Tweet;
import com.codepath.apps.mysimpletweet.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;


public class ComposeFragment extends DialogFragment {

    private static final String TAG = "COM";
    private RestClient client;
    private ImageView ivProfileAvatar;
    private TextView txName;
    private EditText etContent;
    private OnTweetSuccessListener listener;
    private TextView tvCount;
    private Button btnClose;
    private Button btnCompose;

    public static ComposeFragment newInstance(){
        return new ComposeFragment();
    }

    public ComposeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TweetApplication.getRestClient();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_compose,null);
        User user = Parcels.unwrap(getArguments().getParcelable("user"));

        ivProfileAvatar = (ImageView) view.findViewById(R.id.ivProfileAvatar);
        txName = (TextView) view.findViewById(R.id.txName);
        tvCount = (TextView) view.findViewById(R.id.tv_text_count);
        btnClose = (Button) view.findViewById(R.id.btn_close);
        btnCompose = (Button) view.findViewById(R.id.btn_send);
        etContent = (EditText) view.findViewById(R.id.etContent);

        Picasso.with(getContext()).load(user.getProfile_image_url_https()).into(ivProfileAvatar);
        txName.setText(user.getScreen_name());

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvCount.setText(""+(140-s.length()));
                btnCompose.setEnabled(s.length()<=140);
            }
        });

        btnCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.postTweet(etContent.getText().toString(),new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Tweet tweet = new Tweet(response);
                        tweet.save();
                        if(listener!=null) {
                            listener.onCreateSuccess(tweet.getId());
                            listener = null;
                        }
                        dismiss();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                    }
                });
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnTweetSuccessListener) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
