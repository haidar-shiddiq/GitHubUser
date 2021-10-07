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
import com.omellete.githubuser.model.ModelFollow;
import com.omellete.githubuser.R;

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
        ModelFollow follow = modelFollowArrayList.get(position);

        Glide.with(holder.itemView.getContext())
                .load(follow.getAvatarUrl())
                .into(holder.avatarrr);
        holder.usernameee.setText(follow.getLogin());
        holder.urlll.setText(follow.getHtmlUrl());
        holder.listUser.setOnClickListener(view -> {
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

        CardView listUser;
        TextView usernameee, urlll;
        ImageView avatarrr;

        public FolowersViewHolder(View itemView) {
            super(itemView);
            listUser = itemView.findViewById(R.id.listUser);
            usernameee = itemView.findViewById(R.id.unameSearch);
            urlll = itemView.findViewById(R.id.urlSearch);
            avatarrr = itemView.findViewById(R.id.avaSearch);
        }
    }

}
