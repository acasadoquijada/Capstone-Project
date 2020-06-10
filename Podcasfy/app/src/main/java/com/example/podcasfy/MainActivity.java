package com.example.podcasfy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;


import com.example.podcasfy.databinding.ActivityMainBinding;
import com.example.podcasfy.ui.EpisodeFragment;
import com.example.podcasfy.ui.MainFragment;
import com.example.podcasfy.utils.PanelSlideListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;


public class MainActivity extends AppCompatActivity implements EpisodeFragment.ItemClickListener {

    ActivityMainBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        BottomNavigationView bottomNavigationView = mBinding.bottomNavigation;

        // First I get a reference to main_fragment

        View fragment_main = findViewById(R.id.fragment_main_activity);

        // Second I get a reference to podcast_list_fragment
        View fragment_podcast_list = fragment_main.findViewById(R.id.nav_host_fragment);

        // Then I set the navController with the graph set in podcast_list_fragment
        NavController navController = Navigation.findNavController(fragment_podcast_list);

        // Finally set BottonNavigationView with the NavController
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

     //   NavigationUI.setupActionBarWithNavController(this,navController);
      //  MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_main_activity);

    }


    @Override
    public void onItemClick(int clickedItem) {


    }
}
