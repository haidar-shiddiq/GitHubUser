package com.omellete.githubuser.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.omellete.githubuser.fragment.FragmentFollowers;
import com.omellete.githubuser.fragment.FragmentFollowing;
import com.omellete.githubuser.model.ModelFollow;

public class ViewPagerFollowAdapter extends FragmentStateAdapter {

    ModelFollow modelFollow;

    public ViewPagerFollowAdapter(AppCompatActivity activity, ModelFollow modelFollow) {
        super(activity);
        this.modelFollow = modelFollow;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
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
    public int getItemCount() {
        return 2;
    }
}