package com.omellete.githubuser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    public static final String DETAIL_USER = "DETAIL_USER";
    public static final String DETAIL_FOLLOW = "DETAIL_FOLLOW";
//    private FavoriteHelper favoriteHelper;
    ArrayList<ModelUser> modelUserArrayList = new ArrayList<>();
    MyViewModel userViewModel;
    SearchModel modelSearchData;
    ModelFollow modelFollow;
    String strUsername;
    ImageView imageUser;
    TextView tvUsername, tvBio, tvFollowers, tvFollowing, tvRepository;
    TabLayout tabsLayout;
    Toolbar toolbar;
    ViewPager viewPager;
//    MaterialFavoriteButton imageFavorite;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageUser = findViewById(R.id.avaDetail);
        tvUsername = findViewById(R.id.unameDetail);
        tvBio = findViewById(R.id.locationDetail);
        tvFollowers = findViewById(R.id.followersDetail);
        tvFollowing = findViewById(R.id.followingDetail);
        tvRepository = findViewById(R.id.repoDetail);
        tabsLayout = findViewById(R.id.tabsLayout);
        viewPager = findViewById(R.id.viewPager);

        modelFollow = getIntent().getParcelableExtra(DETAIL_FOLLOW);
        modelSearchData = getIntent().getParcelableExtra(DETAIL_USER);
        if (modelSearchData != null) {
            strUsername = modelSearchData.getLogin();
            viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), modelSearchData));
        }else if (modelFollow !=null){
            strUsername = modelFollow.getLogin();
            viewPager.setAdapter(new ViewPagerFollowAdapter(getSupportFragmentManager(), modelFollow));
        }


        viewPager.setOffscreenPageLimit(2);
        tabsLayout.setupWithViewPager(viewPager);

        //method set viewmodel
        userViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MyViewModel.class);
        userViewModel.setUserDetail(strUsername);
        userViewModel.getUserList().observe(this, modelUser -> {
            Glide.with(getApplicationContext())
                    .load(modelUser.getAvatarUrl())
                    .into(imageUser);

//            collapsingToolbarLayout.setTitle(modelUser.getName());
            tvUsername.setText(modelUser.getLogin() + " \u2022 " + modelUser.getLocation());
            tvBio.setText(modelUser.getBio());
            tvFollowers.setText(modelUser.getFollowers());
            tvFollowing.setText(modelUser.getFollowing());
            tvRepository.setText(modelUser.getPublicRepos());

            //method set favorite or unfavorite
//            if (FavoriteExist(strUsername)) {
//                imageFavorite.setFavorite(true);
//                imageFavorite.setOnFavoriteChangeListener(
//                        (buttonView, favorite) -> {
//                            if (favorite) {
//                                modelUserArrayList = favoriteHelper.getAllFavorites();
//                                favoriteHelper.favoriteInsert(modelUser);
//                                Toast.makeText(getApplicationContext(), "Ditambahkan Favorite", Toast.LENGTH_SHORT).show();
//                            } else {
//                                modelUserArrayList = favoriteHelper.getAllFavorites();
//                                favoriteHelper.favoriteDelete(strUsername);
//                                Toast.makeText(getApplicationContext(), "Dihapus Favorite", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//            } else {
//                imageFavorite.setOnFavoriteChangeListener(
//                        (buttonView, favorite) -> {
//                            if (favorite) {
//                                modelUserArrayList = favoriteHelper.getAllFavorites();
//                                favoriteHelper.favoriteInsert(modelUser);
//                                Toast.makeText(getApplicationContext(), "Ditambahkan Favorite", Toast.LENGTH_SHORT).show();
//                            } else {
//                                modelUserArrayList = favoriteHelper.getAllFavorites();
//                                favoriteHelper.favoriteDelete(strUsername);
//                                Toast.makeText(getApplicationContext(), "Dihapus Favorite", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//            }
        });
    }

    //method check data if exist
//    public boolean FavoriteExist(String item) {
//        String pilih = DatabaseContract.FavoriteColoumn.TITLE + " =?";
//        String[] pilihArg = {item};
//        String limit = "1";
//        favoriteHelper = new FavoriteHelper(this);
//        favoriteHelper.open();
//        DatabaseHelper dataBaseHelper = new DatabaseHelper(DetailActivity.this);
//        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
//        Cursor cursor = database.query(TABLE_NAME, null, pilih, pilihArg, null, null, null, limit);
//        boolean exists;
//        exists = (cursor.getCount() > 0);
//        cursor.close();
//
//        return exists;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}