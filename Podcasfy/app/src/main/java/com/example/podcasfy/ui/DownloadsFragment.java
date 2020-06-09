package com.example.podcasfy.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcasfy.R;
import com.example.podcasfy.adapter.PodcastEpisodeListAdapter;
import com.example.podcasfy.viewmodel.PodcastViewModel;

public class DownloadsFragment extends Fragment implements PodcastEpisodeListAdapter.ItemClickListener {

    private PodcastEpisodeListAdapter adapter;
    private PodcastViewModel mViewModel;

    public static DownloadsFragment newInstance() {
        return new DownloadsFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.download_fragment,container,false);

        // Setting Adapter, RecyclerView and LayoutManager
        adapter = new PodcastEpisodeListAdapter(this,false);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        RecyclerView view = root.findViewById(R.id.downloadRecyclerView);

        view.setAdapter(adapter);
        view.setLayoutManager(layoutManager);


        return root;

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

        mViewModel = new PodcastViewModel();

        mViewModel.getDonwloadedEpisodes().observe(getViewLifecycleOwner(), podcastEpisodes -> adapter.setPodcastEpisodes(podcastEpisodes));
    }
}
