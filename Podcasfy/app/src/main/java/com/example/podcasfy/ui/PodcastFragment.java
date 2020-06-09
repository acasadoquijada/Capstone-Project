package com.example.podcasfy.ui;

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
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcasfy.R;
import com.example.podcasfy.adapter.PodcastEpisodeListAdapter;
import com.example.podcasfy.databinding.PodcastFragmentBinding;
import com.example.podcasfy.model.Podcast;
import com.example.podcasfy.viewmodel.PodcastViewModel;
import com.squareup.picasso.Picasso;

import java.util.Objects;


public class PodcastFragment extends Fragment implements PodcastEpisodeListAdapter.ItemClickListener {

    private PodcastViewModel mViewModel;
    private PodcastFragmentBinding mBinding;
    private String podcastID;
    private PodcastEpisodeListAdapter podcastEpisodeListAdapter;


    public PodcastFragment(){

    }
    public static PodcastFragment newInstance() {
        return new PodcastFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.podcast_fragment, container, false);

     //   mBinding = DataBindingUtil.setContentView(getActivity(),R.layout.podcast_fragment);

        setHasOptionsMenu(true);

      // setHasOptionsMenu(true);

       // TextView v = rootView.findViewById(R.id.podcastNamePodcastFragment);

        RecyclerView r1 = mBinding.getRoot().findViewById(R.id.episodeRecyclerView);

        GridLayoutManager g = new GridLayoutManager(getContext(),1);

        g.setOrientation(RecyclerView.VERTICAL);
        podcastEpisodeListAdapter = new PodcastEpisodeListAdapter(this,false);

        r1.setLayoutManager(g);

        r1.setAdapter(podcastEpisodeListAdapter);

        return mBinding.getRoot();
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


        mViewModel = new ViewModelProvider(requireActivity()).get(PodcastViewModel.class);
        mViewModel.setPodcastId(podcastID);

        mViewModel.getPodcast().observe(getViewLifecycleOwner(), podcast -> {
            getActivity().setTitle(podcast.getName());
            mBinding.podcastDescription.setText(podcast.getDescription());
            Picasso.get().load(podcast.getMediaURL()).into(mBinding.podcastLogo);
        });

        mViewModel.getPodcastEpisode().observe(getViewLifecycleOwner(), podcastEpisodes -> {
            // Here I update the podcastepisodes in
            podcastEpisodeListAdapter.setPodcastEpisodes(podcastEpisodes);
        });

    }

    @Override
    public void onItemClick(int clickedItem) {
        NavDirections action =
                PodcastFragmentDirections.actionPodcastFragment2ToPodcastEpsiodeFragment2();

        NavHostFragment.findNavController(this).navigate(action);
    }
}
