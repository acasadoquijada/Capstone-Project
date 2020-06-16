package com.example.podcasfy.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcasfy.R;
import com.example.podcasfy.adapter.EpisodeListAdapter;
import com.example.podcasfy.adapter.PodcastListAdapter;
import com.example.podcasfy.databinding.SearchFragmentBinding;
import com.example.podcasfy.model.Podcast;
import com.example.podcasfy.viewmodel.PodcastViewModel;

import java.util.List;
import java.util.Objects;


public class SearchFragment extends Fragment implements PodcastListAdapter.ItemClickListener {

    private PodcastViewModel mViewModel;
    private PodcastListAdapter adapter;
    private SearchFragmentBinding binding;

    public SearchFragment(){}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.search_fragment,container,false);

        setupRecyclerView();

        setupSearchView();

        return binding.getRoot();
    }


    /**
     * To setup the RecyclerViewSearch we create the necessary LayoutManager and PodcastListAdapter
     */
    private void setupRecyclerView(){

        RecyclerView recyclerViewSearch = binding.searchRecyclerView;

        recyclerViewSearch.setLayoutManager(createGridLayoutManager());

        recyclerViewSearch.setAdapter(createPodcastEpisodeListAdapter());
    }

    /**
     * To create a GridLayoutManager with spanCount = 1 and horizontal orientation
     * @return a GridLayoutManager object with spanCount = 1 and horizontal orientation
     */
    private GridLayoutManager createGridLayoutManager(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);

        return gridLayoutManager;
    }

    /**
     * To create a PodcastEpisodeListAdapterw with OnClickListener but without swipeListener
     * @return a PodcastEpisodeListAdapter without swipeListener
     */
    private PodcastListAdapter createPodcastEpisodeListAdapter(){
        adapter = new PodcastListAdapter(this,PodcastListAdapter.SUBSCRIBED);
        return adapter;
    }

    /**
     * To create and install the necessary listener in the SearchView to be able to
     * search podcasts
     */
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
                mViewModel.getQueryId().setValue(query);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupViewModel();

    }

    /**
     * To setup the PodcastViewModel, we create the PodcastViewModel and observe the query ID to
     * search for new podcasts
     */
    private void setupViewModel(){
        createPodcastViewModel();
        observeQueryId();
    }

    private void createPodcastViewModel(){
        mViewModel = new ViewModelProvider(requireActivity()).get(PodcastViewModel.class);
    }

    /**
     * To observe the queryId. When is changed we search for new podcasts and update the adapter
     */
    private void observeQueryId(){
        mViewModel.getQueryId().observe(getViewLifecycleOwner(), this::updateAdapterPodcasts);
    }

    /**
     * To update the Adapter with the result of the searching
     * @param query for searching
     */
    private void updateAdapterPodcasts(String query){
        adapter.setPodcasts(searchPodcasts(query));
    }

    /**
     * To search podcast with the new query
     * @param query for searching
     * @return podcast list according to the query
     */
    private List<Podcast> searchPodcasts(String query){
        return mViewModel.searchPodcasts(query);
    }

    // Open a PodcastFragment
    @Override
    public void onItemClick(int clickedItem) {


    }
}
