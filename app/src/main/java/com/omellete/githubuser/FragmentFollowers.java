package com.omellete.githubuser;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class FragmentFollowers extends Fragment {

    SearchModel modelSearchData;
    MyViewModel followersViewModel;
    FollowAdapter followAdapter;
    RecyclerView rvListFollowers;
    ConstraintLayout layoutEmpty;
    ProgressDialog progressDialog;
    String strUsername;

    public FragmentFollowers() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followers, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Mohon Tunggu...");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Sedang menampilkan data");

        rvListFollowers = view.findViewById(R.id.rvListFollowers);
//        layoutEmpty = view.findViewById(R.id.layoutEmpty);

        modelSearchData = this.getArguments().getParcelable("modelSearchData");
        if (modelSearchData != null) {
            strUsername = modelSearchData.getLogin();
        }

        followAdapter = new FollowAdapter(getContext());
        rvListFollowers.setLayoutManager(new LinearLayoutManager(getContext()));
        rvListFollowers.setAdapter(followAdapter);
        rvListFollowers.setHasFixedSize(true);

        //method set viewmodel
        followersViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MyViewModel.class);
        followersViewModel.setFollowersUser(strUsername);
        progressDialog.show();
        followersViewModel.getFollowersUser().observe(getViewLifecycleOwner(), modelFollowers -> {
            if (modelFollowers.size() != 0) {
//                layoutEmpty.setVisibility(View.GONE);
                followAdapter.setFollowList(modelFollowers);
            } else {
//                layoutEmpty.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Followers Tidak Ditemukan!", Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        });

        return view;
    }

}