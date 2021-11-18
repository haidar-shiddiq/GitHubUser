package com.omellete.githubuser.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omellete.githubuser.api.ApiInterface;
import com.omellete.githubuser.api.ApiService;
import com.omellete.githubuser.model.FavoriteModel;
import com.omellete.githubuser.model.DetailModel;
import com.omellete.githubuser.model.ItemModel;
import com.omellete.githubuser.model.ModelFollow;
import com.omellete.githubuser.model.SearchModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<SearchModel>> searchModelMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<DetailModel> modelUserMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<ModelFollow>> modelFollowersMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<ModelFollow>> modelFollowingMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<FavoriteModel>> modelFavoriteMutableLiveData = new MutableLiveData<>();

    public static String APIKey = "YOUR API KEY HERE";

    public void searchUsername(String query) {
        ApiInterface apiService = ApiService.getClient().create(ApiInterface.class);
        Call<ItemModel> call = apiService.searchUser(APIKey, query);
        call.enqueue(new Callback<ItemModel>() {
            @Override
            public void onResponse(Call<ItemModel> call, Response<ItemModel> response) {
                if (!response.isSuccessful()) {
                    Log.e("response", response.toString());
                } else if (response.body() != null) {
                    ArrayList<SearchModel> items = new ArrayList<>(response.body().getModelSearchData());
                    searchModelMutableLiveData.setValue(items);
                }
            }

            @Override
            public void onFailure(Call<ItemModel> call, Throwable t) {
                Log.e("failure", t.toString());
            }
        });
    }

    public void setUserDetail(String username) {
        ApiInterface apiService = ApiService.getClient().create(ApiInterface.class);
        Call<DetailModel> call = apiService.detailUser(username);
        call.enqueue(new Callback<DetailModel>() {

            @Override
            public void onResponse(Call<DetailModel> call, Response<DetailModel> response) {
                if (!response.isSuccessful()) {
                    Log.e("response", response.toString());
                } else if (response.body() != null) {
                    modelUserMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<DetailModel> call, Throwable t) {
                Log.e("failure", t.toString());
            }
        });
    }

    public void setFollowers(String username) {
        ApiInterface apiService = ApiService.getClient().create(ApiInterface.class);

        Call<ArrayList<ModelFollow>> call = apiService.followersUser(APIKey, username);
        call.enqueue(new Callback<ArrayList<ModelFollow>>() {
            @Override
            public void onResponse(Call<ArrayList<ModelFollow>> call, Response<ArrayList<ModelFollow>> response) {
                if (!response.isSuccessful()) {
                    Log.e("response", response.toString());
                } else if (response.body() != null) {
                    modelFollowersMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ModelFollow>> call, Throwable t) {
                Log.e("failure", t.toString());
            }
        });
    }

    public void setFollowing(String username) {
        ApiInterface apiService = ApiService.getClient().create(ApiInterface.class);

        Call<ArrayList<ModelFollow>> call = apiService.followingUser(APIKey, username);
        call.enqueue(new Callback<ArrayList<ModelFollow>>() {
            @Override
            public void onResponse(Call<ArrayList<ModelFollow>> call, Response<ArrayList<ModelFollow>> response) {
                if (!response.isSuccessful()) {
                    Log.e("response", response.toString());
                } else if (response.body() != null) {
                    modelFollowingMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ModelFollow>> call, Throwable t) {
                Log.e("failure", t.toString());
            }
        });
    }

    public LiveData<ArrayList<SearchModel>> getResultList() {
        return searchModelMutableLiveData;
    }


    public LiveData<DetailModel> getUserList() {
        return modelUserMutableLiveData;
    }

    public LiveData<ArrayList<ModelFollow>> getFollowersUser() {
        return modelFollowersMutableLiveData;
    }

    public LiveData<ArrayList<ModelFollow>> getFollowingUser() {
        return modelFollowingMutableLiveData;
    }

}
