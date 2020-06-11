package com.example.podcasfy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;

import com.example.podcasfy.databinding.ActivityMainBinding;
import com.example.podcasfy.ui.EpisodeFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity implements EpisodeFragment.ItemClickListener {

    private ActivityMainBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        setUpBottonNavigation();

    }

    /**
     * To setup the BottomNavigationView navigation, we get a reference of the fragment that
     * contains the navGraph we want to use, then we get the NavController within it. Finally
     * we setup the BottonNavigationView with the NavController
     */
    private void setUpBottonNavigation(){

        BottomNavigationView bottomNavigationView = mBinding.bottomNavigation;

        // First I get a reference to main_fragment
        View fragment_main = findViewById(R.id.main_fragment);

        // Second I get a reference to podcast_list_fragment
        View fragment_podcast_list = fragment_main.findViewById(R.id.nav_host_fragment);

        // Then I set the navController with the graph set in podcast_list_fragment
        NavController navController = Navigation.findNavController(fragment_podcast_list);

        // Finally set BottonNavigationView with the NavController
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    @Override
    public void onItemClick(int clickedItem) {


    }
}
