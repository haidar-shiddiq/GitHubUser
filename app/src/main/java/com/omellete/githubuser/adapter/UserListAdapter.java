package com.omellete.githubuser.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.omellete.githubuser.DetailActivity;
import com.omellete.githubuser.R;
import com.omellete.githubuser.model.SearchModel;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.SearchViewHolder> {

    private ArrayList<SearchModel> searchModelList = new ArrayList<>();
    private Context context;

    public UserListAdapter(Context context) {
        this.context = context;
    }

    public void setSearchUserList(ArrayList<SearchModel> items) {
        searchModelList.clear();
        searchModelList.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        SearchModel item = searchModelList.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getAvatarUrl())
                .into(holder.avaSearch);

        holder.unameSearch.setText(item.getLogin());
        holder.urlSearch.setText(item.getHtmlUrl());
        holder.listUser.setOnClickListener(view -> {
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

        CardView listUser;
        TextView unameSearch, urlSearch;
        ImageView avaSearch;

        public SearchViewHolder(View itemView) {
            super(itemView);
            listUser = itemView.findViewById(R.id.listUser);
            unameSearch = itemView.findViewById(R.id.unameSearch);
            urlSearch = itemView.findViewById(R.id.urlSearch);
            avaSearch = itemView.findViewById(R.id.avaSearch);
        }
    }

}
