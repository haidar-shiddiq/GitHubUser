package com.omellete.githubuser;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.omellete.githubuser.adapter.FavoriteAdapter;
import com.omellete.githubuser.databinding.ActivityFavoriteBinding;
import com.omellete.githubuser.databinding.ActivityMainBinding;


public class FavoriteActivity extends AppCompatActivity {

    private ActivityFavoriteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.rvFavorite.setLayoutManager(new LinearLayoutManager(this));
        FavoriteAdapter adapter = new FavoriteAdapter();
        binding.rvFavorite.setAdapter(adapter);
        binding.rvFavorite.setHasFixedSize(true);

        ActionBar back = getSupportActionBar();
        if (back != null) {
            back.setDisplayHomeAsUpEnabled(true);
        }
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