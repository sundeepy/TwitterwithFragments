package fragment;

import org.json.JSONArray;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sundeepy.basictwitter.TwitterApplication;
import com.sundeepy.basictwitter.models.Tweet;

import android.os.Bundle;

public class UserTimelineFragment extends TweetsListFragment {

	Long userId = -1L;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getActivity().getIntent().getSerializableExtra("tweet") != null) {
			Tweet tweet = (Tweet) (getActivity().getIntent().getSerializableExtra("tweet"));
			userId = tweet.getUser().getUserId();
		}
		populateTimeline(-1);
		
	}

	public void populateTimeline(long maxId) {
		
		TwitterApplication.getRestClient().getUserTimeline(
				new JsonHttpResponseHandler() {

					@Override
					public void onSuccess(JSONArray jsonArray) {

						getAdapter().addAll(Tweet.fromJsonArray(jsonArray));
					}

				}, userId, maxId);
	}

}
