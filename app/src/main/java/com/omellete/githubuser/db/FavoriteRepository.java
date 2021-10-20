package com.omellete.githubuser.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.omellete.githubuser.model.FavoriteModel;

import java.util.List;

public class FavoriteRepository {

    private Dao dao;
    private LiveData<List<FavoriteModel>> allFav;

    public FavoriteRepository(Application application) {
        FavDatabase database = FavDatabase.getInstance(application);
        dao = database.Dao();
        allFav = dao.getAllFav();
    }

    public void insert(FavoriteModel model) {
        new InsertFavAsyncTask(dao).execute(model);
    }

    public void update(FavoriteModel model) {
        new UpdateFavAsyncTask(dao).execute(model);
    }

    public void delete(FavoriteModel model) {
        new DeleteFavAsyncTask(dao).execute(model);
    }

    public void deleteAllFav() {
        new DeleteAllFavAsyncTask(dao).execute();
    }

    public LiveData<List<FavoriteModel>> getAllFav() {
        return allFav;
    }

    private static class InsertFavAsyncTask extends AsyncTask<FavoriteModel, Void, Void> {
        private Dao dao;

        private InsertFavAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(FavoriteModel... model) {
            dao.insert(model[0]);
            return null;
        }
    }

    private static class UpdateFavAsyncTask extends AsyncTask<FavoriteModel, Void, Void> {
        private Dao dao;

        private UpdateFavAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(FavoriteModel... models) {
            dao.update(models[0]);
            return null;
        }
    }

    private static class DeleteFavAsyncTask extends AsyncTask<FavoriteModel, Void, Void> {
        private Dao dao;

        private DeleteFavAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(FavoriteModel... models) {
            dao.delete(models[0]);
            return null;
        }
    }

    private static class DeleteAllFavAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;

        private DeleteAllFavAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllFav();
            return null;
        }
    }
}
