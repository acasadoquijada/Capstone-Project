package com.example.podcasfy.ui;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.podcasfy.R;
import com.example.podcasfy.databinding.MainFragmentBinding;
import com.example.podcasfy.viewmodel.ReproducerViewModel;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;

import ru.rambler.libs.swipe_layout.SwipeLayout;

public class MainFragment extends Fragment {

    private MainFragmentBinding binding;
    private ReproducerViewModel reproducerViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.main_fragment,container,false);

        setupSlidingUpPanel();

        return binding.getRoot();
    }

    /**
     * To setup the SlidingUpPanel, we add a Panel SlideListener that change the alpha value
     * of the sliding elements to have a smooth transition when the panel is slided.
     *
     */

    private void setupSlidingUpPanel(){

      //  hideSlidingPanel();

        setupPanelSlideListener();

        setupSlidingPanelSwipeLayout();
    }

    private void setupPanelSlideListener(){

        binding.slidingLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                changeAlphaSlidingElements(slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState,
                                            SlidingUpPanelLayout.PanelState newState) {
                // We don't want to change this behavior. But it needs to be override
            }
        });
    }

    private void setupSlidingPanelSwipeLayout(){
        binding.reproducer.reproducerSlidingPanel.swipeLayout.setOnSwipeListener(new SwipeLayout.OnSwipeListener() {
            @Override
            public void onBeginSwipe(SwipeLayout swipeLayout, boolean moveToRight) {
                //nothing to do
            }

            @Override
            public void onSwipeClampReached(SwipeLayout swipeLayout, boolean moveToRight) {
                if(!moveToRight){
                    reproducerViewModel.stopPlayer();
                    reproducerViewModel.setPostion(0);
                    reproducerViewModel.setShowReproducer(false);
                }
            }

            @Override
            public void onLeftStickyEdge(SwipeLayout swipeLayout, boolean moveToRight) {
                //nothing to do
            }

            @Override
            public void onRightStickyEdge(SwipeLayout swipeLayout, boolean moveToRight) {
                //nothing to do
            }
        });
    }

    private void hideSlidingPanel(){
        binding.slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
       // reproducerViewModel.setShowReproducer(false);
    }

    private void showSlidingPanel(){
        binding.reproducer.reproducerSlidingPanel.swipeLayout.reset();
        binding.slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    /**
     * To change the alpha value of all the elements of the sliding part of the panel
     * @param slideOffset value to set as alpha
     */
    private void changeAlphaSlidingElements(float slideOffset){

        final float newAlphaValue = calculateNewAlphaValue(slideOffset);

        changeAlphaValueSlingindEpisodeImage(newAlphaValue);
        changeAlphaValueSlidingName(newAlphaValue);
        changeAlphaValueSlidingMediaReproducer(newAlphaValue);
    }

    /**
     * To calculate the new alpha value to guarantees a smooth transition when the panel is slided
     * @param slideOffset needed for the calculation
     * @return the new alpha value
     */
    private float calculateNewAlphaValue(float slideOffset){
        return 1-(6*slideOffset);
    }

    /**
     * To change the alpha value of the slingindEpisodeImage
     */

    private void changeAlphaValueSlingindEpisodeImage(float newAlphaValue){
        binding.reproducer.reproducerSlidingPanel.slingindEpisodeImage.setAlpha(newAlphaValue);
    }

    /**
     * To change the alpha value of the slingindEpisodeImage
     */

    private void changeAlphaValueSlidingName(float newAlphaValue){
        binding.reproducer.reproducerSlidingPanel.slidingName.setAlpha(newAlphaValue);
    }

    /**
     * To change the alpha value of the slingindEpisodeImage
     */

    private void changeAlphaValueSlidingMediaReproducer(float newAlphaValue){
        binding.reproducer.reproducerSlidingPanel.slidingMediaReproducer.setAlpha(newAlphaValue);
    }

    /**
     * To create the ExoPlayer, setPlayWhenReady and attach it to the corresponding PlayerView
     */

    private void setupMediaPlayer(){
        setExoPlayerInPlayerView();
    }


    private void setExoPlayerInPlayerView(){
        if(reproducerViewModel.getPlayer() == null){
            Log.d("EXOPLAYER_", "NULL");
        }
        binding.reproducer.mainMediaReproducer.setPlayer(reproducerViewModel.getPlayer());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupReproducerViewModel();
        setupMediaPlayer();
    }
    /**
     * To create the ReproducerViewModel and observe podcastName to update the UI.
     */

    private void setupReproducerViewModel(){
        createReproducerViewModel();
        observeEpisodetName();
        observeEpisodeLogo();
        observeEpisodeMediaURL();
        //observePlay();

        observeShowReproducer();

    }

    /**
     * To create the ReproducerViewModel
     */
    private void createReproducerViewModel(){
        reproducerViewModel = new ViewModelProvider(requireActivity()).get(ReproducerViewModel.class);
    }

    private void observeShowReproducer(){
        reproducerViewModel.getShowReproducer().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){

                    Log.d("TESTING", "I SHOULD REPRODUCE!");
                    //show
                    reproducerViewModel.getPlayer().setPlayWhenReady(true);
                    binding.slidingLayout.setTouchEnabled(true);
                    binding.reproducer.reproducerSlidingPanel.slidingMediaReproducer.setImageDrawable(requireContext().getDrawable(R.drawable.ic_pause));
                    showSlidingPanel();
                } else {
                    //don't
                    Log.d("TESTING", "I SHOULD NOT REPRODUCE!");

                    binding.slidingLayout.setTouchEnabled(false);
                    reproducerViewModel.getPlayer().setPlayWhenReady(false);
                    binding.reproducer.reproducerSlidingPanel.slidingMediaReproducer.setImageDrawable(requireContext().getDrawable(R.drawable.ic_play_arrow));
                    hideSlidingPanel();

                }
            }
        });
    }

    /**
     * To observe and update the podcast name in the UI
     */
    private void observeEpisodetName(){
        reproducerViewModel.getName().observe(
                getViewLifecycleOwner(), this::updateSlidingUIName);
    }

    /**
     * To observe and update the podcast name in the UI
     */
    private void observeEpisodeLogo(){
        reproducerViewModel.getLogoURL().observe(
                getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        Picasso.get().load(s).into(binding.reproducer.reproducerSlidingPanel.slingindEpisodeImage);
                        Picasso.get().load(s).into(binding.reproducer.mainEpisodeImage);
                    }
                });
    }

    private void observeEpisodeMediaURL(){
        reproducerViewModel.getMediaURL().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                reproducerViewModel.setupAudio();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        reproducerViewModel.storePlayerPosition();
        reproducerViewModel.stopPlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        reproducerViewModel.resumePlayer();
    }

    /**
     * To update the UI with the given podcast name
     * @param name of the podcast
     */
    private void updateSlidingUIName(String name){
        binding.reproducer.reproducerSlidingPanel.slidingName.setText(name);
        binding.reproducer.mainEpsiodeDescription.setText(name);

        showSlidingPanel();
    }

}