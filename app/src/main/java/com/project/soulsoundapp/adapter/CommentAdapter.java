package com.project.soulsoundapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.project.soulsoundapp.R;
import com.project.soulsoundapp.model.Comment;
import com.squareup.picasso.Picasso;

import java.util.*;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<Comment> comments;
    private Context context;

    public CommentAdapter(Context context) {
        this.context = context;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        if(comment == null) {
            return;
        }

//        Set image
        String initial = comment.getEmail().substring(0, 1).toUpperCase();
        Picasso.get().load("https://music-player.sgp1.digitaloceanspaces.com/song_thumbnail/Z7U00WDEx96.jpg").into(holder.siUserAvatar);

//        Drawable drawable = Drawable.

//        holder.siUserAvatar.setIm()

        holder.tvUserName.setText(comment.getEmail());
        holder.tvContent.setText(comment.getContent());
    }

    @Override
    public int getItemCount() {
        if(comments != null) return comments.size();
        else return 0;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivUserAvatar;
        private TextView tvUserName;
        private TextView tvContent;
        private ShapeableImageView siUserAvatar;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
//            ivUserAvatar = itemView.findViewById(R.id.ivUserAvatar);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvContent = itemView.findViewById(R.id.tvContent);
            siUserAvatar = itemView.findViewById(R.id.siUserAvatar);
        }
    }
}
