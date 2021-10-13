package com.omellete.githubuser.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ModelFollow implements Parcelable {

    @SerializedName("login")
    private String login;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("html_url")
    private final String htmlUrl;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(avatarUrl);
        parcel.writeString(login);
        parcel.writeString(htmlUrl);
    }

    protected ModelFollow(Parcel in) {
        avatarUrl = in.readString();
        login = in.readString();
        htmlUrl = in.readString();
    }

    public static final Creator<ModelFollow> CREATOR = new Creator<ModelFollow>() {
        @Override
        public ModelFollow createFromParcel(Parcel in) {
            return new ModelFollow(in);
        }

        @Override
        public ModelFollow[] newArray(int size) {
            return new ModelFollow[size];
        }
    };

}
