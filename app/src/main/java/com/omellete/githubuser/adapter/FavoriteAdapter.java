package com.omellete.githubuser.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.omellete.githubuser.DetailActivity;
import com.omellete.githubuser.databinding.ItemFavBinding;
import com.omellete.githubuser.model.FavoriteModel;

public class FavoriteAdapter extends ListAdapter<FavoriteModel, FavoriteAdapter.ViewHolder> {

    private OnItemClickListener listener;

    public FavoriteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<FavoriteModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<FavoriteModel>() {
        @Override
        public boolean areItemsTheSame(FavoriteModel oldItem, FavoriteModel newItem) {
            return oldItem.getUid() == newItem.getUid();
        }

        @Override
        public boolean areContentsTheSame(FavoriteModel oldItem, FavoriteModel newItem) {
            return oldItem.getUsername().equals(newItem.getUsername()) &&
                    oldItem.getHtmlurl().equals(newItem.getHtmlurl());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFavBinding binding = ItemFavBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FavoriteAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavoriteModel model = getFavAt(position);
        holder.binding.unameSearch.setText(model.getUsername());
        holder.binding.urlSearch.setText(model.getHtmlurl());
        holder.binding.listUser.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.DETAIL_FAV, model);
            view.getContext().startActivity(intent);
        });

    }

    public FavoriteModel getFavAt(int position) {
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemFavBinding binding;

        ViewHolder(ItemFavBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public interface OnItemClickListener {
        void onItemClick(FavoriteModel model);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
