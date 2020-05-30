package com.example.podcasfy.UI;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
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
import com.example.podcasfy.model.Podcast;

import java.util.ArrayList;
import java.util.List;

public class PodcastListFragment extends Fragment implements PodcastListAdapter.ItemClickListener  {

    private PodcastListViewModel mViewModel;
    private PodcastListFragmentBinding binding;
    private List<Podcast> mPodcasts;
    private List<String> mPodcastNames;
    private List<String> mPodcastImages;

    private PodcastListAdapter podcastListAdapter;
    private PodcastListAdapter podcastListAdapter2;

    private onGridElementClick mCallback;

    @Override
    public void onItemClick(int clickedItemIndex) {
        int a = 2;
        mCallback.onItemSelected(mPodcasts.get(clickedItemIndex).getId());
    }

    public interface onGridElementClick{
        void onItemSelected(String posdcastName);
    }

    public static PodcastListFragment newInstance() {
        return new PodcastListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.podcast_list_fragment, container, false);

        RecyclerView recyclerView1 = rootView.findViewById(R.id.podcast_list_1);
        RecyclerView recyclerView2 = rootView.findViewById(R.id.podcast_list_2);

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
            podcastListAdapter = new PodcastListAdapter(mPodcastNames,mPodcastImages,this);
            podcastListAdapter2 = new PodcastListAdapter(mPodcastNames,mPodcastImages,this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        recyclerView1.setAdapter(podcastListAdapter);
        recyclerView2.setAdapter(podcastListAdapter2);
        return rootView;

        //return inflater.inflate(R.layout.podcast_fragment, container, false);
    }

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(PodcastListViewModel.class);

        mViewModel.getPodcasts().observe(getViewLifecycleOwner(), new Observer<List<Podcast>>() {
            @Override
            public void onChanged(List<Podcast> podcasts) {
                mPodcasts.clear();
                mPodcasts = podcasts;
            }
        });

        mViewModel.getPodcastImages().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> images) {
                mPodcastImages.clear();
                mPodcastImages = images;
                podcastListAdapter.setPodcastsImages(images);
                podcastListAdapter2.setPodcastsImages(images);

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
        });
    }

    private void populateUI(){

    }
}
