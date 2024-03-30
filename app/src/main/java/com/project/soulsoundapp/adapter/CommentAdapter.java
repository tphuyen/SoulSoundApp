package com.project.soulsoundapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.model.Comment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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

        Log.d("CommentAdapter", comment.getContent());
        Log.d("CommentAdapter", "Here");

//        Set image
        holder.tvUserName.setText(comment.getUserId());
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
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUserAvatar = itemView.findViewById(R.id.ivUserAvatar);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvContent = itemView.findViewById(R.id.tvContent);
        }
    }
}
