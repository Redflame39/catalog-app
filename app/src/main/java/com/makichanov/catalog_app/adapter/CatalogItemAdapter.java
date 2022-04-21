package com.makichanov.catalog_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makichanov.catalog_app.R;
import com.makichanov.catalog_app.databinding.ActivityMainBinding;
import com.makichanov.catalog_app.databinding.CatalogItemBinding;
import com.makichanov.catalog_app.model.CatalogItem;

import java.util.List;

public class CatalogItemAdapter extends RecyclerView.Adapter<CatalogItemAdapter.CatalogItemViewHolder> {

    public static class CatalogItemViewHolder extends RecyclerView.ViewHolder {

        private CatalogItemBinding binding;

        public CatalogItemViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = CatalogItemBinding.bind(itemView);
        }

    }

    private List<CatalogItem> catalogItemList;

    public CatalogItemAdapter(List<CatalogItem> catalogItemList) {
        this.catalogItemList = catalogItemList;
    }

    @NonNull
    @Override
    public CatalogItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CatalogItemBinding binding = CatalogItemBinding.inflate(inflater, parent, false);
        View view = binding.getRoot();

        return new CatalogItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogItemViewHolder holder, int position) {
        CatalogItem itemData = catalogItemList.get(position);
        holder.binding.name.setText(itemData.getTitle());
        holder.binding.description.setText(itemData.getDescription());
        if (!itemData.getImage().isEmpty()) {
            Glide.with(holder.binding.itemImage.getContext())
                    .load(itemData.getImage())
                    .circleCrop()
                    .placeholder(R.drawable.ic_user_avatar)
                    .error(R.drawable.ic_user_avatar)
                    .into(holder.binding.itemImage);
        } else {
            holder.binding.itemImage.setImageResource(R.drawable.ic_user_avatar);
        }
    }

    @Override
    public int getItemCount() {
        return catalogItemList.size();
    }

}
