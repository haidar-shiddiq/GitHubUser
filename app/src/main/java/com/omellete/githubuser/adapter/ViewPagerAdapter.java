package com.omellete.githubuser.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.omellete.githubuser.fragment.FragmentFollowers;
import com.omellete.githubuser.fragment.FragmentFollowing;
import com.omellete.githubuser.model.SearchModel;

public class ViewPagerAdapter extends FragmentStateAdapter {

    SearchModel modelSearchData;

    public ViewPagerAdapter(AppCompatActivity activity, SearchModel modelSearchData) {
        super(activity);
        this.modelSearchData = modelSearchData;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("modelSearchData", modelSearchData);
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
    public int getItemCount() {
        return 2;
    }
}
