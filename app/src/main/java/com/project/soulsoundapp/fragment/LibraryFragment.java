package com.project.soulsoundapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.activity.PlaylistActivity;
import com.project.soulsoundapp.adapter.PlaylistAdapter;
import com.project.soulsoundapp.helper.DatabaseHelper;
import com.project.soulsoundapp.model.Playlist;
import com.project.soulsoundapp.model.Song;
import com.project.soulsoundapp.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LibraryFragment extends Fragment {
    private static final String TAG = "LibraryFragment";
    private View itemFavourite;
//  View of items
    private ImageView item_ivFavouriteImage;
    private TextView item_tvFavouriteName, item_tvFavouriteSongCount;

    DatabaseHelper db;

    private List<String> mFavorite;

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
        addEvents();
    }

    private void addEvents() {
        itemFavourite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PlaylistActivity.class);
                Bundle bundle = new Bundle();
                ArrayList<String> list = new ArrayList<String>(mFavorite);
                bundle.putStringArrayList("mFavorite", list);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void addControls(View view) {
        itemFavourite = view.findViewById(R.id.itemFavourite);
        item_ivFavouriteImage = itemFavourite.findViewById(R.id.ivPlaylistImage);
        item_tvFavouriteName = itemFavourite.findViewById(R.id.tvPlaylistName);
        item_tvFavouriteSongCount = itemFavourite.findViewById(R.id.tvSongCount);
        mFavorite = new ArrayList<>();
        setFavourite();
    }

    public void setFavourite() {
        List<String> mFavorite = db.getFavoriteSongs();

        item_tvFavouriteName.setText("Bài hát yêu thích");
        if(mFavorite.size() > 0) {
            item_tvFavouriteSongCount.setText(Integer.toString(mFavorite.size()));
        } else {
            item_tvFavouriteSongCount.setText("0");
        }
    }
}