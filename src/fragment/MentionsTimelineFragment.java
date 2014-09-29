package fragment;

import java.util.List;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sundeepy.basictwitter.TwitterApplication;
import com.sundeepy.basictwitter.TwitterClient;
import com.sundeepy.basictwitter.models.Tweet;

public class MentionsTimelineFragment extends TweetsListFragment {
	private TwitterClient twitterClient;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		twitterClient = TwitterApplication.getRestClient();
	}
	
	
	public void populateTimeline(long maxId) {

		twitterClient.getMentionsTimeline(new JsonHttpResponseHandler() {

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

}
