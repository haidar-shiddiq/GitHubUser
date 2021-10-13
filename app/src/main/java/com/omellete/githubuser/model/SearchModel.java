package com.omellete.githubuser.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SearchModel implements Parcelable {

    @SerializedName("login")
    private final String login;

    @SerializedName("url")
    private final String url;

    @SerializedName("avatar_url")
    private final String avatarUrl;

    @SerializedName("html_url")
    private String htmlUrl;

    public String getLogin() {
        return login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    protected SearchModel(Parcel in) {
        this.avatarUrl = in.readString();
        this.login = in.readString();
        this.url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.avatarUrl);
        parcel.writeString(this.login);
        parcel.writeString(this.url);
    }

    public static final Parcelable.Creator<SearchModel> CREATOR = new Parcelable.Creator<SearchModel>() {
        @Override
        public SearchModel createFromParcel(Parcel in) {
            return new SearchModel(in);
        }

        @Override
        public SearchModel[] newArray(int size) {
            return new SearchModel[size];
        }
    };

}
