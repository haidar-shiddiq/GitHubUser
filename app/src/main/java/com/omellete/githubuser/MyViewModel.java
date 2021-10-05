package com.omellete.githubuser;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyViewModel extends ViewModel {

    private MutableLiveData<ArrayList<SearchModel>> searchModelMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ModelUser> modelUserMutableLiveData = new MutableLiveData<>();
//    private MutableLiveData<ArrayList<ModelFollow>> modelFollowersMutableLiveData = new MutableLiveData<>();
//    private MutableLiveData<ArrayList<ModelFollow>> modelFollowingMutableLiveData = new MutableLiveData<>();


    public static String Key = "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";

    public void searchUsername(String query) {
        ApiInterface apiService = ApiService.getClient().create(ApiInterface.class);
        Call<ItemModel> call = apiService.searchUser(Key, query);
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

    public void userDetail(String unamee) {
        ApiInterface apiService = ApiService.getClient().create(ApiInterface.class);
        Call<ModelUser> call = apiService.detailUser(unamee);
        call.enqueue(new Callback<ModelUser>() {

            @Override
            public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                if (!response.isSuccessful()) {
                    Log.e("response", response.toString());
                } else if (response.body() != null) {
                    modelUserMutableLiveData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<ModelUser> call, Throwable t) {
                Log.e("failure", t.toString());
            }
        });
    }

    //method get followers
//    public void setFollowersUser(String strUsername) {
//        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//
//        Call<ArrayList<ModelFollow>> call = apiService.followersUser(strApiKey, strUsername);
//        call.enqueue(new Callback<ArrayList<ModelFollow>>() {
//            @Override
//            public void onResponse(Call<ArrayList<ModelFollow>> call, Response<ArrayList<ModelFollow>> response) {
//                if (!response.isSuccessful()) {
//                    Log.e("response", response.toString());
//                } else if (response.body() != null) {
//                    modelFollowersMutableLiveData.setValue(response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<ModelFollow>> call, Throwable t) {
//                Log.e("failure", t.toString());
//            }
//        });
//    }

    //method get following
//    public void setFollowingUser(String strUsername) {
//        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//
//        Call<ArrayList<ModelFollow>> call = apiService.followingUser(strApiKey, strUsername);
//        call.enqueue(new Callback<ArrayList<ModelFollow>>() {
//            @Override
//            public void onResponse(Call<ArrayList<ModelFollow>> call, Response<ArrayList<ModelFollow>> response) {
//                if (!response.isSuccessful()) {
//                    Log.e("response", response.toString());
//                } else if (response.body() != null) {
//                    modelFollowingMutableLiveData.setValue(response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<ModelFollow>> call, Throwable t) {
//                Log.e("failure", t.toString());
//            }
//        });
//    }

    public LiveData<ArrayList<SearchModel>> getResultList() {
        return searchModelMutableLiveData;
    }

    public LiveData<ModelUser> getUserList() {
        return modelUserMutableLiveData;
    }

//    public LiveData<ArrayList<ModelFollow>> getFollowersUser() {
//        return modelFollowersMutableLiveData;
//    }
//
//    public LiveData<ArrayList<ModelFollow>> getFollowingUser() {
//        return modelFollowingMutableLiveData;
//    }

}
