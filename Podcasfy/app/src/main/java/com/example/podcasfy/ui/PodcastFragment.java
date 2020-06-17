package com.example.podcasfy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcasfy.R;
import com.example.podcasfy.adapter.EpisodeListAdapter;
import com.example.podcasfy.api.Provider;
import com.example.podcasfy.databinding.PodcastFragmentBinding;
import com.example.podcasfy.model.Episode;
import com.example.podcasfy.model.Podcast;
import com.example.podcasfy.viewmodel.PodcastListViewModel;
import com.example.podcasfy.viewmodel.PodcastViewModel;
import com.example.podcasfy.viewmodel.ReproducerViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;


public class PodcastFragment extends Fragment implements EpisodeListAdapter.ItemClickListener {

    private PodcastViewModel mViewModel;
    private PodcastListViewModel podcastListViewModel;
    private ReproducerViewModel reproducerViewModel;
    private PodcastFragmentBinding mBinding;
    private EpisodeListAdapter adapter;

    private String provider;
    private int pos;

    private String episodeURL;

    @Override
    public void onItemClick(int clickedItem, boolean delete) {
        updateName("pepe");
    }

    public interface ItemClickListener {
        void onItemClick(int clickedItem);
    }

    public PodcastFragment(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.podcast_fragment, container, false);

        setupRecyclerView();

        mBinding.subscriptionButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    Log.d("ALEX__","SUBSCRIBE");
                } else {
                    Log.d("ALEX__","NO SUBSCRIBE");
                }
            }
        });

        return mBinding.getRoot();
    }

    /**
     * To setup the RecyclerViewEpisodes we create the necessary LayoutManager and PodcastListAdapter
     */
    private void setupRecyclerView(){

        RecyclerView recyclerViewEpisodes = mBinding.getRoot().findViewById(R.id.episodeRecyclerView);

        recyclerViewEpisodes.setLayoutManager(createGridLayoutManager());

        recyclerViewEpisodes.setAdapter(createPodcastEpisodeListAdapter());
    }

    /**
     * To create a GridLayoutManager with spanCount = 1 and horizontal orientation
     * @return a GridLayoutManager object with spanCount = 1 and horizontal orientation
     */
    private GridLayoutManager createGridLayoutManager(){

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

        return gridLayoutManager;

    }
    /**
     * To create a PodcastEpisodeListAdapterw with OnClickListener but without swipeListener
     * @return a PodcastEpisodeListAdapter without swipeListener
     */
    private EpisodeListAdapter createPodcastEpisodeListAdapter(){
        adapter = new EpisodeListAdapter(this,false);
        return adapter;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.app_bar_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
}

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.share_podcast){
            setupShareInformation();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * To create the information to be shared and create a implicit intent
     */

    private void setupShareInformation(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Your body here";
        String shareSub = "Your subject here";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createReproducerViewModel();
        setupPodcastViewModel();

        podcastListViewModel =  new ViewModelProvider(requireActivity()).get(PodcastListViewModel.class);

        assert getArguments() != null;
        PodcastFragmentArgs podcastFragmentArgs = PodcastFragmentArgs.fromBundle(getArguments());

        pos = podcastFragmentArgs.getPos();
        provider = podcastFragmentArgs.getProvider();

        if(provider.equals(Provider.IVOOX)) {
            if (podcastListViewModel.getIvooxRecommended() != null) {
                updateUI(podcastListViewModel.getIvooxRecommended().getValue().get(pos));
                podcastListViewModel.getIvooxEpisodes(episodeURL).observe(getViewLifecycleOwner(), new Observer<List<Episode>>() {
                    @Override
                    public void onChanged(List<Episode> episodes) {
                        adapter.setEpisodes(episodes);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        } else if(provider.equals(Provider.SUBSCRIBED)){
            updateUI(podcastListViewModel.getPodcasts().getValue().get(pos));
        } else if(provider.equals(Provider.DIGITAL)) {
            updateUI(podcastListViewModel.getDigitalRecommended().getValue().get(pos));
            podcastListViewModel.getDigitalEpisodes(episodeURL).observe(getViewLifecycleOwner(), new Observer<List<Episode>>() {
                @Override
                public void onChanged(List<Episode> episodes) {
                    Log.d("TEST", "ONCHANGED!");
                    adapter.setEpisodes(episodes);
                    adapter.notifyDataSetChanged();
                }
            });
        }

    }

    /**
     * To setup the PodcastViewModel, we create the PodcastViewModel and observe:
     * - Podcast (podcast information)
     * - Episodes (podcast episodes information)
     */
    private void setupPodcastViewModel(){
        createPodcastViewModel();
      //  observePodcast();
       // observeEpisodes();
    }

    private void createPodcastViewModel(){
        mViewModel = new ViewModelProvider(requireActivity()).get(PodcastViewModel.class);
    }

    /**
     * To observe the podcast information and update the UI
     */
    private void observePodcast(){
        mViewModel.getPodcast().observe(getViewLifecycleOwner(), this::updateUI);
    }

    /**
     * Updates the UI with the podcast information
     */

    private void updateUI(Podcast podcast){
        episodeURL = podcast.getUrl();
        setActivityTitle(podcast.getName());
        setDescription(podcast.getDescription());
        setLogo(podcast.getMediaURL());

    }

    private void setActivityTitle(String name){
        requireActivity().setTitle(name);
    }

    private void setDescription(String description){
        mBinding.podcastDescription.setText(description);
    }

    private void setLogo(String media){
        Picasso.get().load(media).into(mBinding.podcastLogo);
    }

    /**
     * To observe the podcast episodes and updates the adapter
     */
    private void observeEpisodes(){
        mViewModel.getPodcastEpisode().observe(getViewLifecycleOwner(), this::updateAdapterEpisodes);
    }

    private void updateAdapterEpisodes(List<Episode> episodesList){
        adapter.setEpisodes(episodesList);
    }

    /**
     * To create the ReproducerViewModel
     */
    private void createReproducerViewModel(){
        reproducerViewModel = new ViewModelProvider(requireActivity()).get(ReproducerViewModel.class);
    }


    /**
     * To update the ViewModel name field with the name of the selected episode
     * @param name of the episode
     */
    private void updateName(String name){
        reproducerViewModel.setName(name);
    }
}
