package com.example.podcasfy.ui;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.podcasfy.R;
import com.example.podcasfy.databinding.MainFragmentBinding;
import com.example.podcasfy.viewmodel.ReproducerViewModel;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import ru.rambler.libs.swipe_layout.SwipeLayout;

public class MainFragment extends Fragment {

    private MainFragmentBinding binding;
    private ReproducerViewModel reproducerViewModel;
    private ExoPlayer player;
    private ViewHandler mHandler;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mHandler = new ViewHandler();

        binding = DataBindingUtil.inflate(inflater,R.layout.main_fragment,container,false);

        setupSlidingUpPanel();

        setupMediaPlayer();

        return binding.getRoot();
    }

    /**
     * To setup the SlidingUpPanel, we add a Panel SlideListener that change the alpha value
     * of the sliding elements to have a smooth transition when the panel is slided.
     *
     */

    private void setupSlidingUpPanel(){

        hideSlidingPanel();

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
                //   Toast.makeText(requireContext(),"onBeginSwipe",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeClampReached(SwipeLayout swipeLayout, boolean moveToRight) {

                if(!moveToRight){
                    hideSlidingPanel();
                }
            }

            @Override
            public void onLeftStickyEdge(SwipeLayout swipeLayout, boolean moveToRight) {
                Toast.makeText(requireContext(),"onLeftStickyEdge",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onRightStickyEdge(SwipeLayout swipeLayout, boolean moveToRight) {
                Toast.makeText(requireContext(),"onRightStickyEdge",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hideSlidingPanel(){
        binding.slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
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
        binding.reproducer.reproducerSlidingPanel.slidingName.setText(name);

        // These methods are here for testing purposes. Will be removed eventually
        binding.slidingLayout.setTouchEnabled(true);
        // showSlidingPanel();
        mHandler.sendMessage(Message.obtain(mHandler, 0));
        // MediaSource mediaSource = buildMediaSource();

        // Prepare ExoPlayer
        // player.prepare(mediaSource,false,false);
        //
    }

    private MediaSource buildMediaSource() {
        String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4";
        Uri uri = Uri.parse(url);
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(requireContext(), "exoplayer-codelab");
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }

    private class ViewHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            showSlidingPanel();
        }
    }
}