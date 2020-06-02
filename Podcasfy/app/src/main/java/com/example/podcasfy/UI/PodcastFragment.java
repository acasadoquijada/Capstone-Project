package com.example.podcasfy.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcasfy.R;
import com.example.podcasfy.adapter.PodcastEpisodeListAdapter;


public class PodcastFragment extends Fragment implements PodcastEpisodeListAdapter.ItemClickListener {



    public static PodcastFragment newInstance() {
        return new PodcastFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        PodcastFragmentArgs b = PodcastFragmentArgs.fromBundle(requireArguments());
        getActivity().setTitle(b.getPodcastName());
        final View rootView = inflater.inflate(R.layout.podcast_fragment, container, false);
        setHasOptionsMenu(true);

      // setHasOptionsMenu(true);

       // TextView v = rootView.findViewById(R.id.podcastNamePodcastFragment);

        RecyclerView r1 = rootView.findViewById(R.id.episodeRecyclerView);

        GridLayoutManager g = new GridLayoutManager(getContext(),1);

        g.setOrientation(RecyclerView.VERTICAL);
        PodcastEpisodeListAdapter podcastEpisodeListAdapter = new PodcastEpisodeListAdapter(this);

        r1.setLayoutManager(g);

        r1.setAdapter(podcastEpisodeListAdapter);


 //       v.setText(b.getPodcastName());

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.app_bar_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.share_podcast){
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Your body here";
            String shareSub = "Your subject here";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void populateUI(){

    }


    @Override
    public void onItemClick(int clickedItem) {

        Toast.makeText(getContext(),"" + clickedItem,Toast.LENGTH_SHORT).show();
    }
}
