package com.project.soulsoundapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.activity.PlaylistActivity;
import com.project.soulsoundapp.helper.DatabaseHelper;
import com.project.soulsoundapp.model.Playlist;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {
    private List<Playlist> playlists;
    private Context context;

    public PlaylistAdapter(Context context) {
        this.context = context;
    }

    public void setPlaylist(List<Playlist> playlists) {
        this.playlists = playlists;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist, parent, false);
        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        Playlist playlist = playlists.get(position);
        if (playlist == null) return;
//        holder.ivPlaylistImage.setImageResource(playlist.getImage());
        Picasso.get().load(playlist.getPlaylistThumbnail()).into(holder.ivPlaylistImage);
        holder.tvPlaylistName.setText(playlist.getPlaylistTitle());
        int songCount = playlist.getPlaylistSongs().size();
        holder.tvSongCount.setText(String.format("%d songs", songCount));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlaylistActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("playlist", playlist);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    public class PlaylistViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPlaylistImage;
        private TextView tvPlaylistName, tvSongCount;

        public PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPlaylistImage = itemView.findViewById(R.id.ivPlaylistImage);
            tvPlaylistName = itemView.findViewById(R.id.tvPlaylistName);
            tvSongCount = itemView.findViewById(R.id.tvSongCount);
        }
    }
}
