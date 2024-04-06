package com.project.soulsoundapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.model.Playlist;
import com.project.soulsoundapp.model.Playlist;
import com.squareup.picasso.Picasso;

import java.util.List;


public class PlaylistHorizontalAdpater extends RecyclerView.Adapter<PlaylistHorizontalAdpater.PlaylistViewHolder> {
    private List<Playlist> mPlaylists;
    private Context context;

    public PlaylistHorizontalAdpater(Context context) {
        this.context = context;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.mPlaylists = playlists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist_horizontal, parent, false);
        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        Playlist playlist = mPlaylists.get(position);
        if(playlist == null) return;
        Picasso.get().load(playlist.getPlaylistThumbnail()).into(holder.ivImgPlaylist);
        holder.tvNamePlaylist.setText(playlist.getPlaylistTitle());
        holder.tvArtistPlaylist.setText(playlist.getPlaylistDescription());
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
