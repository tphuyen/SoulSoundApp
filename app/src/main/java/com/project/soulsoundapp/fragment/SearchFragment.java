package com.project.soulsoundapp.fragment;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.adapter.CategoryAdapter;
import com.project.soulsoundapp.adapter.SongAdapter;
import com.project.soulsoundapp.helper.DatabaseHelper;
import com.project.soulsoundapp.model.Category;
import com.project.soulsoundapp.model.Song;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private ImageView ivCloseIcon;
    private RecyclerView rvCategories, rvResultsForSearch;
    private EditText etSearch;

    private CategoryAdapter categoryAdapter;
    private SongAdapter songAdapter;

    private DatabaseHelper db;
    private static final String TAG = "SearchFragment";

    public SearchFragment() {
        categoryAdapter = new CategoryAdapter(getContext());
        songAdapter = new SongAdapter(getContext());
        db = DatabaseHelper.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControls(view);
        addEvents(view);
    }

    private void addEvents(View view) {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String key = v.getText().toString();
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    if(key.trim().length() > 0) {
                        searchSongByTitle(v.getText().toString().trim());
                    } else {
                        List<Category> categories = new ArrayList<>();
                        categories.add(new Category(R.drawable.img_kpop, "KPOP", R.color.light_blue));
                        categories.add(new Category(R.drawable.img_kpop, "KPOP", R.color.light_blue));
                        categories.add(new Category(R.drawable.img_kpop, "KPOP", R.color.light_blue));
                        categories.add(new Category(R.drawable.img_kpop, "KPOP", R.color.light_blue));
                        categories.add(new Category(R.drawable.img_kpop, "KPOP", R.color.light_blue));
                        categories.add(new Category(R.drawable.img_kpop, "KPOP", R.color.light_blue));
                        setCategories(categories);
                    }
                    return true;
                }
                return false;
            }
        });
      
        ivCloseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setText("");
                etSearch.clearFocus();
            }
        });
    }

    public void addControls(View view) {
        rvCategories =view.findViewById(R.id.rvCategories);
        rvResultsForSearch = view.findViewById(R.id.rvResultsForSearch);
        etSearch = view.findViewById(R.id.etSearch);
        ivCloseIcon = view.findViewById(R.id.ivCloseIcon);

        List<Category> categories = new ArrayList<>();
        categories.add(new Category(R.drawable.img_kpop, "KPOP", R.color.light_blue));
        categories.add(new Category(R.drawable.img_kpop, "KPOP", R.color.light_blue));
        categories.add(new Category(R.drawable.img_kpop, "KPOP", R.color.light_blue));
        categories.add(new Category(R.drawable.img_kpop, "KPOP", R.color.light_blue));
        categories.add(new Category(R.drawable.img_kpop, "KPOP", R.color.light_blue));
        categories.add(new Category(R.drawable.img_kpop, "KPOP", R.color.light_blue));
        setCategories(categories);
    }

    private void setCategories(List<Category> categories) {
        rvCategories.setVisibility(View.VISIBLE);
        rvResultsForSearch.setVisibility(View.GONE);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);

        rvCategories.setLayoutManager(layoutManager);
        categoryAdapter.setData(categories);

        rvCategories.setAdapter(categoryAdapter);
    }


    public List<Category> getListCategories() {
        List<Category> categories = new ArrayList<Category>();
        categories.add(new Category(R.drawable.img_kpop, "KPOP", R.color.grey));
        categories.add(new Category(R.drawable.img_kpop, "VPOP", R.color.light_blue));
        categories.add(new Category(R.drawable.img_kpop, "POP", R.color.sky_blue));
        categories.add(new Category(R.drawable.img_kpop, "AAA", R.color.grey));
        categories.add(new Category(R.drawable.img_kpop, "KPOP", R.color.grey));
        categories.add(new Category(R.drawable.img_kpop, "VPOP", R.color.light_blue));
        return categories; 
    }

    private void setResultForSearch(List<Song> songs) {
        rvCategories.setVisibility(View.GONE);
        rvResultsForSearch.setVisibility(View.VISIBLE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvResultsForSearch.setLayoutManager(layoutManager);
        songAdapter.setSongs(songs);

        rvResultsForSearch.setAdapter(songAdapter);
    }

    private void searchSongByTitle(String query) {
        List<Song> songs = db.getSongsByTitle(query);
        if(songs.size() > 0) {
            setResultForSearch(songs);
        }
    }

}
