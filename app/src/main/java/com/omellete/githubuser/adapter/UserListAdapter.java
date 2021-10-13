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
import com.omellete.githubuser.model.SearchModel;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.SearchViewHolder> {

    private final ArrayList<SearchModel> searchModelList = new ArrayList<>();
    private final Context context;

    public UserListAdapter(Context context) {
        this.context = context;
    }

    public void setSearchUserList(ArrayList<SearchModel> items) {
        searchModelList.clear();
        searchModelList.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemUserBinding binding = ItemUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SearchViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        SearchModel item = searchModelList.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getAvatarUrl())
                .into(holder.binding.avaSearch);

        holder.binding.unameSearch.setText(item.getLogin());
        holder.binding.urlSearch.setText(item.getHtmlUrl());
        holder.binding.listUser.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(DetailActivity.DETAIL_USER, searchModelList.get(position));
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return searchModelList.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {

        ItemUserBinding binding;

        SearchViewHolder(ItemUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

}
