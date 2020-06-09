package com.example.podcasfy.ui;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.podcasfy.MainActivity;
import com.example.podcasfy.adapter.PodcastListAdapter;
import com.example.podcasfy.databinding.PodcastListFragmentBinding;
import com.example.podcasfy.model.PodcastEpisode;
import com.example.podcasfy.utils.onGridElementClick;
import com.example.podcasfy.viewmodel.PodcastListViewModel;
import com.example.podcasfy.R;
import com.example.podcasfy.model.Podcast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PodcastListFragment extends Fragment implements PodcastListAdapter.ItemClickListener  {

    private PodcastListViewModel mViewModel;
    private List<Podcast> mPodcasts;
    private List<String> mPodcastNames;
    private List<String> mPodcastImages;

    private PodcastListAdapter podcastListAdapter;
    private PodcastListAdapter podcastListAdapter2;

    private onGridElementClick mCallback;
    private PodcastListFragmentBinding binding;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mCallback = (onGridElementClick) context;
        }catch (ClassCastException e){
            throw new ClassCastException(
                    context.toString() + "must implement onGridElementClick interface");
        }
    }
    @Override
    public void onItemClick(int clickedItemIndex) {

        NavDirections action =
                     PodcastListFragmentDirections
                        .actionPodcastListFragment2ToPodcastFragment2();

        NavHostFragment.findNavController(this).navigate(action);
    }

    public static PodcastListFragment newInstance() {
        return new PodcastListFragment();
    }

    public PodcastListFragment(){}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding =
                DataBindingUtil.inflate(inflater,R.layout.podcast_list_fragment, container, false);

        RecyclerView recyclerView1 = binding.podcastList1;
        RecyclerView recyclerView2 =  binding.podcastList2;

        binding.setHandler(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 1);

        gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        gridLayoutManager2.setOrientation(RecyclerView.HORIZONTAL);

        recyclerView1.setLayoutManager(gridLayoutManager);
        recyclerView2.setLayoutManager(gridLayoutManager2);

        podcastListAdapter = null;
        podcastListAdapter2 = null;

        try {
            mPodcasts = new ArrayList<>();
            mPodcastImages = new ArrayList<>();
            mPodcastNames = new ArrayList<>();
            podcastListAdapter = new PodcastListAdapter(mPodcasts,this);
            podcastListAdapter2 = new PodcastListAdapter(mPodcasts,this);
     //       podcastListAdapter = new PodcastListAdapter(mPodcastNames,mPodcastImages,this);
        //    podcastListAdapter2 = new PodcastListAdapter(mPodcastNames,mPodcastImages,this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        recyclerView1.setAdapter(podcastListAdapter);
        recyclerView2.setAdapter(podcastListAdapter2);
        return binding.getRoot();

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(PodcastListViewModel.class);

        mViewModel.getPodcasts().observe(getViewLifecycleOwner(), podcasts -> {


            mPodcasts.clear();
            mPodcasts = podcasts;
            podcastListAdapter.setPodcasts(podcasts);
            podcastListAdapter2.setPodcasts(podcasts);
        });
/*
        mViewModel.getPodcastImages().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> images) {
                mPodcastImages.clear();
                mPodcastImages = images;
             //   podcastListAdapter.setPodcastsImages(images);
   //             podcastListAdapter2.setPodcastsImages(images);

            }
        });

        mViewModel.getPodcastNames().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> names) {
                mPodcastNames.clear();
                mPodcastNames = names;
                podcastListAdapter.setPodcastNames(names);
                podcastListAdapter2.setPodcastNames(names);
            }
        });*/
    }
}
