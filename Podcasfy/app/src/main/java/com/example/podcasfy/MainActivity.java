package com.example.podcasfy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.example.podcasfy.UI.PodcastListAdapter;
import com.example.podcasfy.databinding.ActivityMainBinding;

import com.example.podcasfy.UI.PodcastListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements PodcastListFragment.onGridElementClick{

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }


    @Override
    public void onItemSelected(String pos) {

        Toast.makeText(this,"pos" + pos ,Toast.LENGTH_LONG).show();
    }
}
