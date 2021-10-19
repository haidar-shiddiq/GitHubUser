package com.omellete.githubuser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.omellete.githubuser.R;
import com.omellete.githubuser.databinding.ItemUserBinding;
import com.omellete.githubuser.db.FavoriteList;
import com.omellete.githubuser.model.DetailModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private List<FavoriteList> favoriteLists;
    Context context;

    public FavoriteAdapter(List<FavoriteList> favoriteLists, Context context) {
        this.favoriteLists = favoriteLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        FavoriteList fl=favoriteLists.get(i);
//        Picasso.with(context).load(fl.getImage()).into(viewHolder.img);
        viewHolder.tv.setText(fl.getUsername());
        viewHolder.url.setText(fl.getUrl());
        Glide.with(viewHolder.itemView.getContext())
        .load(fl.getImage())
        .into(viewHolder.img);
    }

    @Override
    public int getItemCount() {
        return favoriteLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView tv,url;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=(ImageView)itemView.findViewById(R.id.avaSearch);
            tv=(TextView)itemView.findViewById(R.id.unameSearch);
            url=(TextView) itemView.findViewById(R.id.urlSearch);
        }
    }
}
