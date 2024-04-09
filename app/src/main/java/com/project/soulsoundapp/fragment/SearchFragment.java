package com.project.soulsoundapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.activity.PlayMusicActivity;
import com.project.soulsoundapp.adapter.CategoryAdapter;
import com.project.soulsoundapp.adapter.SongAdapter;
import com.project.soulsoundapp.helper.DatabaseHelper;
import com.project.soulsoundapp.model.Category;
import com.project.soulsoundapp.model.Song;

import java.util.List;

public class SearchFragment extends Fragment {
    private ImageView ivCloseIcon;
    private RecyclerView rvCategories, rvResultsForSearch;
    private EditText etSearch;
    private TextView tvSearchResult;

    //    private CategoryAdapter categoryAdapter;
    private DatabaseHelper db;
    private static final String TAG = "SearchFragment";

    public SearchFragment() {
//        categoryAdapter = new CategoryAdapter(getContext());
        db = DatabaseHelper.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControls(view);
        addEvents();
    }

    private void addEvents() {
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    actionId == EditorInfo.IME_ACTION_SEARCH ||
                    actionId == EditorInfo.IME_ACTION_GO ||
                    (event != null && event.getAction() == KeyEvent.ACTION_DOWN &&
                            event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

                String key = v.getText().toString().trim();

                if (!key.isEmpty()) {
                    searchSongByTitle(key);
                } else {
                    setCategories(db.getAllCategories());
                }

                return true;
            }
            return false;
        });


        ivCloseIcon.setOnClickListener(v -> {
            etSearch.setText("");
            etSearch.clearFocus();
            setCategories(db.getAllCategories());
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
        });

    }

    public void addControls(View view) {
        rvCategories = view.findViewById(R.id.rvCategories);
        rvResultsForSearch = view.findViewById(R.id.rvResultsForSearch);
        etSearch = view.findViewById(R.id.etSearch);
        ivCloseIcon = view.findViewById(R.id.ivCloseIcon);
        tvSearchResult = view.findViewById(R.id.tvSearchResult);

        List<Category> categories = db.getAllCategories();

        for (Category c : categories) {
            Log.v(TAG, "Num of Cate :: " + c.getCategoryPlaylists().size());
        }

        Log.v(TAG, "" + categories.size());
        setCategories(categories);
    }

    private void setCategories(List<Category> categories) {
        tvSearchResult.setText("Discover Categories");
        rvCategories.setVisibility(View.VISIBLE);
        rvResultsForSearch.setVisibility(View.GONE);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);

        CategoryAdapter categoryAdapter = new CategoryAdapter(getContext());
        rvCategories.setLayoutManager(layoutManager);
        categoryAdapter.setData(categories);

        rvCategories.setAdapter(categoryAdapter);
    }

    private void setResultForSearch(List<Song> songs) {
        rvCategories.setVisibility(View.GONE);
        rvResultsForSearch.setVisibility(View.VISIBLE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvResultsForSearch.setLayoutManager(layoutManager);
        SongAdapter songAdapter = new SongAdapter(getContext());
        songAdapter.setSongs(songs);
        Log.d(TAG, "Songs from result: " + songs.size());
        rvResultsForSearch.setAdapter(songAdapter);
    }

    private void searchSongByTitle(String query) {
        List<Song> songs = db.getSongsByTitle(query);

        tvSearchResult.setText(songs.isEmpty() ? "No Results" : "Search Results");
        setResultForSearch(songs);

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
    }

}
