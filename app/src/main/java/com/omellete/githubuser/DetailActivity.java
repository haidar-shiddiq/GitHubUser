package com.omellete.githubuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.omellete.githubuser.adapter.ViewPagerAdapter;
import com.omellete.githubuser.adapter.ViewPagerFollowAdapter;
import com.omellete.githubuser.model.ModelFollow;
import com.omellete.githubuser.model.SearchModel;
import com.omellete.githubuser.viewmodel.MyViewModel;

public class DetailActivity extends AppCompatActivity {

    public static final String DETAIL_USER = "DETAIL_USER";
    public static final String DETAIL_FOLLOW = "DETAIL_FOLLOW";
    private final int[] TAB_TITLES = new int[]{
            R.string.Followers,
            R.string.Following
    };

    MyViewModel userViewModel;
    SearchModel modelSearchData;
    ModelFollow modelFollow;
    String usernameKey;
    ImageView avatarr;
    TextView usernamee, locationn, followerr, following, reposs,namee,companyy;
    TabLayout tabsLayout;
    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ActionBar back = getSupportActionBar();
        back.setDisplayHomeAsUpEnabled(true);

        avatarr = findViewById(R.id.avaDetail);
        namee = findViewById(R.id.nameDetail);
        companyy = findViewById(R.id.companyDetail);
        usernamee = findViewById(R.id.unameDetail);
        locationn = findViewById(R.id.locationDetail);
        followerr = findViewById(R.id.followersDetail);
        following = findViewById(R.id.followingDetail);
        reposs = findViewById(R.id.repoDetail);
        tabsLayout = findViewById(R.id.tabsLayout);
        viewPager = findViewById(R.id.viewPager);

        modelFollow = getIntent().getParcelableExtra(DETAIL_FOLLOW);
        modelSearchData = getIntent().getParcelableExtra(DETAIL_USER);
        if (modelSearchData != null) {
            usernameKey = modelSearchData.getLogin();
            ViewPagerAdapter adapter = new ViewPagerAdapter(this, modelSearchData);
            viewPager.setAdapter(adapter);
        }else if (modelFollow !=null){
            usernameKey = modelFollow.getLogin();
            ViewPagerFollowAdapter adapter = new ViewPagerFollowAdapter(this, modelFollow);
            viewPager.setAdapter(adapter);
        }

        new TabLayoutMediator(tabsLayout, viewPager,
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
                    .into(avatarr);

            namee.setText(modelUser.getName());
            companyy.setText(modelUser.getCompany());
            usernamee.setText(modelUser.getLogin());
            locationn.setText(modelUser.getLocation());
            followerr.setText(modelUser.getFollowers());
            following.setText(modelUser.getFollowing());
            reposs.setText(modelUser.getPublicRepos());

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
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
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}