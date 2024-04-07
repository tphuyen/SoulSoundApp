package com.project.soulsoundapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.soulsoundapp.R;
import com.project.soulsoundapp.fragment.HomeFragment;
import com.project.soulsoundapp.fragment.LibraryFragment;
import com.project.soulsoundapp.fragment.MiniPlayerFragment;
import com.project.soulsoundapp.fragment.SearchFragment;
import com.project.soulsoundapp.fragment.SettingFragment;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;
    FrameLayout fragmentLayout;
    FrameLayout flMiniPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFragment(new HomeFragment());

//        drawerLayout = findViewById(R.id.drawer_layout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        fragmentLayout = findViewById(R.id.fragment_layout);
//        fab = findViewById(R.id.fab);

        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.search) {
                replaceFragment(new SearchFragment());
            } else if (itemId == R.id.library) {
                replaceFragment(new LibraryFragment());
            } else if (itemId == R.id.settings) {
                replaceFragment(new SettingFragment());
            } else {
                replaceFragment(new SettingFragment()); // Default handling
            }
            return true;
        });

        addControls();
        setMiniPlayer();
    }

    private void addControls() {
        flMiniPlayer = findViewById(R.id.flMiniPlayer);
    }

    private void setMiniPlayer() {
        flMiniPlayer.setVisibility(View.VISIBLE);

        MiniPlayerFragment miniPlayerFragment = new MiniPlayerFragment(this);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.flMiniPlayer, miniPlayerFragment).commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_layout, fragment);
        fragmentTransaction.commit();
    }
}
