package com.omellete.githubuser.api;

import com.omellete.githubuser.model.DetailModel;
import com.omellete.githubuser.model.ItemModel;
import com.omellete.githubuser.model.ModelFollow;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("users/{username}")
    Call<DetailModel> detailUser(@Path("username") String username);

    @GET("/search/users")
    Call<ItemModel> searchUser(@Header("Authorization") String authorization,
                               @Query("q") String username);

    @GET("users/{username}/followers")
    Call<ArrayList<ModelFollow>> followersUser(@Header("Authorization") String authorization,
                                               @Path("username") String username);

    @GET("users/{username}/following")
    Call<ArrayList<ModelFollow>> followingUser(@Header("Authorization") String authorization,
                                          @Path("username") String username);

}
