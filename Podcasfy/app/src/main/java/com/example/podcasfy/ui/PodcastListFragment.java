package com.example.podcasfy.ui;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.podcasfy.adapter.PodcastListAdapter;
import com.example.podcasfy.databinding.PodcastListFragmentBinding;
import com.example.podcasfy.viewmodel.PodcastListViewModel;
import com.example.podcasfy.R;

public class PodcastListFragment extends Fragment implements PodcastListAdapter.ItemClickListener  {

    private PodcastListViewModel mViewModel;

    private PodcastListAdapter mAdapterSubscriptions;
    private PodcastListAdapter mAdapterIvoox;
    private PodcastListAdapter mAdapterSpotify;

    private PodcastListFragmentBinding binding;

    public PodcastListFragment(){}

    /**
     * To inflate the view, we inflate the view using DataBinding, then we setup the different
     * RecyclerViews with its Adapters and LayoutManagers
     * @param inflater Layout inflater object
     * @param container ViewGroup
     * @param savedInstanceState bundle
     * @return the inflated view
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding =
                DataBindingUtil.inflate(inflater,R.layout.podcast_list_fragment, container, false);

        setupRecyclerViews();

        return binding.getRoot();

    }

    /**
     * To setup the RecyclerViews we create the necessary LayoutManager and PodcastListAdapter
     */
    private void setupRecyclerViews(){
        setupSubscriptions();
        setupIvoox();
        setupSpotify();
    }

    /**
     * To setup the RecyclerView, LayoutManager and Adapter for the Subscriptions RecyclerView
     */
    private void setupSubscriptions(){
        RecyclerView recyclerViewSubscriptions = binding.podcastListSubscription;
        recyclerViewSubscriptions.setLayoutManager(createGridLayoutManager());
        mAdapterSubscriptions = createPodcastListAdapter(PodcastListAdapter.SUBSCRIBED);
        recyclerViewSubscriptions.setAdapter(mAdapterSubscriptions);
    }

    /**
     * To setup the RecyclerView, LayoutManager and Adapter for the Ivoox RecyclerView
     */
    private void setupIvoox(){
        RecyclerView recyclerViewIvoox =  binding.podcastListIvoox;
        recyclerViewIvoox.setLayoutManager(createGridLayoutManager());
        mAdapterIvoox = createPodcastListAdapter(PodcastListAdapter.IVOOX);
        recyclerViewIvoox.setAdapter(mAdapterIvoox);
    }

    /**
     * To setup the RecyclerView, LayoutManager and Adapter for the Spotify RecyclerView
     */
    private void setupSpotify(){
        RecyclerView recyclerViewSpotify =  binding.podcastListSpotify;
        recyclerViewSpotify.setLayoutManager(createGridLayoutManager());
        mAdapterSpotify = createPodcastListAdapter(PodcastListAdapter.SPOTIFY);
        recyclerViewSpotify.setAdapter(mAdapterSpotify);
    }

    /**
     * To create a GridLayoutManager with spanCount = 1 and horizontal orientation
     * @return a GridLayoutManager object with spanCount = 1 and horizontal orientation
     */
    private GridLayoutManager createGridLayoutManager(){

        int spanCount = 1;

        GridLayoutManager manager = new GridLayoutManager(getContext(), spanCount);

        manager.setOrientation(RecyclerView.HORIZONTAL);

        return manager;
    }

    private PodcastListAdapter createPodcastListAdapter(int provider){
        return new PodcastListAdapter(this, provider);
    }


    /**
     * To create the ViewModel and setup the observer, we observe the changes in the Podcasts field
     * and update the podcastListAdapter with the new list of podcasts
     * @param savedInstanceState bundle
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(PodcastListViewModel.class);

        mViewModel.getPodcasts().observe(getViewLifecycleOwner(), podcasts -> {

            mAdapterSubscriptions.setPodcasts(podcasts);
            mAdapterIvoox.setPodcasts(podcasts);
            mAdapterSpotify.setPodcasts(podcasts);

            mAdapterSubscriptions.notifyDataSetChanged();
            mAdapterIvoox.notifyDataSetChanged();
            mAdapterSpotify.notifyDataSetChanged();
            Log.d("ALEX___", "Value in Fragment " + podcasts.get(0).getDescription());
        });

        mViewModel.testing();
    }


    /**
     * To launch a PodcastFragment, we open a PodcastFragment with the id of the Podcast selected
     * using the NavigationComponent
     * @param clickedItemIndex index of the selected Podcast
     */
    @Override
    public void onItemClick(int clickedItemIndex) {
        NavDirections action =
                PodcastListFragmentDirections.actionPodcastListFragmentToPodcastFragment("id");
        NavHostFragment.findNavController(this).navigate(action);
    }
}
