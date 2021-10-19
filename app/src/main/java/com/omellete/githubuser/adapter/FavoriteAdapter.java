package com.omellete.githubuser.adapter;

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
import com.omellete.githubuser.model.DetailModel;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private ArrayList<DetailModel> detailModelArrayList = new ArrayList<>();

    public void setFavoriteUserList(ArrayList<DetailModel> data) {
        this.detailModelArrayList.clear();
        this.detailModelArrayList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemUserBinding binding = ItemUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FavoriteAdapter.FavoriteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {
        DetailModel item = detailModelArrayList.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getAvatarUrl())
                .into(holder.binding.avaSearch);

        holder.binding.unameSearch.setText(item.getLogin());
        holder.binding.urlSearch.setText("github.com/"+item.getLogin());
        /*holder.cvListUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.DETAIL_USER, modelFollowersArrayList.get(position));
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return detailModelArrayList.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        ItemUserBinding binding;

        public FavoriteViewHolder(ItemUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
