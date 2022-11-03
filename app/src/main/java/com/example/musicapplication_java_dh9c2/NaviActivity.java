package com.example.musicapplication_java_dh9c2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.musicapplication_java_dh9c2.Fragment.HomeFragment;
import com.example.musicapplication_java_dh9c2.Fragment.MusicListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class NaviActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);


        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.page_home: {
                        fragment = new HomeFragment();
                        loadFragment(fragment);
                        return true;
                    }
                    case R.id.page_list: {
                        fragment = new MusicListFragment();
                        loadFragment(fragment);
                        return true;
                    }
                }
                return false;
            }
        });
        navigation.setSelectedItemId(R.id.page_home);
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}