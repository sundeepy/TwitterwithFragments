package com.sundeepy.basictwitter.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5773670155424508538L;

	public String body;

	public long tweetId;

	public String createdAt;

	public User user;

	public String getBody() {
		return body;
	}

	public Tweet() {
		super();
	}

	public long getTweetId() {
		return tweetId;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public User getUser() {
		return user;
	}

	public static Tweet fromJson(JSONObject json) {
		Tweet tweet = new Tweet();
		// extract values form json
		try {
			tweet.body = json.getString("text");
			tweet.createdAt = json.getString("created_at");
			tweet.tweetId = json.getLong("id");
			tweet.user = User.fromJson(json.getJSONObject("user"));

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return tweet;
	}

	public static List<Tweet> fromJsonArray(JSONArray jsonArray) {
		List<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());
		// Process each result in json array, decode and convert to business
		// object
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject json = null;
			try {
				json = jsonArray.getJSONObject(i);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

			Tweet tweet = Tweet.fromJson(json);
			if (tweet != null) {
				tweets.add(tweet);
			}
		}

		return tweets;
	}

	@Override
	public String toString() {
		return "Tweet [body=" + body + ", id=" + tweetId + ", createdAt="
				+ createdAt + ", user=" + user + "]";
	}

}
