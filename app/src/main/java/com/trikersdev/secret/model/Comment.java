package com.trikersdev.secret.model;

import android.app.Application;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONObject;

import com.trikersdev.secret.constants.Constants;

public class Comment extends Application implements Constants, Parcelable {

    private long id, postId, fromUserId, replyToUserId, postFromUserId;
    private int fromUserState, fromUserVerify, createAt, likesCount, commentatorId;
    private String comment, timeAgo, title, image, area, country, city;
    private Double lat = 0.000000, lng = 0.000000;
    private Boolean myLike;

    public Comment() {

    }

    public Comment(JSONObject jsonData) {

        try {

            this.setId(jsonData.getLong("id"));
            this.setCommentatorId(jsonData.getInt("commentatorId"));
            this.setItemId(jsonData.getLong("postId"));
            this.setFromUserId(jsonData.getLong("fromUserId"));
            this.setFromUserState(jsonData.getInt("fromUserState"));
            this.setReplyToUserId(jsonData.getLong("replyToUserId"));
            this.setText(jsonData.getString("comment"));
            this.setTitle(jsonData.getString("title"));
            this.setImage(jsonData.getString("image"));
            this.setTimeAgo(jsonData.getString("timeAgo"));
            this.setCreateAt(jsonData.getInt("createAt"));

            this.setPostFromUserId(jsonData.getLong("postFromUserId"));

            this.setArea(jsonData.getString("area"));
            this.setCountry(jsonData.getString("country"));
            this.setCity(jsonData.getString("city"));
            this.setLikesCount(jsonData.getInt("likesCount"));
            this.setMyLike(jsonData.getBoolean("myLike"));
            this.setLat(jsonData.getDouble("lat"));
            this.setLng(jsonData.getDouble("lng"));

        } catch (Throwable t) {

            Log.e("Comment", "Could not parse malformed JSON: \"" + jsonData.toString() + "\"");

        } finally {

            Log.d("Comment", jsonData.toString());
        }
    }

    public void setId(long id) {

        this.id = id;
    }

    public long getId() {

        return this.id;
    }

    public void setItemId(long postId) {

        this.postId = postId;
    }

    public long getItemId() {

        return this.postId;
    }

    public void setFromUserId(long fromUserId) {

        this.fromUserId = fromUserId;
    }

    public long getFromUserId() {

        return this.fromUserId;
    }

    public void setPostFromUserId(long postFromUserId) {

        this.postFromUserId = postFromUserId;
    }

    public long getPostFromUserId() {

        return this.postFromUserId;
    }

    public void setCommentatorId(int commentatorId) {

        this.commentatorId = commentatorId;
    }

    public int getCommentatorId() {

        return this.commentatorId;
    }

    public void setReplyToUserId(long replyToUserId) {

        this.replyToUserId = replyToUserId;
    }

    public long getReplyToUserId() {

        return this.replyToUserId;
    }

    public void setFromUserState(int fromUserState) {

        this.fromUserState = fromUserState;
    }

    public int getFromUserState() {

        return this.fromUserState;
    }

    public void setFromUserVerify(int fromUserVerify) {

        this.fromUserVerify = fromUserVerify;
    }

    public int getFromUserVerify() {

        return this.fromUserVerify;
    }

    public void setText(String comment) {

        this.comment = comment;
    }

    public String getText() {

        return this.comment;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getTitle() {

        return this.title;
    }

    public void setImage(String image) {

        this.image = image;
    }

    public String getImage() {

        return this.image;
    }

    public void setTimeAgo(String timeAgo) {

        this.timeAgo = timeAgo;
    }

    public String getTimeAgo() {

        return this.timeAgo;
    }

    public void setCreateAt(int createAt) {

        this.createAt = createAt;
    }

    public int getCreateAt() {

        return this.createAt;
    }

    public int getLikesCount() {

        return this.likesCount;
    }

    public void setLikesCount(int likesCount) {

        this.likesCount = likesCount;
    }

    public String getArea() {

        if (this.area == null) {

            this.area = "";
        }

        return this.area;
    }

    public void setArea(String area) {

        this.area = area;
    }

    public String getCountry() {

        if (this.country == null) {

            this.country = "";
        }

        return this.country;
    }

    public void setCountry(String country) {

        this.country = country;
    }

    public String getCity() {

        if (this.city == null) {

            this.city = "";
        }

        return this.city;
    }

    public void setCity(String city) {

        this.city = city;
    }

    public Double getLat() {

        return this.lat;
    }

    public void setLat(Double lat) {

        this.lat = lat;
    }

    public Double getLng() {

        return this.lng;
    }

    public void setLng(Double lng) {

        this.lng = lng;
    }

    public Boolean isMyLike() {

        return myLike;
    }

    public void setMyLike(Boolean myLike) {

        this.myLike = myLike;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.postId);
        dest.writeLong(this.fromUserId);
        dest.writeLong(this.replyToUserId);
        dest.writeLong(this.postFromUserId);
        dest.writeInt(this.fromUserState);
        dest.writeInt(this.fromUserVerify);
        dest.writeInt(this.createAt);
        dest.writeInt(this.likesCount);
        dest.writeInt(this.commentatorId);
        dest.writeString(this.comment);
        dest.writeString(this.timeAgo);
        dest.writeString(this.title);
        dest.writeString(this.image);
        dest.writeString(this.area);
        dest.writeString(this.country);
        dest.writeString(this.city);
        dest.writeValue(this.lat);
        dest.writeValue(this.lng);
        dest.writeValue(this.myLike);
    }

    protected Comment(Parcel in) {
        this.id = in.readLong();
        this.postId = in.readLong();
        this.fromUserId = in.readLong();
        this.replyToUserId = in.readLong();
        this.postFromUserId = in.readLong();
        this.fromUserState = in.readInt();
        this.fromUserVerify = in.readInt();
        this.createAt = in.readInt();
        this.likesCount = in.readInt();
        this.commentatorId = in.readInt();
        this.comment = in.readString();
        this.timeAgo = in.readString();
        this.title = in.readString();
        this.image = in.readString();
        this.area = in.readString();
        this.country = in.readString();
        this.city = in.readString();
        this.lat = (Double) in.readValue(Double.class.getClassLoader());
        this.lng = (Double) in.readValue(Double.class.getClassLoader());
        this.myLike = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
