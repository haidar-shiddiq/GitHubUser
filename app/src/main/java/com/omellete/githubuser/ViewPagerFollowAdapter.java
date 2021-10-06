package com.omellete.githubuser;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerFollowAdapter extends FragmentStatePagerAdapter {

    ModelFollow modelFollow;

    public ViewPagerFollowAdapter(FragmentManager fragmentManager, ModelFollow modelFollow) {
        super(fragmentManager);
        this.modelFollow = modelFollow;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("modelFollow", modelFollow);
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FragmentFollowers();
                fragment.setArguments(bundle);
                break;
            case 1:
                fragment = new FragmentFollowing();
                fragment.setArguments(bundle);
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Followers";
                break;
            case 1:
                title = "Following";
                break;
        }
        return title;
    }

}