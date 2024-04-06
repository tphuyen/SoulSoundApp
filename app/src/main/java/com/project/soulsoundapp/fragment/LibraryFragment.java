package com.project.soulsoundapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.*;
import android.widget.*;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.activity.PlaylistActivity;
import com.project.soulsoundapp.adapter.PlaylistAdapter;
import com.project.soulsoundapp.helper.DatabaseHelper;
import com.project.soulsoundapp.model.Playlist;
import com.project.soulsoundapp.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LibraryFragment extends Fragment {
    private static final String TAG = "LibraryFragment";
    private RecyclerView rvPlaylists;
    private View itemFavourite;
//  View of items
    private ImageView item_ivFavouriteImage;
    private TextView item_tvFavouriteName, item_tvFavouriteSongCount;

    DatabaseHelper db;
    private List<Playlist> playlists = new ArrayList<>();

    private Playlist mFavorite;

    public LibraryFragment() {
        db = DatabaseHelper.getInstance(getContext());
    }

    public static LibraryFragment newInstance() {
        return new LibraryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_library, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControls(view);
    }

    public void addControls(View view) {
        rvPlaylists = view.findViewById(R.id.rvPlaylists);
        itemFavourite = view.findViewById(R.id.itemFavourite);
        item_ivFavouriteImage = itemFavourite.findViewById(R.id.ivPlaylistImage);
        item_tvFavouriteName = itemFavourite.findViewById(R.id.tvPlaylistName);
        item_tvFavouriteSongCount = itemFavourite.findViewById(R.id.tvSongCount);

//        Set layout RecyclerView
        LinearLayoutManager managerCategory = new LinearLayoutManager(getContext());
        managerCategory.setOrientation(RecyclerView.VERTICAL);
        rvPlaylists.setLayoutManager(managerCategory);

//        Get data from db
        playlists = db.getAllPlaylists();
        setPlaylist(playlists);
    }

    public void setPlaylist(List<Playlist> pl) {
        playlists = new ArrayList<>(pl);
        PlaylistAdapter playlistAdapter = new PlaylistAdapter(getContext());
        playlistAdapter.setPlaylist(playlists);
        rvPlaylists.setAdapter(playlistAdapter);

//        Favourite Playlist
        item_tvFavouriteName.setText("Bài hát yêu thích");
        item_tvFavouriteSongCount.setText("0");
        getFavoriteSong();
    }

    public void getFavoriteSong() {
        String email = "thangvb@gmail.com";
//        ApiService.apiService.getFavoriteApi(email)
//                .enqueue(new Callback<ApiService.ApiResponse<Playlist>>() {
//                    @Override
//                    public void onResponse(Call<ApiService.ApiResponse<Playlist>> call, Response<ApiService.ApiResponse<Playlist>> response) {
//                        mFavorite = response.body().getData();
//                        Log.v(TAG, "" + mFavorite);
//                    }
//
//                    @Override
//                    public void onFailure(Call<ApiService.ApiResponse<Playlist>> call, Throwable throwable) {
//                        Log.v(TAG, "Call failure: " + throwable.getMessage());
//                    }
//                });
    }
}