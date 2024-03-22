package com.project.soulsoundapp.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.model.Album;

import java.util.List;


public class AlbumAdpater extends RecyclerView.Adapter<AlbumAdpater.AlbumViewHolder> {
    private List<Album> albums;
    private Context context;

    public AlbumAdpater(Context context) {
        this.context = context;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        Album album = albums.get(position);
        if(album == null) return;
        holder.ivImgAlbum.setImageResource(album.getThumbnail());
        holder.tvNameAlbum.setText(album.getTitle());
        holder.tvArtistAlbum.setText(album.getDescription());
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivImgAlbum;
        private TextView tvNameAlbum, tvArtistAlbum;
        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImgAlbum = itemView.findViewById(R.id.ivImgAlbum);
            tvNameAlbum = itemView.findViewById(R.id.tvNameAlbum);
            tvArtistAlbum = itemView.findViewById(R.id.tvArtistAlbum);
        }
    }
}
