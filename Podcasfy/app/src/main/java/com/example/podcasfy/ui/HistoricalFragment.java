package com.example.podcasfy.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcasfy.R;
import com.example.podcasfy.adapter.EpisodeListAdapter;
import com.example.podcasfy.databinding.HistoricalFragmentBinding;
import com.example.podcasfy.model.Episode;
import com.example.podcasfy.viewmodel.PodcastListViewModel;
import com.example.podcasfy.viewmodel.ReproducerViewModel;

import java.util.List;
import java.util.Objects;

public class HistoricalFragment extends Fragment implements EpisodeListAdapter.ItemClickListener {

    private HistoricalFragmentBinding binding;
    private EpisodeListAdapter adapter;
    private PodcastListViewModel podcastListViewModel;
    private ReproducerViewModel reproducerViewModel;

    public HistoricalFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.historical_fragment,container,false);

        setupRecyclerView();

        return binding.getRoot();

    }

    /**
     * To setup the RecyclerViewDownloads we create the necessary LayoutManager and PodcastListAdapter
     */
    private void setupRecyclerView(){

        RecyclerView recyclerViewDownloads = binding.historicalRecyclerView;

        recyclerViewDownloads.setAdapter(createPodcastEpisodeListAdapter());

        recyclerViewDownloads.setLayoutManager(createGridLayoutManager());

    }

    /**
     * To create a GridLayoutManager with spanCount = 1 and vertical orientation
     * @return a GridLayoutManager object with spanCount = 1 and vertical orientation
     */
    private GridLayoutManager createGridLayoutManager(){

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

        return gridLayoutManager;

    }

    private EpisodeListAdapter createPodcastEpisodeListAdapter(){
        adapter = new EpisodeListAdapter(this,true);
        return adapter;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupPodcastViewModel();
    }

    /**
     * To setup the PodcastViewModel, we create the PodcastViewModel and observe the downloaded
     * episodes
     */

    private void setupPodcastViewModel(){
        createPodcastViewModel();
        createReproducerViewModel();
        observeDownloads();
    }

    private void createPodcastViewModel(){
        podcastListViewModel = new ViewModelProvider(requireActivity()).get(PodcastListViewModel.class);
    }

    private void createReproducerViewModel(){
        reproducerViewModel = new ViewModelProvider(requireActivity()).get(ReproducerViewModel.class);
    }

    /**
     * To observe the downloaded episodes information and update the UI
     */
    private void observeDownloads(){

        podcastListViewModel.getHistoricalEpisodeList().observe(getViewLifecycleOwner(), this::updateAdapterEpisodes);
    }

    private void updateAdapterEpisodes(List<Episode> episodes){
        adapter.setEpisodes(episodes);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(int clickedItem, boolean delete) {

        if(delete){
            podcastListViewModel.deleteEpisode(clickedItem);
            adapter.notifyItemRemoved(clickedItem);
        } else {
            if(clickedItem != -1){
                Episode episode = Objects.requireNonNull(podcastListViewModel.getHistoricalEpisodeList().getValue()).get(clickedItem);
                updateReproducerViewModelInfo(episode);
                Toast.makeText(requireContext(),"" + delete + "-" + clickedItem,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateReproducerViewModelInfo(Episode episode){
        updateName(episode.getName());
        updateLogo(episode.getImageURL());
        updateMediaURL(episode.getMediaURL());
        reproducerViewModel.setShowReproducer(true);
        podcastListViewModel.logEventEpisodeName(episode.getName());
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

/*
    private void updateUI(Podcast podcast){
        setActivityTitle(podcast.getName());
        podcastListViewModel.logEventPodcastName(podcast.getName());
        setDescription(podcast.getDescription());
        setLogo(podcast.getMediaURL());
    }*/
}
