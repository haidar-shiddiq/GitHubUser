package com.omellete.githubuser.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemModel {

    @SerializedName("items")
    private List<SearchModel> searchData;

    public List<SearchModel> getModelSearchData() {
        return searchData;
    }

    public void setSearchModel(List<SearchModel> searchData) {
        this.searchData = searchData;
    }

}
