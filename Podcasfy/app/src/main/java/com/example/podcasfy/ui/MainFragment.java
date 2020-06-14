package com.example.podcasfy.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.podcasfy.R;
import com.example.podcasfy.databinding.MainFragmentBinding;
import com.example.podcasfy.viewmodel.ReproducerViewModel;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class MainFragment extends Fragment {

    private MainFragmentBinding binding;
    private ReproducerViewModel reproducerViewModel;
    private ExoPlayer player;
    private int panelHeight;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.main_fragment,container,false);

        setupSlidingUpPanel();

        setupMediaPlayer();

       return binding.getRoot();
    }

    private void setupPanelHeight(){
        panelHeight = binding.slidingLayout.getPanelHeight();
    }

    /**
     * To setup the SlidingUpPanel, we add a Panel SlideListener that change the alpha value
     * of the sliding elements to have a smooth transition when the panel is slided.
     *
     */
    private void setupSlidingUpPanel(){

        setupPanelHeight();

        hideSlidingPanel();

        disableSlidingPanelTouchResponsiveness();

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


    private void hideSlidingPanel(){
        changeVisibilitySlidingPanel(View.GONE);
        changePanelHeight(0);
        changeShadowHeight(0);
    }


    private void showSlidingPanel(){
        changeVisibilitySlidingPanel(View.VISIBLE);
        changePanelHeight(panelHeight);
        changeShadowHeight(4);
    }

    private void changePanelHeight(int panelHeight){
        binding.slidingLayout.setPanelHeight(panelHeight);
    }

    private void changeShadowHeight(int shadowHeight){
        binding.slidingLayout.setShadowHeight(shadowHeight);
    }

    private void changeVisibilitySlidingPanel(int visibility){
        changeVisibilitySlidingEpisodeImage(visibility);
        changeVisibilitySlidingName(visibility);
        changeVisibilitySlidingMediaReproducer(visibility);
    }

    private void changeVisibilitySlidingEpisodeImage(int visibility){
        binding.reproducer.slingindEpisodeImage.setVisibility(visibility);
    }


    private void changeVisibilitySlidingName(int visibility){
        binding.reproducer.slidingName.setVisibility(visibility);
    }

    private void changeVisibilitySlidingMediaReproducer(int visibility) {
        binding.reproducer.slidingMediaReproducer.setVisibility(visibility);
    }

    private void disableSlidingPanelTouchResponsiveness(){
        binding.slidingLayout.setTouchEnabled(false);
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
        binding.reproducer.slingindEpisodeImage.setAlpha(newAlphaValue);
    }

    /**
     * To change the alpha value of the slingindEpisodeImage
     */

    private void changeAlphaValueSlidingName(float newAlphaValue){
        binding.reproducer.slidingName.setAlpha(newAlphaValue);
    }

    /**
     * To change the alpha value of the slingindEpisodeImage
     */

    private void changeAlphaValueSlidingMediaReproducer(float newAlphaValue){
        binding.reproducer.slidingMediaReproducer.setAlpha(newAlphaValue);
    }

    /**
     * To create the ExoPlayer, setPlayWhenReady and attach it to the corresponding PlayerView
     */

    private void setupMediaPlayer(){
        createExoPlayer();
        setPlayWhenReady();
        setExoPlayerInPlayerView();
    }

    private void createExoPlayer(){
        player =  ExoPlayerFactory.newSimpleInstance(requireContext());
    }

    private void setPlayWhenReady(){
        player.setPlayWhenReady(true);
    }

    private void setExoPlayerInPlayerView(){
        binding.reproducer.mainMediaReproducer.setPlayer(player);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupReproducerViewModel();

    }
    /**
     * To create the ReproducerViewModel and observe podcastName to update the UI.
     */

    private void setupReproducerViewModel(){
        createReproducerViewModel();
        observePodcastName();
    }

    /**
     * To create the ReproducerViewModel
     */
    private void createReproducerViewModel(){
        reproducerViewModel = new ViewModelProvider(requireActivity()).get(ReproducerViewModel.class);
    }

    /**
     * To observe and update the podcast name in the UI
     */
    private void observePodcastName(){
        reproducerViewModel.getName().observe(
                getViewLifecycleOwner(), this::updateSlidingUIName);
    }

    /**
     * To update the UI with the given podcast name
     * @param name of the podcast
     */
    private void updateSlidingUIName(String name){
        binding.reproducer.slidingName.setText(name);

        // These methods are here for testing purposes. Will be removed eventually
        binding.slidingLayout.setTouchEnabled(true);
        showSlidingPanel();
    }
}
