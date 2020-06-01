package com.example.podcasfy.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import  androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.podcasfy.R;

import org.w3c.dom.Text;


public class PodcastFragment extends Fragment {



    public static PodcastFragment newInstance() {
        return new PodcastFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.podcast_fragment, container, false);

      // setHasOptionsMenu(true);

        TextView v = root.findViewById(R.id.podcastNamePodcastFragment);

        Toolbar toolbar = root.findViewById(R.id.fragment_toolbar);

        toolbar.inflateMenu(R.menu.app_bar_menu);
        PodcastFragmentArgs b = PodcastFragmentArgs.fromBundle(requireArguments());

        v.setText(b.getPodcastName());
      //  v.setText(podca);

        return root;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.app_bar_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void populateUI(){

    }


}
