package com.omellete.githubuser.model;

import com.google.gson.annotations.SerializedName;

public class DetailModel {

    @SerializedName("id")
    private int id;

    @SerializedName("login")
    private String login;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("name")
    private String name;

    @SerializedName("company")
    private String company;

    @SerializedName("location")
    private String location;

    @SerializedName("public_repos")
    private String publicRepos;

    @SerializedName("followers")
    private String followers;

    @SerializedName("following")
    private String following;

    public DetailModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }


    public String getAvatarUrl() {
        return avatarUrl;
    }


    public String getCompany() {
        return company;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getLocation() {
        return location;
    }


    public String getPublicRepos() {
        return publicRepos;
    }


    public String getFollowers() {
        return followers;
    }


    public String getFollowing() {
        return following;
    }

}
