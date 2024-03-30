package com.project.soulsoundapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//
//import com.denzcoskun.imageslider.ImageSlider;
//import com.denzcoskun.imageslider.constants.ScaleTypes;
//import com.denzcoskun.imageslider.models.SlideModel;
import com.project.soulsoundapp.Domian.SliderItems;
import com.project.soulsoundapp.R;
import com.project.soulsoundapp.adapter.AlbumAdpater;
import com.project.soulsoundapp.adapter.SliderAdapter;
import com.project.soulsoundapp.model.Album;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import com.project.soulsoundapp.adapter.ImageSliderAdapter;
public class HomeFragment extends Fragment {
    private RecyclerView rvDiscover;
    private ViewPager2 viewPager2;
    private Handler slideHandler = new Handler();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControl(view);

        initView(view);
        banner();
    }

    private void banner() {
        List<SliderItems> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItems(R.drawable.item1));
        sliderItems.add(new SliderItems(R.drawable.item2));
        sliderItems.add(new SliderItems(R.drawable.item3));
        sliderItems.add(new SliderItems(R.drawable.item4));
    viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));
    viewPager2.setClipToPadding(false);
    viewPager2.setClipChildren(false);
    viewPager2.setOffscreenPageLimit(3);
    viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1-Math.abs(position);
                page.setScaleY(0.85f+r*0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.setCurrentItem(3);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(slideRunnable);
            }
        });
    }

    private  Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(slideRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        slideHandler.postDelayed(slideRunnable, 2000);
    }

    private void initView(View view) {
        viewPager2=view.findViewById(R.id.viewpagerSlider);
    }


    private void addControl(View view) {
        rvDiscover = view.findViewById(R.id.rvDiscover);
        AlbumAdpater albumAdpater = new AlbumAdpater(getContext());
        albumAdpater.setAlbums(laydulieu());

        LinearLayoutManager managerDiscover = new LinearLayoutManager(getContext());
        managerDiscover.setOrientation(LinearLayoutManager.HORIZONTAL);

        rvDiscover.setLayoutManager(managerDiscover);
        rvDiscover.setAdapter(albumAdpater);
    }

    private List<Album> laydulieu() {
        List<Album> albums = new ArrayList<>();
        albums.add(new Album("A001", "ABC", new Date(2020, 12, 30), "Gi Gi Do", R.drawable.img_kpop));
        albums.add(new Album("A001", "ABC", new Date(2020, 12, 30), "Gi Gi Do", R.drawable.img_kpop));
        albums.add(new Album("A001", "ABC", new Date(2020, 12, 30), "Gi Gi Do", R.drawable.img_kpop));
        albums.add(new Album("A001", "ABC", new Date(2020, 12, 30), "Gi Gi Do", R.drawable.img_kpop));
        albums.add(new Album("A001", "ABC", new Date(2020, 12, 30), "Gi Gi Do", R.drawable.img_kpop));
        albums.add(new Album("A001", "ABC", new Date(2020, 12, 30), "Gi Gi Do", R.drawable.img_kpop));
        albums.add(new Album("A001", "ABC", new Date(2020, 12, 30), "Gi Gi Do", R.drawable.img_kpop));
        albums.add(new Album("A001", "ABC", new Date(2020, 12, 30), "Gi Gi Do", R.drawable.img_kpop));
        albums.add(new Album("A001", "ABC", new Date(2020, 12, 30), "Gi Gi Do", R.drawable.img_kpop));
        albums.add(new Album("A001", "ABC", new Date(2020, 12, 30), "Gi Gi Do", R.drawable.img_kpop));
        albums.add(new Album("A001", "ABC", new Date(2020, 12, 30), "Gi Gi Do", R.drawable.img_kpop));
        albums.add(new Album("A001", "ABC", new Date(2020, 12, 30), "Gi Gi Do", R.drawable.img_kpop));
        albums.add(new Album("A001", "ABC", new Date(2020, 12, 30), "Gi Gi Do", R.drawable.img_kpop));
        return albums;
    }

}