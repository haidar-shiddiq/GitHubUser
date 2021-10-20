package com.omellete.githubuser;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omellete.githubuser.databinding.ActivityFavoriteBinding;
import com.omellete.githubuser.adapter.FavoriteAdapter;
import com.omellete.githubuser.model.FavoriteModel;
import com.omellete.githubuser.db.RoomViewModel;

import java.util.List;


public class FavoriteActivity extends AppCompatActivity {
    private RoomViewModel roomViewModel;
    FavoriteAdapter favoriteAdapter;
    ActivityFavoriteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar back = getSupportActionBar();
        if (back != null) {
            back.setDisplayHomeAsUpEnabled(true);
        }

        favoriteAdapter = new FavoriteAdapter();
        binding.rvFavorite.setLayoutManager(new LinearLayoutManager(this));
        binding.rvFavorite.setAdapter(favoriteAdapter);
        binding.rvFavorite.setHasFixedSize(true);

        roomViewModel = new ViewModelProvider(this).get(RoomViewModel.class);

        roomViewModel.getAllFav().observe(this, new Observer<List<FavoriteModel>>() {
            @Override
            public void onChanged(List<FavoriteModel> models) {
                favoriteAdapter.submitList(models);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                roomViewModel.delete(favoriteAdapter.getFavAt(viewHolder.getAdapterPosition()));
                Toast.makeText(FavoriteActivity.this, "User removed from Favorite", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(binding.rvFavorite);

        favoriteAdapter.setOnItemClickListener(new FavoriteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FavoriteModel model) {
                Intent intent = new Intent(FavoriteActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_UID, model.getUid());
                intent.putExtra(DetailActivity.EXTRA_USERNAME, model.getUsername());
                intent.putExtra(DetailActivity.EXTRA_NAME, model.getHtmlurl());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.setting:
                Intent i = new Intent(this, SettingActivity.class);
                startActivity(i);
                break;
            case R.id.delete:
                roomViewModel.deleteAllFav();
                Toast.makeText(FavoriteActivity.this, "Removed All Favorites", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}