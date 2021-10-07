package com.omellete.githubuser.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omellete.githubuser.R;
import com.omellete.githubuser.adapter.FollowAdapter;
import com.omellete.githubuser.model.ModelFollow;
import com.omellete.githubuser.model.SearchModel;
import com.omellete.githubuser.viewmodel.MyViewModel;


public class FragmentFollowers extends Fragment {

    SearchModel modelSearchData;
    MyViewModel followersViewModel;
    FollowAdapter followAdapter;
    RecyclerView rv_follower;
    ModelFollow modelFollow;
    ProgressDialog loading;
    String usernameKey;

    public FragmentFollowers() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followers, container, false);

        loading = new ProgressDialog(getContext());
        loading.setCancelable(false);
        loading.setMessage("Wait for a moment");

        rv_follower = view.findViewById(R.id.rv_follower);

        assert this.getArguments() != null;
        modelFollow = this.getArguments().getParcelable("modelFollow");
        modelSearchData = this.getArguments().getParcelable("modelSearchData");
        if (modelSearchData != null) {
            usernameKey = modelSearchData.getLogin();
        } else if (modelFollow != null) {
            usernameKey = modelFollow.getLogin();
        }

        followAdapter = new FollowAdapter(getContext());
        rv_follower.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_follower.setAdapter(followAdapter);
        rv_follower.setHasFixedSize(true);

        followersViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MyViewModel.class);
        followersViewModel.setFollowers(usernameKey);
        loading.show();
        followersViewModel.getFollowersUser().observe(getViewLifecycleOwner(), modelFollowers -> {
            if (modelFollowers.size() != 0) {
                followAdapter.setFollowList(modelFollowers);
            } else {
                Toast.makeText(getContext(), "Followers Not Found", Toast.LENGTH_SHORT).show();
            }
            loading.dismiss();
        });

        return view;
    }

}