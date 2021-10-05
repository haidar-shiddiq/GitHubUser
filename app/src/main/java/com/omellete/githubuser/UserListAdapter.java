package com.omellete.githubuser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

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
                .into(holder.imageUser);

        holder.tvUsername.setText(item.getLogin());
        holder.tvUrl.setText(item.getHtmlUrl());
//        holder.cvListUser.setOnClickListener(view -> {
//            Intent intent = new Intent(context, DetailActivity.class);
//            intent.putExtra(DetailActivity.DETAIL_USER, modelSearchDataList.get(position));
//            context.startActivity(intent);
//        });

    }

    @Override
    public int getItemCount() {
        return searchModelList.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {

        CardView cvListUser;
        TextView tvUsername, tvUrl;
        ImageView imageUser;

        public SearchViewHolder(View itemView) {
            super(itemView);
            cvListUser = itemView.findViewById(R.id.cvListUser);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvUrl = itemView.findViewById(R.id.tvUrl);
            imageUser = itemView.findViewById(R.id.imageUser);
        }
    }

}
