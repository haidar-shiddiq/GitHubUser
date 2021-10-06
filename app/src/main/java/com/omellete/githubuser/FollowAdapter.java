package com.omellete.githubuser;

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

import java.util.ArrayList;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.FolowersViewHolder> {

    private final ArrayList<ModelFollow> modelFollowArrayList = new ArrayList<>();
    private Context context;

    public FollowAdapter(Context context) {
        this.context = context;
    }

    public void setFollowList(ArrayList<ModelFollow> items) {
        modelFollowArrayList.clear();
        modelFollowArrayList.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public FollowAdapter.FolowersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new FolowersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FollowAdapter.FolowersViewHolder holder, int position) {
        ModelFollow item = modelFollowArrayList.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getAvatarUrl())
                .into(holder.imageUser);

        holder.tvUsername.setText(item.getLogin());
        holder.tvUrl.setText(item.getHtmlUrl());
        holder.cvListUser.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.DETAIL_FOLLOW, item);
            view.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return modelFollowArrayList.size();
    }

    public static class FolowersViewHolder extends RecyclerView.ViewHolder {

        CardView cvListUser;
        TextView tvUsername, tvUrl;
        ImageView imageUser;

        public FolowersViewHolder(View itemView) {
            super(itemView);
            cvListUser = itemView.findViewById(R.id.listUser);
            tvUsername = itemView.findViewById(R.id.unameSearch);
            tvUrl = itemView.findViewById(R.id.urlSearch);
            imageUser = itemView.findViewById(R.id.avaSearch);
        }
    }

}
