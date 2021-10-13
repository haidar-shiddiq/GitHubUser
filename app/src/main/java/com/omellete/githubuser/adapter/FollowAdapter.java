package com.omellete.githubuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.omellete.githubuser.DetailActivity;
import com.omellete.githubuser.databinding.ItemUserBinding;
import com.omellete.githubuser.model.ModelFollow;

import java.util.ArrayList;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.FolowersViewHolder> {

    private final ArrayList<ModelFollow> modelFollowArrayList = new ArrayList<>();
    private final Context context;

    public FollowAdapter(Context context) {
        this.context = context;
    }

    public void setFollowList(ArrayList<ModelFollow> items) {
        modelFollowArrayList.clear();
        modelFollowArrayList.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FollowAdapter.FolowersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemUserBinding binding = ItemUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FollowAdapter.FolowersViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(FollowAdapter.FolowersViewHolder holder, int position) {
        ModelFollow follow = modelFollowArrayList.get(position);

        Glide.with(holder.itemView.getContext())
                .load(follow.getAvatarUrl())
                .into(holder.binding.avaSearch);
        holder.binding.unameSearch.setText(follow.getLogin());
        holder.binding.urlSearch.setText(follow.getHtmlUrl());
        holder.binding.listUser.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.DETAIL_FOLLOW, follow);
            view.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return modelFollowArrayList.size();
    }

    public static class FolowersViewHolder extends RecyclerView.ViewHolder {

        ItemUserBinding binding;

        FolowersViewHolder(ItemUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
