package fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sundeepy.basictwitter.EndlessScrollListener;
import com.sundeepy.basictwitter.ProfileActivity;
import com.sundeepy.basictwitter.TweetArrayAdapter;
import com.sundeepy.basictwitter.TwitterApplication;
import com.sundeepy.basictwitter.TwitterClient;
import com.sundeepy.basictwitter.models.Tweet;
import com.sundeepy.basictwitter.R;

public class TweetsListFragment extends Fragment {

	private List<Tweet> tweets;
	private TweetArrayAdapter aTweets;
	private ListView lvTweets;
	private SwipeRefreshLayout swipeContainer;
	private static long maxId = -1;
	private TwitterClient twitterClient;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
		twitterClient = TwitterApplication.getRestClient();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_tweetslist, container,
				false);
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);
		swipeContainer = (SwipeRefreshLayout) v
				.findViewById(R.id.swipeContainer);

		populateTimeline(maxId);
		setUpListeners();
		return v;
	}

	private void setUpListeners() {
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// Triggered only when new data needs to be appended to the list
				// Add whatever code is needed to append new items to your
				// AdapterView
				populateTimeline(maxId);
				// or customLoadMoreDataFromApi(totalItemsCount);
			}
		});

		// Setup refresh listener which triggers new data loading
		swipeContainer.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// Your code to refresh the list here.
				// Make sure you call swipeContainer.setRefreshing(false)
				// once the network request has completed successfully.
				resetAdapterAndMaxIdAndPopulateTimeline();

			}
		});
		// Configure the refreshing colors
		swipeContainer.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);

		lvTweets.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View parent,
					int position, long rowID) {
				Intent i = new Intent(getActivity().getApplicationContext(),
						ProfileActivity.class);
				Tweet tweet = tweets.get(position);
				i.putExtra("tweet", tweet);
				startActivity(i);

			}

		});

	}

	public void resetAdapterAndMaxIdAndPopulateTimeline() {
		maxId = -1;
		aTweets.clear();
		populateTimeline(maxId);
	}

	public void populateTimeline(long maxId) {

		twitterClient.getHomeClient(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONArray jsonArray) {
				List<Tweet> tweetsList = Tweet.fromJsonArray(jsonArray);
				System.out.println(jsonArray);
				addAllTweets(tweetsList);
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		}, maxId);

	}

	public void addAllTweets(List<Tweet> tweetsList) {
		aTweets.addAll(tweetsList);
		aTweets.notifyDataSetChanged();
		if (tweets.size() > 0) {
			maxId = tweets.get(tweets.size() - 1).getTweetId() - 1;
		}
		swipeContainer.setRefreshing(false);
	}

	public TweetArrayAdapter getAdapter() {
		return aTweets;
	}

}
