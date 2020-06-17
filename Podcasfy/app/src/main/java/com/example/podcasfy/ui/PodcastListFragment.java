package com.example.podcasfy.ui;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
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
import com.example.podcasfy.api.Provider;
import com.example.podcasfy.databinding.PodcastListFragmentBinding;
import com.example.podcasfy.model.Podcast;
import com.example.podcasfy.viewmodel.PodcastListViewModel;
import com.example.podcasfy.R;

import java.util.List;

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
        setupDigital();
    }

    /**
     * To setup the RecyclerView, LayoutManager and Adapter for the Subscriptions RecyclerView
     */
    private void setupSubscriptions(){
        RecyclerView recyclerViewSubscriptions = binding.podcastListSubscription;
        recyclerViewSubscriptions.setLayoutManager(createGridLayoutManager());
        mAdapterSubscriptions = createPodcastListAdapter(Provider.SUBSCRIBED);
        recyclerViewSubscriptions.setAdapter(mAdapterSubscriptions);
    }

    /**
     * To setup the RecyclerView, LayoutManager and Adapter for the Ivoox RecyclerView
     */
    private void setupIvoox(){
        RecyclerView recyclerViewIvoox =  binding.podcastListIvoox;
        recyclerViewIvoox.setLayoutManager(createGridLayoutManager());
        mAdapterIvoox = createPodcastListAdapter(Provider.IVOOX);
        recyclerViewIvoox.setAdapter(mAdapterIvoox);
    }

    /**
     * To setup the RecyclerView, LayoutManager and Adapter for the Digital RecyclerView
     */
    private void setupDigital(){
        RecyclerView recyclerViewDigital =  binding.podcastListDigital;
        recyclerViewDigital.setLayoutManager(createGridLayoutManager());
        mAdapterSpotify = createPodcastListAdapter(Provider.DIGITAL);
        recyclerViewDigital.setAdapter(mAdapterSpotify);
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

    private PodcastListAdapter createPodcastListAdapter(String provider){
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
            mAdapterSubscriptions.notifyDataSetChanged();
        });

        mViewModel.getIvooxRecommended().observe(getViewLifecycleOwner(), podcastList -> mAdapterIvoox.setPodcasts(podcastList));
        mViewModel.getDigitalRecommended().observe(getViewLifecycleOwner(), new Observer<List<Podcast>>() {
            @Override
            public void onChanged(List<Podcast> podcastList) {
                Log.d("ALEX__","ONCHANGED");
                mAdapterSpotify.setPodcasts(podcastList);
                mAdapterSpotify.notifyDataSetChanged();
            }
        });
    }

    /**
     * To launch a PodcastFragment, we open a PodcastFragment with the id of the Podcast selected
     * using the NavigationComponent
     * @param clickedItem index of the selected Podcast
     */

    @Override
    public void onItemClick(int clickedItem, String provider) {
        NavDirections action =
                PodcastListFragmentDirections.actionPodcastListFragmentToPodcastFragment(clickedItem,provider);
        NavHostFragment.findNavController(this).navigate(action);
    }
}
