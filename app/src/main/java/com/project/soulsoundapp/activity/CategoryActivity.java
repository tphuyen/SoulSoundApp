package com.project.soulsoundapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.adapter.PlaylistCategoryAdapter;
import com.project.soulsoundapp.adapter.SongAdapter;
import com.project.soulsoundapp.helper.DatabaseHelper;
import com.project.soulsoundapp.model.Category;
import com.project.soulsoundapp.model.Playlist;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private static final String TAG = "CategoryActivity";

    private ImageButton ibBack;
    private RecyclerView rvTop100;
    private TextView tvCategoryName;
    private DatabaseHelper db;
    private Category category;
    private List<Playlist> mListPlaylists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        db = DatabaseHelper.getInstance(getApplicationContext());
        category = (Category) getIntent().getSerializableExtra("category");
        mListPlaylists = new ArrayList<>();
        addControls();
        setupBackBtn();
        setAdapter();
    }

    private void setAdapter() {
        mListPlaylists = db.getPlaylistsByIds(category.getCategoryPlaylists());
        Log.v(TAG, "Playlist in cate ::" + category.getCategoryPlaylists().size());
        if(mListPlaylists.size() == 0) {
            Log.v(TAG, "No Playlist");
        } else {
            Log.v(TAG, "Have Playlist : " + mListPlaylists.size());
            for (Playlist i : mListPlaylists) {
                Log.v(TAG, "Playlist :: " + i.getPlaylistTitle());
            }
        }

        PlaylistCategoryAdapter playlistCategoryAdapterTop100 = new PlaylistCategoryAdapter(getApplicationContext());
        playlistCategoryAdapterTop100.setPlaylists(mListPlaylists);
        GridLayoutManager gridLayoutManagerTop100 = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManagerTop100.setOrientation(RecyclerView.VERTICAL);
        rvTop100.setLayoutManager(gridLayoutManagerTop100);
        rvTop100.setAdapter(playlistCategoryAdapterTop100);
    }

    private void addControls() {
        ibBack = findViewById(R.id.ibBack);
        rvTop100 = findViewById(R.id.rvTop100);
        tvCategoryName = findViewById(R.id.tvCategoryName);

        tvCategoryName.setText(category.getCategoryTitle());
    }
    private void setupBackBtn() {
        ibBack.setOnClickListener(v -> onBackPressed());
    }
}