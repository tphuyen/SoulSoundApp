package com.project.soulsoundapp.adapter;

import android.content.Context;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.model.Artist;
import com.project.soulsoundapp.model.Song;

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder>{
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
        if(song == null) {
            return;
        }
        holder.ivSongImage.setImageResource(song.getImage());
        holder.tvSongTitle.setText(song.getName());
        holder.tvSongArtistName.setText(getSongArtistName(song.getArtists()));
    }

    @Override
    public int getItemCount() {
        if(songs == null) {
            return 0;
        }
        return songs.size();
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

    private String getSongArtistName(List<Artist> artists) {
        if(artists == null) return "Unknown artist";
        String artistsName = "";
        for (Artist artist : artists) {
            artistsName += artist.getName() + " ";
        }
        return artistsName;
    }
}
