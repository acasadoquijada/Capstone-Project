package com.example.podcasfy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
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
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        setUpBottonNavigation();

        if(savedInstanceState == null){
            FragmentManager fragmentManager = getSupportFragmentManager();

            PodcastListFragment podcastFragment = new PodcastListFragment();

            fragmentManager.beginTransaction().add(R.id.main_activity_fragment,podcastFragment).commit();
        }
    }

    private void setUpBottonNavigation(){
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.page_1){
                    Toast.makeText(getApplicationContext(),"PAGE 1",Toast.LENGTH_SHORT).show();
                    return true;
                } else if(item.getItemId() == R.id.page_2){
                    Toast.makeText(getApplicationContext(),"PAGE 2",Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onItemSelected(String pos) {

        Toast.makeText(this,"pos" + pos ,Toast.LENGTH_LONG).show();
    }
}
