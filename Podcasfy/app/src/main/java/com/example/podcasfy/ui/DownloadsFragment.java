package com.example.podcasfy.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcasfy.R;
import com.example.podcasfy.adapter.EpisodeListAdapter;
import com.example.podcasfy.databinding.DownloadFragmentBinding;
import com.example.podcasfy.model.Episode;
import com.example.podcasfy.viewmodel.PodcastViewModel;

import java.util.List;

public class DownloadsFragment extends Fragment implements EpisodeListAdapter.ItemClickListener {

    private DownloadFragmentBinding binding;
    private EpisodeListAdapter adapter;
    private PodcastViewModel mViewModel;

    public DownloadsFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.download_fragment,container,false);

        setupRecyclerView();

        return binding.getRoot();

    }

    /**
     * To setup the RecyclerViewDownloads we create the necessary LayoutManager and PodcastListAdapter
     */
    private void setupRecyclerView(){
        // Setting Adapter, RecyclerView and LayoutManager

        RecyclerView recyclerViewDownloads = binding.downloadRecyclerView;

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
        adapter = new EpisodeListAdapter(this,false);
        return adapter;
    }

    @Override
    public void onItemClick(int clickedItem) {

        NavDirections action =
                DownloadsFragmentDirections.actionDonwloadsFragmentToPodcastEpsiodeFragment("id");
        NavHostFragment.findNavController(this).navigate(action);
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
        observeDownloads();
    }

    private void createPodcastViewModel(){
        mViewModel = new PodcastViewModel();
    }

    /**
     * To observe the downloaded episodes information and update the UI
     */
    private void observeDownloads(){
        mViewModel.getDonwloadedEpisodes().observe(getViewLifecycleOwner(), this::updateAdapterEpisodes);
    }

    private void updateAdapterEpisodes(List<Episode> episodes){
        adapter.setEpisodes(episodes);
    }


}
