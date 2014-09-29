package com.sundeepy.basictwitter.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5136274739057372583L;

	public String name;

	public long userId;

	public String screenName;

	public String profileImageUrl;

	private long followers;
	private long following;
	private String tagLine;

	public String getName() {
		return name;
	}

	public long getUserId() {
		return userId;
	}

	public User() {
		super();
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public long getFollowers() {
		return followers;
	}

	public void setFollowers(long followers) {
		this.followers = followers;
	}

	public long getFollowing() {
		return following;
	}

	public void setFollowing(long following) {
		this.following = following;
	}

	public String getTagLine() {
		return tagLine;
	}

	public void setTagLine(String tagLine) {
		this.tagLine = tagLine;
	}

	public static User fromJson(JSONObject json) {
		User user = new User();
		// extract values form json
		try {
			user.name = json.getString("name");
			user.userId = json.getLong("id");
			user.screenName = json.getString("screen_name");
			user.profileImageUrl = json.getString("profile_image_url");
			user.followers = json.getLong("followers_count");
			user.following = json.getLong("friends_count");
			user.tagLine = json.getString("description");

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", userId=" + userId + ", screenName="
				+ screenName + ", profileImageUrl=" + profileImageUrl
				+ ", followers=" + followers + ", following=" + following
				+ ", tagLine=" + tagLine + "]";
	}

}
