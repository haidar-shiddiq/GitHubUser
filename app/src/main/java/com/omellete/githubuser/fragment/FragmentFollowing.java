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

public class FragmentFollowing extends Fragment {

    SearchModel modelSearchData;
    ModelFollow modelFollow;
    MyViewModel followingViewModel;
    FollowAdapter followingAdapter;
    RecyclerView rvFollowing;
    ProgressDialog loading;
    String usernameKey;

    public FragmentFollowing() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following, container, false);

        loading = new ProgressDialog(getContext());
        loading.setCancelable(false);
        loading.setMessage("Wait for a moment");

        rvFollowing = view.findViewById(R.id.rv_following);

        assert this.getArguments() != null;
        modelFollow = this.getArguments().getParcelable("modelFollow");
        modelSearchData = this.getArguments().getParcelable("modelSearchData");
        if (modelSearchData != null) {
            usernameKey = modelSearchData.getLogin();
        } else if (modelFollow != null) {
            usernameKey = modelFollow.getLogin();
        }

        followingAdapter = new FollowAdapter(getContext());
        rvFollowing.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFollowing.setAdapter(followingAdapter);
        rvFollowing.setHasFixedSize(true);

        followingViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MyViewModel.class);
        followingViewModel.setFollowing(usernameKey);
        loading.show();
        followingViewModel.getFollowingUser().observe(getViewLifecycleOwner(), modelFollowing -> {
            if (modelFollowing.size() != 0) {
                followingAdapter.setFollowList(modelFollowing);
            } else {
                Toast.makeText(getContext(), "Following Not Found", Toast.LENGTH_SHORT).show();
            }
            loading.dismiss();
        });

        return view;
    }

}