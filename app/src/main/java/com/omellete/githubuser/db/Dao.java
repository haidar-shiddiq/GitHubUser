package com.omellete.githubuser.db;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.omellete.githubuser.model.FavoriteModel;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteModel model);

    @Update
    void update(FavoriteModel model);

    @Delete
    void delete(FavoriteModel model);

    @Query("DELETE FROM favorite_table")
    void deleteAllFav();

    @Query("SELECT * FROM favorite_table ORDER BY username ASC")
    LiveData<List<FavoriteModel>> getAllFav();
}
