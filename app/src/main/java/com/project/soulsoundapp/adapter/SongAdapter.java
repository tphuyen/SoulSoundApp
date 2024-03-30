package com.project.soulsoundapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.activity.PlayMusicActivity;
import com.project.soulsoundapp.model.Song;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    private List<Song> songs = new ArrayList<>();
    private Context context;

    public SongAdapter(Context context) {
        this.context = context;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songs.get(position);

        if (song == null) return;
        holder.tvSongTitle.setText(song.getTitle());
        holder.tvSongArtistName.setText(song.getArtist());
        Picasso.get().load(song.getThumbnailUrl()).into(holder.ivSongImage);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, PlayMusicActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("song", song);
            intent.putExtras(bundle);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return songs == null ? 0 : songs.size();
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivSongImage;
        private TextView tvSongTitle;
        private TextView tvSongArtistName;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSongImage = (ImageView) itemView.findViewById(R.id.ivSongImage);
            tvSongTitle = (TextView) itemView.findViewById(R.id.tvSongTitle);
            tvSongArtistName = (TextView) itemView.findViewById(R.id.tvSongArtistName);
        }
    }
}
