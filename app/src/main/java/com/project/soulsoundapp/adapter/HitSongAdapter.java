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
public class HitSongAdapter extends RecyclerView.Adapter<HitSongAdapter.HitSongViewHolder>{
    private List<Song> songs = new ArrayList<>();
    private Context context;

    public HitSongAdapter(Context context) {
        this.context = context;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HitSongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new HitSongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HitSongViewHolder holder, int position) {
        Song row = songs.get(position);

//        holder.bindRow(row);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class HitSongViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llSongRow;

        public HitSongViewHolder(@NonNull View itemView) {
            super(itemView);
            llSongRow = itemView.findViewById(R.id.llSongRow);
        }

        public void bindRow(List<Song> row) {
            llSongRow.removeAllViews();
            for (Song song : row) {
                View songView = LayoutInflater.from(context).inflate(R.layout.item_song, llSongRow, false);
                TextView tvSongTitle = songView.findViewById(R.id.tvSongTitle);
                TextView tvSongArtistName = songView.findViewById(R.id.tvSongArtistName);
                ImageView ivSongImage = songView.findViewById(R.id.ivSongImage);

                tvSongTitle.setText(song.getTitle());
                tvSongArtistName.setText(song.getArtist());
                Picasso.get().load(song.getThumbnailUrl()).into(ivSongImage);

                songView.setOnClickListener(v -> {
                    Intent intent = new Intent(context, PlayMusicActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("song", song);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                });

                llSongRow.addView(songView);
            }
        }
    }
}
