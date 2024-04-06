package com.project.soulsoundapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.adapter.PlaylistHorizontalAdpater;
import com.project.soulsoundapp.helper.DatabaseHelper;
import com.project.soulsoundapp.model.Category;
import com.project.soulsoundapp.model.Playlist;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView rvTop100;
    private DatabaseHelper db;
    private Category category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        category = (Category) getIntent().getSerializableExtra("category");
        addControls();
    }

    private void addControls() {
        db = DatabaseHelper.getInstance(getApplicationContext());
        rvTop100 = findViewById(R.id.rvTop100);
        PlaylistHorizontalAdpater playlistHorizontalAdapterTop100 = new PlaylistHorizontalAdpater(getApplicationContext());
        playlistHorizontalAdapterTop100.setPlaylists(getPlaylists());
        LinearLayoutManager managerTop100 = new LinearLayoutManager(getApplicationContext());
        managerTop100.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvTop100.setLayoutManager(managerTop100);
        rvTop100.setAdapter(playlistHorizontalAdapterTop100);

    }
    private List<Playlist> getPlaylists() {
        return db.getAllPlaylists();
    }
}