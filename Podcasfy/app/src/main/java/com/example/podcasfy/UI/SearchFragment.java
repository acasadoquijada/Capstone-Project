package com.example.podcasfy.UI;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.podcasfy.R;
import com.example.podcasfy.adapter.PodcastListAdapter;
import com.example.podcasfy.viewmodel.PodcastViewModel;



public class SearchFragment extends Fragment implements PodcastListAdapter.ItemClickListener {

    private PodcastViewModel mViewModel;

    private PodcastListAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.search_fragment, container, false);

        // Adapter, RecyclerView and LayoutManager

        adapter = new PodcastListAdapter(this);
        RecyclerView recyclerView = rootView.findViewById(R.id.searchRecyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        // Setup searchView interaction
        SearchView searchView = rootView.findViewById(R.id.searchView);

        // Click anywhere will open the keyboard
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.searchView) {
                    searchView.onActionViewExpanded();
                }
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

        mViewModel = new ViewModelProvider(getActivity()).get(PodcastViewModel.class);

        mViewModel.getQueryId().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                adapter.setPodcasts(mViewModel.searchPodcasts(s));
            }
        });
    }

    // Open a PodcastFragment
    @Override
    public void onItemClick(int clickedItem) {
        NavDirections action =
                SearchFragmentDirections
                        .actionSearchFragmentToPodcastFragment("id");
        Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(action);

    }
}
