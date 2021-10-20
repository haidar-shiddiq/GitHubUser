package com.omellete.githubuser;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayoutMediator;
import com.omellete.githubuser.adapter.ViewPagerAdapter;
import com.omellete.githubuser.adapter.ViewPagerFavAdapter;
import com.omellete.githubuser.adapter.ViewPagerFollowAdapter;
import com.omellete.githubuser.databinding.ActivityDetailBinding;
import com.omellete.githubuser.model.FavoriteModel;
import com.omellete.githubuser.db.RoomViewModel;
import com.omellete.githubuser.model.ModelFollow;
import com.omellete.githubuser.model.SearchModel;
import com.omellete.githubuser.viewmodel.MyViewModel;

public class DetailActivity extends AppCompatActivity {

    public static final String DETAIL_USER = "DETAIL_USER";
    public static final String DETAIL_FOLLOW = "DETAIL_FOLLOW";
    public static final String DETAIL_FAV = "DETAIL_FAV";
    public static String usernameKey = "";
    public boolean flag;
    private final int[] TAB_TITLES = new int[]{
            R.string.Followers,
            R.string.Following
    };

    MyViewModel userViewModel;
    SearchModel modelSearchData;
    ModelFollow modelFollow;
    FavoriteModel modelFav, favModel;
    String favUnamee, favNamee;
    private ActivityDetailBinding binding;

    public static final String EXTRA_UID = "EXTRA_UID";
    public static final String EXTRA_USERNAME = "EXTRA_USERNAME";
    public static final String EXTRA_NAME = "EXTRA_NAME";

    public DetailActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar back = getSupportActionBar();
        if (back != null) {
            back.setDisplayHomeAsUpEnabled(true);
        }

        modelFav = getIntent().getParcelableExtra(DETAIL_FAV);

        modelFollow = getIntent().getParcelableExtra(DETAIL_FOLLOW);
        modelSearchData = getIntent().getParcelableExtra(DETAIL_USER);
        if (modelSearchData != null) {
            usernameKey = modelSearchData.getLogin();
            ViewPagerAdapter adapter = new ViewPagerAdapter(this, modelSearchData);
            binding.viewPager.setAdapter(adapter);
        } else if (modelFollow != null) {
            usernameKey = modelFollow.getLogin();
            ViewPagerFollowAdapter adapter = new ViewPagerFollowAdapter(this, modelFollow);
            binding.viewPager.setAdapter(adapter);
        } else if (modelFav != null) {
            usernameKey = modelFav.getUsername();
            ViewPagerFavAdapter adapter = new ViewPagerFavAdapter(this, modelFav);
            binding.viewPager.setAdapter(adapter);
        }

        new TabLayoutMediator(binding.tabsLayout, binding.viewPager,
                (tab, position) -> tab.setText(getResources().getString(TAB_TITLES[position]))
        ).attach();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
        }


        userViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MyViewModel.class);
        userViewModel.setUserDetail(usernameKey);
        userViewModel.getUserList().observe(this, modelUser -> {
            Glide.with(getApplicationContext())
                    .load(modelUser.getAvatarUrl())
                    .into(binding.avaDetail);

            binding.nameDetail.setText(modelUser.getName());
            binding.companyDetail.setText(modelUser.getCompany());
            binding.unameDetail.setText(modelUser.getLogin());
            binding.locationDetail.setText(modelUser.getLocation());
            binding.followersDetail.setText(modelUser.getFollowers());
            binding.followingDetail.setText(modelUser.getFollowing());
            binding.repoDetail.setText(modelUser.getPublicRepos());
            favUnamee = modelUser.getLogin();
            favNamee = modelUser.getName();

        });


        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_UID)) {
            binding.unameDetail.setText(intent.getStringExtra(EXTRA_USERNAME));
            binding.nameDetail.setText(intent.getStringExtra(EXTRA_NAME));
        }

        flag = true;
        binding.fabFavorite.setOnClickListener(v -> {
            RoomViewModel roomViewModel = new ViewModelProvider(DetailActivity.this).get(RoomViewModel.class);
            String favUname = favUnamee;
            String favName = favNamee;
            FavoriteModel model = new FavoriteModel(favUname, favName);
            if (flag) {
                binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.delete));
                flag = false;
                roomViewModel.insert(model);
                Toast.makeText(getApplicationContext(), "User added to Favorite", Toast.LENGTH_SHORT).show();
            } else {
                binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.favorite));
                flag = true;
                roomViewModel.deleteAllFav();
                Toast.makeText(getApplicationContext(), "All user removed from Favorite", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shareButton:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Hey! Im viewing " + usernameKey + " on Github User List!";
                String shareSub = "Hey! Im using Github User List!";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                break;
            case R.id.setting:
                Intent i = new Intent(this, SettingActivity.class);
                startActivity(i);
                break;
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}