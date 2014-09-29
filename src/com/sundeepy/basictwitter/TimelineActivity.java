package com.sundeepy.basictwitter;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sundeepy.basictwitter.listeners.FragmentTabListener;
import com.sundeepy.basictwitter.util.IntentConstants;
import com.sundeepy.basictwitter.R;

import fragment.HomeTimeLineFragment;
import fragment.MentionsTimelineFragment;
import fragment.TweetsListFragment;

public class TimelineActivity extends FragmentActivity {
	private TwitterClient twitterClient;
	private TweetsListFragment fragmentTweetList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		twitterClient = TwitterApplication.getRestClient();
		// fragmentTweetList = (TweetsListFragment) getSupportFragmentManager()
		// .findFragmentById(R.id.flContainer);
		setupTabs();

	}

	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
				.newTab()
				.setText("Home")
				.setIcon(R.drawable.ic_home)
				.setTag("HomeTimelineFragment")
				.setTabListener(
						new FragmentTabListener<HomeTimeLineFragment>(
								R.id.flContainer, this, "first",
								HomeTimeLineFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
				.newTab()
				.setText("Mention")
				.setTag("MentionsTimelineFragment")
				.setTabListener(
						new FragmentTabListener<MentionsTimelineFragment>(
								R.id.flContainer, this, "second",
								MentionsTimelineFragment.class));

		actionBar.addTab(tab2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);

		return true;
	}

	public void onCompose(MenuItem mi) {
		Intent iCompose = new Intent(this, ComposeActivity.class);
		startActivityForResult(iCompose, IntentConstants.COMPOSE_INTENT);
	}
	
	
	public void onProfileView(MenuItem mi) {
		Intent iProfileView = new Intent(this, ProfileActivity.class);
		startActivity(iProfileView);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == IntentConstants.COMPOSE_INTENT) {
			if (resultCode == RESULT_OK) {
				String tweet = data.getStringExtra("tweet");
				twitterClient.postTweetClient(new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(JSONObject json) {
						// fragmentTweetList
						// .resetAdapterAndMaxIdAndPopulateTimeline();
					}

					@Override
					public void onFailure(Throwable e, String s) {
						Log.d("debug", e.toString());
						Log.d("debug", s.toString());
					}
				}, tweet);
			}
		}
	}
}
