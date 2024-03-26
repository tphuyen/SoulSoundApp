package com.project.soulsoundapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;

import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.adapter.PlaylistAdapter;
import com.project.soulsoundapp.model.Playlist;

import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment {
    private RecyclerView rvPlaylists;

    public LibraryFragment() {
        // Required empty public constructor
    }

    public static LibraryFragment newInstance() {
        return new LibraryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_library, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControl(view);
    }

    public void addControl(View view) {
        rvPlaylists = view.findViewById(R.id.rvPlaylists);
        LinearLayoutManager managerCategory = new LinearLayoutManager(getContext());
        managerCategory.setOrientation(RecyclerView.VERTICAL);
        rvPlaylists.setLayoutManager(managerCategory);

        PlaylistAdapter playlistAdapter = new PlaylistAdapter(getContext());
        playlistAdapter.setPlaylist(getListPlaylists());

        rvPlaylists.setAdapter(playlistAdapter);
    }

    public List<Playlist> getListPlaylists() {
        List<Playlist> playlists = new ArrayList<Playlist>();
        playlists.add(new Playlist("Playlist 1", R.drawable.kpop, 10));
        playlists.add(new Playlist("Playlist 2", R.drawable.kpop, 15));
        playlists.add(new Playlist("Playlist 3", R.drawable.kpop, 20));
        playlists.add(new Playlist("Playlist 4", R.drawable.kpop, 25));
        playlists.add(new Playlist("Playlist 5", R.drawable.kpop, 30));
        return playlists;
    }
}