package com.example.podcasfy.UI;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.podcasfy.PodcastListViewModel;
import com.example.podcasfy.R;
import com.example.podcasfy.databinding.PodcastListFragmentBinding;

import java.util.ArrayList;
import java.util.List;

public class PodcastListFragment extends Fragment {

    private PodcastListViewModel mViewModel;
    private PodcastListFragmentBinding binding;
    private List<String> mPodcastNames;
    private List<String> mPodcastImages;

    private PodcastListAdapter podcastListAdapter;

    public static PodcastListFragment newInstance() {
        return new PodcastListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.podcast_list_fragment, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.podcast_list);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);

        gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        recyclerView.setLayoutManager(gridLayoutManager);

        podcastListAdapter = null;

        try {
            mPodcastImages = new ArrayList<>();
            mPodcastNames = new ArrayList<>();
            podcastListAdapter = new PodcastListAdapter(mPodcastNames,mPodcastImages);
        } catch (Exception e) {
            e.printStackTrace();
        }

        recyclerView.setAdapter(podcastListAdapter);
        return rootView;

        //return inflater.inflate(R.layout.podcast_fragment, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(PodcastListViewModel.class);

        mViewModel.getPodcastImages().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> images) {
                podcastListAdapter.setPodcastsImages(images);

            }
        });

        mViewModel.getPodcastNames().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> names) {
                podcastListAdapter.setPodcastNames(names);

            }
        });
    }

    private void populateUI(){

    }
}
