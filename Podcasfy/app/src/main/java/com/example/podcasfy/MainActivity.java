package com.example.podcasfy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.podcasfy.adapter.SubscribedPodcastsListAdapter;
import com.example.podcasfy.ui.DownloadsFragment;
import com.example.podcasfy.ui.PodcastListFragment;
import com.example.podcasfy.ui.SearchFragment;
import com.example.podcasfy.ui.SettingsFragment;
import com.example.podcasfy.ui.SubscribedPodcastFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    PodcastListFragment podcastListFragment;

    private  FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        if (savedInstanceState == null) {
            fragmentManager = getSupportFragmentManager();

            PodcastListFragment podcastListFragment = new PodcastListFragment();

            fragmentManager.beginTransaction().add(R.id.nav_host_fragment, podcastListFragment).commit();

            fragmentManager = getSupportFragmentManager();

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.podcastListFragment:
                            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment,podcastListFragment);
                            return true;
/*
                    case R.id.searchFragment:
                        fm.beginTransaction().hide(active).show(searchFragment).commit();
                        active = searchFragment;
                        return true;

                    case R.id.subscribedPodcastFragment:
                        fm.beginTransaction().hide(active).show(subscribedPodcastFragment).commit();
                        active = subscribedPodcastFragment;
                        return true;

                    case R.id.donwloadsFragment:
                        fm.beginTransaction().hide(active).show(downloadsFragment).commit();
                        active = downloadsFragment;
                        return true;

                    case R.id.settingsFragment:
                        fm.beginTransaction().hide(active).show(settingsFragment).commit();
                        active = settingsFragment;
                        return true;*/
                    }
                    return false;
                }
            });
        }


    }


}
