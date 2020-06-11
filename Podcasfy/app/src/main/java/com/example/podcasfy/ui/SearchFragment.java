package com.example.podcasfy.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcasfy.R;
import com.example.podcasfy.adapter.PodcastListAdapter;
import com.example.podcasfy.model.Podcast;
import com.example.podcasfy.viewmodel.PodcastViewModel;

import java.util.Objects;


public class SearchFragment extends Fragment implements PodcastListAdapter.ItemClickListener {

    private PodcastViewModel mViewModel;

    private PodcastListAdapter adapter;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.search_fragment, container, false);

        // Adapter, RecyclerView and LayoutManager

        adapter = new PodcastListAdapter(this, PodcastListAdapter.SUBSCRIBED);
        RecyclerView recyclerView = rootView.findViewById(R.id.searchRecyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        // Setup searchView interaction
        SearchView searchView = rootView.findViewById(R.id.searchView);

        // Click anywhere will open the keyboard
        searchView.setOnClickListener(v -> {
            if (v.getId() == R.id.searchView) {
                searchView.onActionViewExpanded();
            }
        });

        // We query new data from the ViewModel according to the query result
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mViewModel.getQueryId().setValue(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return rootView;
    }

    // Create ViewModel, observe changes in the queryId and query new data

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(PodcastViewModel.class);

        mViewModel.getQueryId().observe(getViewLifecycleOwner(), s -> adapter.setPodcasts(mViewModel.searchPodcasts(s)));
    }

    // Open a PodcastFragment
    @Override
    public void onItemClick(int clickedItem) {


    }
}
