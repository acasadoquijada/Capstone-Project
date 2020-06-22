package com.example.podcasfy.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcasfy.R;
import com.example.podcasfy.adapter.PodcastListAdapter;
import com.example.podcasfy.api.Provider;
import com.example.podcasfy.databinding.SearchFragmentBinding;
import com.example.podcasfy.model.Podcast;
import com.example.podcasfy.viewmodel.PodcastListViewModel;

import java.util.List;


public class SearchFragment extends Fragment implements PodcastListAdapter.ItemClickListener {

    private PodcastListViewModel viewModel;
    private PodcastListAdapter adapter;
    private SearchFragmentBinding binding;

    public SearchFragment(){}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.search_fragment,container,false);

        setupRecyclerViewSearch();
        setupSearchView();

        return binding.getRoot();
    }

    private void setupSearchView(){
        SearchView searchView = binding.searchView;

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
                viewModel.getSearchQuery().setValue(query);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void setupRecyclerViewSearch(){
        RecyclerView recyclerViewSearch = binding.searchRecyclerView;
        recyclerViewSearch.setLayoutManager(createGridLayoutManager());
        adapter = createPodcastListAdapter(Provider.SEARCH);
        recyclerViewSearch.setAdapter(adapter);
    }

    private GridLayoutManager createGridLayoutManager(){

        int spanCount = 3;

        GridLayoutManager manager = new GridLayoutManager(getContext(), spanCount);

        manager.setOrientation(RecyclerView.VERTICAL);

        return manager;
    }

    private PodcastListAdapter createPodcastListAdapter(String provider){
        return new PodcastListAdapter(this, provider);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewModel();
    }

    private void setupViewModel(){
        createViewModel();
        observeSearchQuery();
        observeSearchedPodcast();
    }

    private void createViewModel(){
        viewModel = new ViewModelProvider(requireActivity()).get(PodcastListViewModel.class);
    }

    private void observeSearchQuery(){
        viewModel.getSearchQuery().observe(getViewLifecycleOwner(), s -> viewModel.searchPodcast());
    }

    private void observeSearchedPodcast(){
        viewModel.getSearchedPodcastList().observe(getViewLifecycleOwner(),
                podcastList -> adapter.setPodcasts(podcastList));
    }

    @Override
    public void onItemClick(int clickedItem, String provider) {

        Log.d("ITEM CLICKED", "" + clickedItem);
        NavDirections action =
                SearchFragmentDirections.actionSearchFragmentToPodcastFragment(clickedItem,provider);
        NavHostFragment.findNavController(this).navigate(action);
    }
}
