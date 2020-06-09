package com.example.podcasfy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;


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

        // get navcontroller
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);

        //get fragment
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        // setup custom navigator
        KeepStateNavigator navigator =
                new KeepStateNavigator(this,navHostFragment.getChildFragmentManager(),R.id.nav_host_fragment);

        navController.getNavigatorProvider().addNavigator(navigator);

        // set navigation graph
        navController.setGraph(R.navigation.nav_graph_aa);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }

    @Override
    public void onItemSelected(String posdcastName) {

    }
}
