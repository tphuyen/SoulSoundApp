package com.project.soulsoundapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.activity.CategoryActivity;
import com.project.soulsoundapp.activity.PlaylistActivity;
import com.project.soulsoundapp.model.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categories;
    private Context context;

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
    public void setData(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        if(category == null) {
            return;
        }
        Picasso.get().load(category.getCategoryThumbnail()).into(holder.ivCategoryImage);
        holder.tvCategoryName.setText(category.getCategoryTitle());
        holder.clItemCategory.setBackgroundColor(Color.parseColor(category.getCategoryBackColor()));

        holder.clItemCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CategoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("category", category);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(categories != null) return categories.size();
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCategoryName;
        private ImageView ivCategoryImage;
        private ConstraintLayout clItemCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            ivCategoryImage = itemView.findViewById(R.id.ivCategoryImage);
            clItemCategory = itemView.findViewById(R.id.clItemCategory);
        }
    }

}
