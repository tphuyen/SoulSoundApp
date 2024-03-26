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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.soulsoundapp.R;
import com.project.soulsoundapp.fragment.HomeFragment;
import com.project.soulsoundapp.fragment.LibraryFragment;
import com.project.soulsoundapp.fragment.SearchFragment;
import com.project.soulsoundapp.fragment.SettingFragment;


public class MainActivity2 extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;
    FrameLayout fragmentLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

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


    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_layout, fragment);
        fragmentTransaction.commit();
    }
}
