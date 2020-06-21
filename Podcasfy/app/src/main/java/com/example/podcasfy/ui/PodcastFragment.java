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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcasfy.R;
import com.example.podcasfy.adapter.EpisodeListAdapter;
import com.example.podcasfy.databinding.PodcastFragmentBinding;
import com.example.podcasfy.model.Episode;
import com.example.podcasfy.model.Podcast;
import com.example.podcasfy.viewmodel.PodcastListViewModel;
import com.example.podcasfy.viewmodel.ReproducerViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;


public class PodcastFragment extends Fragment implements EpisodeListAdapter.ItemClickListener {

    private PodcastListViewModel podcastListViewModel;
    private ReproducerViewModel reproducerViewModel;
    private PodcastFragmentBinding mBinding;
    private EpisodeListAdapter adapter;

    private String provider;
    private int pos;

    private String podcastId;
    private boolean susbcribed;

    public PodcastFragment(){}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.podcast_fragment, container, false);

        setupRecyclerView();

        setupSubscriptionButton();

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

    /**
     * To setup the subscription button. We ensure that if we are already subscribed we set the
     * button properly to avoid re-subscriptions
     */
    private void setupSubscriptionButton(){
        mBinding.subscriptionButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked && !susbcribed){
                subscribeToPodcast();
            } else {
                unsubscribedToPodcast();
            }
        });
    }

    private void subscribeToPodcast(){
        podcastListViewModel.subscribeToPodcast(pos,provider);
    }

    private void unsubscribedToPodcast(){
        podcastListViewModel.unsubscribeToPodcast(podcastId);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
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

        createViewModel();

        getBundleArguments();

        setupPodcastInformation();
    }

    private void createViewModel(){
        podcastListViewModel =  new ViewModelProvider(requireActivity()).get(PodcastListViewModel.class);
    }

    private void getBundleArguments(){
        assert getArguments() != null;
        PodcastFragmentArgs podcastFragmentArgs = PodcastFragmentArgs.fromBundle(getArguments());
        pos = podcastFragmentArgs.getPos();
        provider = podcastFragmentArgs.getProvider();
    }

    private void setupPodcastInformation(){

        Podcast podcast = podcastListViewModel.getPodcast(provider,pos);

        observePodcastEpisodes(podcast.getUrl());

        getIfPodcastIsSubscribed(podcast);

        getPodcastId(podcast);

        updateUI(podcast);

        if(susbcribed){
            setSubscriptionButtonChecked();
        }
    }

    private void observePodcastEpisodes(String podcastURL){
        podcastListViewModel.getEpisodeList(provider, podcastURL).observe(getViewLifecycleOwner(),
                this::updateAdapterEpisodes);
    }

    private void getIfPodcastIsSubscribed(Podcast podcast){
        susbcribed = podcastListViewModel.isPodcastSubscribed(podcast);
    }

    private void getPodcastId(Podcast podcast){
        podcastId = podcast.getId();
    }

    private void setSubscriptionButtonChecked(){
        mBinding.subscriptionButton.setChecked(true);
    }

    /**
     * Updates the UI with the podcast information
     */

    private void updateUI(Podcast podcast){
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


    private void updateAdapterEpisodes(List<Episode> episodesList){
        adapter.setEpisodes(episodesList);
    }

    private void createReproducerViewModel(){
        reproducerViewModel = new ViewModelProvider(requireActivity()).get(ReproducerViewModel.class);
    }

    @Override
    public void onItemClick(int clickedItem, boolean delete) {
        Episode episode = Objects.requireNonNull(podcastListViewModel.getEpisodeList().getValue()).get(clickedItem);

        Log.d("MEDIA_URL", "media in PodcastFragment " + episode.getMediaURL());
        updateReproducerViewModelInfo(episode);
    }

    private void updateReproducerViewModelInfo(Episode episode){
        updateName(episode.getName());
        updateLogo(episode.getImageURL());
        updateMediaURL(episode.getMediaURL());
        reproducerViewModel.setShowReproducer(true);
      //  reproducerViewModel.setShowReproducer(true);
    }

    /**
     * To update the ViewModel name field with the name of the selected episode
     * @param name of the episode
     */
    private void updateName(String name){
        reproducerViewModel.setName(name);
    }

    private void updateLogo(String logo){
        reproducerViewModel.setLogoURL(logo);
    }

    private void updateMediaURL(String mediaURL){
        reproducerViewModel.setMediaURL(mediaURL);
    }

    @Override
    public void onPause() {
        super.onPause();

        // We leave the fragment, we set the name of the app in the UI
        setActivityTitle(requireActivity().getString(R.string.app_name));
    }
}
