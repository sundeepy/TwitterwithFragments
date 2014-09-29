package com.sundeepy.basictwitter;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sundeepy.basictwitter.models.Tweet;
import com.sundeepy.basictwitter.models.User;
import com.sundeepy.basictwitter.R;

public class ProfileActivity extends FragmentActivity {

	Tweet tweet = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		if (getIntent().getSerializableExtra("tweet") != null) {
			tweet = (Tweet) (getIntent().getSerializableExtra("tweet"));
		}
		if(tweet == null){
			loadProfileInfo();
		}else{
			populateProfileHeader(tweet.getUser());
		}
		
	}

	private void loadProfileInfo() {
		TwitterApplication.getRestClient().getUserInfo(
				new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(JSONObject jsonObject) {

						User u = User.fromJson(jsonObject);
						getActionBar().setTitle("@" + u.getScreenName());
						populateProfileHeader(u);
					}

					

				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
	
	private void populateProfileHeader(User u) {
		TextView tvUserName = (TextView) findViewById(R.id.tvUserName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		tvUserName.setText(u.getName());
		tvTagline.setText(u.getTagLine());
		tvFollowers.setText(String.valueOf(u.getFollowers())
				+ "  followers");
		tvFollowing.setText(String.valueOf(u.getFollowing())
				+ "  following");
		ImageLoader.getInstance().displayImage(
				u.getProfileImageUrl(), ivProfileImage);
	}

}
