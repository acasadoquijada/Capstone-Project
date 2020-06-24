package com.example.podcasfy.ui;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.podcasfy.adapter.PodcastListAdapter;
import com.example.podcasfy.api.Provider;
import com.example.podcasfy.databinding.PodcastListFragmentBinding;
import com.example.podcasfy.model.Podcast;
import com.example.podcasfy.viewmodel.PodcastListViewModel;
import com.example.podcasfy.R;
import com.google.android.gms.ads.AdRequest;

import java.util.List;


public class PodcastListFragment extends Fragment implements PodcastListAdapter.ItemClickListener  {

    private PodcastListViewModel mViewModel;

    private PodcastListAdapter mAdapterSubscriptions;
    private PodcastListAdapter mAdapterSpain;
    private PodcastListAdapter mAdapterUK;

    private PodcastListFragmentBinding binding;

    private ProgressDialog progDailog;

    private int visibleCount;
    public PodcastListFragment(){}

    /**
     * To inflate the view, we inflate the view using DataBinding, then we setup the different
     * RecyclerViews with its Adapters and LayoutManagers
     * @param inflater Layout inflater object
     * @param container ViewGroup
     * @param savedInstanceState bundle
     * @return the inflated view
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding =
                DataBindingUtil.inflate(inflater,R.layout.podcast_list_fragment, container, false);

        setupVisibleCount();

        setupProgressDialog();

        setupRecyclerViews();

        setupAdd();

        return binding.getRoot();

    }

    private void setupVisibleCount(){
        visibleCount = 2;
    }


    private void setupProgressDialog(){
        progDailog = new ProgressDialog(requireActivity());

        progDailog.setIndeterminate(true);
        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDailog.setCancelable(false);
        progDailog.show();

    }

    private void setupAdd(){
        loadAd(buildAdRequest());
    }

    private AdRequest buildAdRequest(){
        return new AdRequest.Builder()
                .build();
    }

    private void loadAd(AdRequest adRequest){
        binding.adView.loadAd(adRequest);
    }

    /**
     * To setup the RecyclerViews we create the necessary LayoutManager and PodcastListAdapter
     */
    private void setupRecyclerViews(){
        setupSubscriptions();
        setupSpain();
        setupUK();
    }

    /**
     * To setup the RecyclerView, LayoutManager and Adapter for the Subscriptions RecyclerView
     */
    private void setupSubscriptions(){
        RecyclerView recyclerViewSubscriptions = binding.podcastListSubscription;
        recyclerViewSubscriptions.setLayoutManager(createGridLayoutManager());
        mAdapterSubscriptions = createPodcastListAdapter(Provider.SUBSCRIBED);
        recyclerViewSubscriptions.setAdapter(mAdapterSubscriptions);
    }

    /**
     * To setup the RecyclerView, LayoutManager and Adapter for the Spain RecyclerView
     */
    private void setupSpain(){
        RecyclerView recyclerViewSpain =  binding.podcastListIvoox;
        recyclerViewSpain.setLayoutManager(createGridLayoutManager());
        mAdapterSpain = createPodcastListAdapter(Provider.SPAIN);
        recyclerViewSpain.setAdapter(mAdapterSpain);
    }

    /**
     * To setup the RecyclerView, LayoutManager and Adapter for the UK RecyclerView
     */
    private void setupUK(){
        RecyclerView recyclerViewUK = binding.podcastListDigital;
        recyclerViewUK.setLayoutManager(createGridLayoutManager());
        mAdapterUK = createPodcastListAdapter(Provider.UK);
        recyclerViewUK.setAdapter(mAdapterUK);
    }

    /**
     * To create a GridLayoutManager with spanCount = 1 and horizontal orientation
     * @return a GridLayoutManager object with spanCount = 1 and horizontal orientation
     */
    private GridLayoutManager createGridLayoutManager(){

        int spanCount = 1;

        GridLayoutManager manager = new GridLayoutManager(getContext(), spanCount);

        manager.setOrientation(RecyclerView.HORIZONTAL);

        return manager;
    }

    private PodcastListAdapter createPodcastListAdapter(String provider){
        return new PodcastListAdapter(this, provider);
    }

    /**
     * To create the ViewModel and setup the observer, we observe the changes in the Podcasts field
     * and update the podcastListAdapter with the new list of podcasts
     * @param savedInstanceState bundle
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewModel();
    }

    /**
     * To create the ViewModel and observe the different PodcastList
     */
    private void setupViewModel(){
        createViewModel();
        observePodcastLists();
    }

    private void createViewModel(){
        mViewModel = new ViewModelProvider(requireActivity()).get(PodcastListViewModel.class);
    }

    private void observePodcastLists(){
        observeSubscribed();
        observeSpain();
        observeUK();
    }

    private void observeSubscribed(){
        mViewModel.getSubscribedPodcastList().observe(getViewLifecycleOwner(),
                this::updateSubscribedPodcastList);
    }

    private void observeSpain(){
        mViewModel.getSpainRecommendedPodcastList().observe(getViewLifecycleOwner(),
                this::updateSpainPodcastList);

    }

    private void observeUK(){
        mViewModel.getUKRecommended().observe(getViewLifecycleOwner(),
                this::updateUKPodcastList);

    }

    private void updateSubscribedPodcastList(List<Podcast> podcastList){
        mAdapterSubscriptions.setPodcasts(podcastList);

    }

    private void updateSpainPodcastList(List<Podcast> podcastList){
        mAdapterSpain.setPodcasts(podcastList);
        decreaseVisibleCount();
    }

    private void updateUKPodcastList(List<Podcast> podcastList){
        mAdapterUK.setPodcasts(podcastList);
        decreaseVisibleCount();
    }

    private void decreaseVisibleCount(){

        visibleCount--;
        Log.d("VISIBLE__",""+ visibleCount);
        if(visibleCount == 0){
            setUIVisible();
        }
    }

    private void setUIVisible(){
        setLinearLayoutVisible();
        dismissProgressDialog();
    }

    private void setLinearLayoutVisible(){
        binding.linearLayout.setVisibility(View.VISIBLE);
    }

    private void dismissProgressDialog(){
        progDailog.dismiss();
    }


    /**
     * To launch a PodcastFragment, we open a PodcastFragment with the id of the Podcast selected
     * using the NavigationComponent
     * @param clickedItem index of the selected Podcast
     */

    @Override
    public void onItemClick(int clickedItem, String provider) {
        NavDirections action =
                PodcastListFragmentDirections.actionPodcastListFragmentToPodcastFragment(clickedItem,provider);
        NavHostFragment.findNavController(this).navigate(action);
    }
}
