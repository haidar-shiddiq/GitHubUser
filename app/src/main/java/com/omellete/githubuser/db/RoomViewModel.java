package com.omellete.githubuser.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.omellete.githubuser.model.FavoriteModel;

import java.util.List;

public class RoomViewModel extends AndroidViewModel {

    private FavoriteRepository repository;

    private LiveData<List<FavoriteModel>> allFav;

    public RoomViewModel(@NonNull Application application) {
        super(application);
        repository = new FavoriteRepository(application);
        allFav = repository.getAllFav();
    }

    public void insert(FavoriteModel model) {
        repository.insert(model);
    }

    public void update(FavoriteModel model) {
        repository.update(model);
    }

    public void delete(FavoriteModel model) {
        repository.delete(model);
    }

    public void deleteAllFav() {
        repository.deleteAllFav();
    }

    public LiveData<List<FavoriteModel>> getAllFav() {
        return allFav;
    }
}
