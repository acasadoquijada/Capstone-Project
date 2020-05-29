package com.example.podcasfy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.podcasfy.UI.PodcastListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        PodcastListFragment podcastFragment = new PodcastListFragment();

        fragmentManager.beginTransaction().add(R.id.main_activity_fragment,podcastFragment).commit();
    }
}
