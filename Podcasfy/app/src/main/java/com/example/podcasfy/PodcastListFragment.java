package com.example.podcasfy;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.podcasfy.databinding.PodcastListFragmentBinding;
import com.example.podcasfy.model.Podcast;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

public class PodcastListFragment extends Fragment {

    private PodcastListViewModel mViewModel;
    private PodcastListFragmentBinding binding;

    public static PodcastListFragment newInstance() {
        return new PodcastListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.podcast_list_fragment, container, false);

        return binding.getRoot();

        //return inflater.inflate(R.layout.podcast_fragment, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(PodcastListViewModel.class);

        // TODO: Use the ViewModel
        mViewModel.getPodcasts().observe(getViewLifecycleOwner(), new Observer<List<Podcast>>() {
            @Override
            public void onChanged(List<Podcast> podcasts) {
                binding.podcastLayout.podcastName.setText(podcasts.get(0).getName());
                binding.podcastLayout2.podcastName.setText(podcasts.get(0).getName());

                Picasso.get().load(podcasts.get(0).getMediaURL()).into(binding.podcastLayout.podcastImage);
                Picasso.get().load(podcasts.get(0).getMediaURL()).into(binding.podcastLayout2.podcastImage);
            }
        });
    }

    private void populateUI(){

    }
}
