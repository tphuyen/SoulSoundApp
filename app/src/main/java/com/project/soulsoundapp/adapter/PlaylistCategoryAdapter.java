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
import androidx.recyclerview.widget.RecyclerView;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.activity.PlaylistActivity;
import com.project.soulsoundapp.model.Playlist;
import com.project.soulsoundapp.model.Playlist;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaylistCategoryAdapter extends RecyclerView.Adapter<PlaylistCategoryAdapter.PlaylistViewHolder>{
    private List<Playlist> mPlaylists;
    private Context context;

    public PlaylistCategoryAdapter(Context context) {
        this.context = context;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.mPlaylists = playlists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlaylistCategoryAdapter.PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist_category, parent, false);
        return new PlaylistCategoryAdapter.PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistCategoryAdapter.PlaylistViewHolder holder, int position) {
        Playlist playlist = mPlaylists.get(position);
        if(playlist == null) return;
        Picasso.get().load(playlist.getPlaylistThumbnail()).into(holder.ivImgPlaylist);
        holder.tvNamePlaylist.setText(playlist.getPlaylistTitle());
        holder.tvArtistPlaylist.setText(playlist.getPlaylistDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlaylistActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("playlist", playlist);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mPlaylists.size();
    }

    public class PlaylistViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivImgPlaylist;
        private TextView tvNamePlaylist, tvArtistPlaylist;
        public PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImgPlaylist = itemView.findViewById(R.id.ivImgPlaylist);
            tvNamePlaylist = itemView.findViewById(R.id.tvNamePlaylist);
            tvArtistPlaylist = itemView.findViewById(R.id.tvArtistPlaylist);
        }
    }


}
