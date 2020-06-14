package com.example.podcasfy.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcasfy.R;
import com.example.podcasfy.adapter.PodcastListAdapter;
import com.example.podcasfy.adapter.SubscribedPodcastsListAdapter;
import com.example.podcasfy.databinding.PodcastListFragmentBinding;
import com.example.podcasfy.databinding.SubscribedPodcastsFragmentBinding;
import com.example.podcasfy.model.Podcast;
import com.example.podcasfy.viewmodel.PodcastListViewModel;
import com.example.podcasfy.viewmodel.SubscribedViewModel;

import java.util.ArrayList;
import java.util.List;

public class SubscribedFragment extends Fragment implements SubscribedPodcastsListAdapter.ItemClickListener{


    private SubscribedPodcastsFragmentBinding binding;
    private SubscribedPodcastsListAdapter mAdapter;
    private SubscribedViewModel mViewModel;
    public SubscribedFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding =
                DataBindingUtil.inflate(inflater,R.layout.subscribed_podcasts_fragment, container, false);

        setupRecyclerViews();

        return binding.getRoot();
    }

    /**
     * To setup the RecyclerView we create the necessary LayoutManager and PodcastListAdapter
     */
    private void setupRecyclerViews(){
        setupSubscriptions();
    }

    /**
     * To setup the RecyclerView, LayoutManager and Adapter for the Subscriptions RecyclerView
     */
    private void setupSubscriptions(){

        RecyclerView recyclerViewSubscriptions = binding.subscribedRecyclerView;
        recyclerViewSubscriptions.setLayoutManager(createGridLayoutManager());
        mAdapter = createPodcastListAdapter();
        recyclerViewSubscriptions.setAdapter(mAdapter);
    }

    /**
     * To create a GridLayoutManager with spanCount = 1 and horizontal orientation
     * @return a GridLayoutManager object with spanCount = 1 and horizontal orientation
     */
    private GridLayoutManager createGridLayoutManager(){

        int spanCount = 1;

        GridLayoutManager manager = new GridLayoutManager(getContext(), spanCount);

        manager.setOrientation(RecyclerView.VERTICAL);

        return manager;
    }

    private SubscribedPodcastsListAdapter createPodcastListAdapter(){
        return new SubscribedPodcastsListAdapter(this);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(SubscribedViewModel.class);

        mViewModel.getSubscriptionList().observe(getViewLifecycleOwner(), podcasts -> {
            mAdapter.setSubscriptions(podcasts);
        });
    }

    @Override
    public void onItemClick(int clickedItem) {

        NavDirections action =
        SubscribedFragmentDirections.actionSubscribedPodcastFragmentToPodcastFragment("id");

        NavHostFragment.findNavController(this).navigate(action);

    }
}