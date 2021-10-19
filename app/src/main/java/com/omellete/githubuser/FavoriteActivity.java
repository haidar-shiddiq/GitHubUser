package com.omellete.githubuser;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.omellete.githubuser.adapter.FavoriteAdapter;
import com.omellete.githubuser.databinding.ActivityFavoriteBinding;
import com.omellete.githubuser.databinding.ActivityMainBinding;
import com.omellete.githubuser.db.FavoriteList;

import java.util.List;


public class FavoriteActivity extends AppCompatActivity {
    private RecyclerView rv;
    private FavoriteAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_user);
        rv=(RecyclerView)findViewById(R.id.rvFavorite);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        getFavData();
    }

    private void getFavData() {
        List<FavoriteList> favoriteLists=DetailActivity.favoriteDatabase.favoriteDao().getFavoriteData();

        adapter=new FavoriteAdapter(favoriteLists,getApplicationContext());
        rv.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}