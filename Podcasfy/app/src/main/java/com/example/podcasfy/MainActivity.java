package com.example.podcasfy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.podcasfy.UI.PodcastFragment;


import com.example.podcasfy.UI.PodcastListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements PodcastListFragment.onGridElementClick{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);


        NavController.OnDestinationChangedListener listener = new NavController.OnDestinationChangedListener(){

            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {

                assert destination.getLabel() != null;
                if(destination.getLabel().equals(PodcastFragment.class.getSimpleName())){

                    bottomNavigationView.setVisibility(View.INVISIBLE);

                } else {
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
            }
        };

        navController.addOnDestinationChangedListener(listener);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);


    }


    @Override
    public void onItemSelected(String pos) {

        Toast.makeText(this,"pos" + pos ,Toast.LENGTH_LONG).show();
    }
}
