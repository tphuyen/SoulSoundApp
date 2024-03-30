package com.project.soulsoundapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;

import android.view.*;
import android.widget.*;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.activity.PlaylistActivity;
import com.project.soulsoundapp.adapter.PlaylistAdapter;
import com.project.soulsoundapp.model.Playlist;
import com.project.soulsoundapp.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LibraryFragment extends Fragment {
    private List<Playlist> playlists = new ArrayList<>();
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
        addControls(view);
    }

    public void addControls(View view) {
        rvPlaylists = view.findViewById(R.id.rvPlaylists);
        LinearLayoutManager managerCategory = new LinearLayoutManager(getContext());
        managerCategory.setOrientation(RecyclerView.VERTICAL);
        rvPlaylists.setLayoutManager(managerCategory);
        getAllPlaylists();
    }

    public void setPlaylist(List<Playlist> pl) {
        playlists = new ArrayList<>(pl);
        PlaylistAdapter playlistAdapter = new PlaylistAdapter(getContext());
        playlistAdapter.setPlaylist(playlists);
        rvPlaylists.setAdapter(playlistAdapter);
    }


    public List<Playlist> getListPlaylists() {
        List<Playlist> playlists = new ArrayList<Playlist>();
        playlists.add(new Playlist("Playlist 1", R.drawable.img_kpop, 10));
        playlists.add(new Playlist("Playlist 2", R.drawable.img_kpop, 15));
        playlists.add(new Playlist("Playlist 3", R.drawable.img_kpop, 20));
        playlists.add(new Playlist("Playlist 4", R.drawable.img_kpop, 25));
        playlists.add(new Playlist("Playlist 5", R.drawable.img_kpop, 30));
        return playlists;
    }
  
    private void getAllPlaylists() {
        ApiService.apiService.getAllPlaylists().enqueue(new Callback<ApiService.PlaylistResponse>() {
            @Override
            public void onResponse(Call<ApiService.PlaylistResponse> call, Response<ApiService.PlaylistResponse> response) {
                ApiService.PlaylistResponse res = response.body();
                setPlaylist(res.getPlaylists());
                Toast.makeText(getContext(), res.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiService.PlaylistResponse> call, Throwable throwable) {
                Toast.makeText(getContext(), "Call Api Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}