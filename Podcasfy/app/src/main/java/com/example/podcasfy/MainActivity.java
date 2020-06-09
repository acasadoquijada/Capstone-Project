package com.example.podcasfy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;


import com.example.podcasfy.ui.PodcastEpsiodeFragment;
import com.example.podcasfy.ui.PodcastListFragment;
import com.example.podcasfy.utils.KeepStateNavigator;
import com.example.podcasfy.utils.onGridElementClick;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity implements onGridElementClick {

    PodcastListFragment podcastListFragment;

    private  FragmentManager fragmentManager;
    private PodcastEpsiodeFragment nextFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    @Override
    public void onItemSelected(String posdcastName) {

    }
}
