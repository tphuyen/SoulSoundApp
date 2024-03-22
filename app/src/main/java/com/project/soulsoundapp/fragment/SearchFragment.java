package com.project.soulsoundapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;

import android.view.*;

import com.project.soulsoundapp.R;
import com.project.soulsoundapp.adapter.CategoryAdapter;
import com.project.soulsoundapp.model.Category;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private RecyclerView rvCategories;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
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
        addControl(view);
    }

    public void addControl(View view) {
        rvCategories =view.findViewById(R.id.rvCategories);
        GridLayoutManager managerCategory = new GridLayoutManager(getContext(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

//        LinearLayoutManager managerCategory = new LinearLayoutManager(getContext());

//        managerCategory.setOrientation(RecyclerView.HORIZONTAL);
        rvCategories.setLayoutManager(managerCategory);

        CategoryAdapter categoryAdapter = new CategoryAdapter(getContext());
        categoryAdapter.setData(getListCategories());

        rvCategories.setAdapter(categoryAdapter);
    }

    public void addEvent() {

    }

    public List<Category> getListCategories() {
        List<Category> categories = new ArrayList<Category>();
        categories.add(new Category(R.drawable.kpop, "KPOP", R.color.grey));
        categories.add(new Category(R.drawable.kpop, "VPOP", R.color.light_blue));
        categories.add(new Category(R.drawable.kpop, "POP", R.color.sky_blue));
        categories.add(new Category(R.drawable.kpop, "AAA", R.color.grey));
        categories.add(new Category(R.drawable.kpop, "KPOP", R.color.grey));
        categories.add(new Category(R.drawable.kpop, "VPOP", R.color.light_blue));
        return categories;
    }
}
