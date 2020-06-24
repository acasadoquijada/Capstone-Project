package com.example.podcasfy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.example.podcasfy.databinding.ActivityMainBinding;

import com.example.podcasfy.ui.SubscribedFragment;
import com.example.podcasfy.viewmodel.ReproducerViewModel;
import com.example.podcasfy.widget.UpdateSubscriptionsService;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.installations.FirebaseInstallationsRegistrar;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;

import ru.rambler.libs.swipe_layout.SwipeLayout;


public class MainActivity extends AppCompatActivity implements ExoPlayer.EventListener {

    private ActivityMainBinding mBinding;
    private ReproducerViewModel reproducerViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupDataBinding();

        setUpBottonNavigation();

        setupReproducerViewModel();

        setupMediaPlayer();

        setupSlidingUpPanel();

        setupSlidingPanelPlayerControl();

    }

    private void setupSlidingPanelPlayerControl(){

        mBinding.reproducer.reproducerSlidingPanel.slidingMediaReproducer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                reproducerViewModel.getPlayer().setPlayWhenReady(!isChecked);
            }
        });
    }

    private void setupDataBinding(){
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
    }

    private void setUpBottonNavigation(){
        NavigationUI.setupWithNavController(mBinding.bottomNavigation,
                Navigation.findNavController(this, R.id.nav_host_fragment));
    }

    /**
     * To create the ReproducerViewModel and observe the different fields in order to update
     * properly the reproducer.
     */

    private void setupReproducerViewModel(){

        createReproducerViewModel();

        observeEpisodeName();

        observeEpisodeImage();

        observeEpisodeMediaURL();

        observeShowReproducer();

        UpdateSubscriptionsService.startActionUpdateSubscriptions(this);
    }

    /**
     * To create the ReproducerViewModel
     */
    private void createReproducerViewModel(){
        reproducerViewModel = new ViewModelProvider(this).get(ReproducerViewModel.class);
    }

    private void observeShowReproducer(){
        reproducerViewModel.getShowReproducer().observe(this, aBoolean -> {
            if(aBoolean){
                startReproducer();
            } else {
                stopReproducer();
            }
        });
    }

    /**
     * To start the reproducer, enable and show the slidingPanel and update the slidingMedia
     * reproducer image.
     */
    private void startReproducer(){
        setPlayerPlayWhenReady(true);
        setSlidingLayoutTouch(true);
        setSlidingMediaReproducerImage(R.drawable.ic_pause);
        showSlidingPanel();
    }

    private void stopReproducer(){
        setPlayerPlayWhenReady(false);
        setSlidingLayoutTouch(false);
        setSlidingMediaReproducerImage(R.drawable.ic_play_arrow);
        hideSlidingPanel();
        stopPlayer();
    }

    private void setPlayerPlayWhenReady(boolean value){
        reproducerViewModel.getPlayer().setPlayWhenReady(value);
    }

    private void setSlidingLayoutTouch(boolean value){
        mBinding.slidingLayout.setTouchEnabled(value);
    }

    private void setSlidingMediaReproducerImage(int drawableId){
    //    mBinding.reproducer.reproducerSlidingPanel.slidingMediaReproducer.
        //        setImageDrawable(getDrawable(drawableId));
    }

    private void stopPlayer(){
        reproducerViewModel.stopPlayer();
    }

    /**
     * To observe and update the podcast name in the UI
     */
    private void observeEpisodeName(){
        reproducerViewModel.getName().observe(this, this::updateNames);
    }

    /**
     * To update the UI with the given podcast name
     * @param name of the podcast
     */
    private void updateNames(String name){
        setSlidingName(name);
        setMainEpisodeDescription(name);
    }

    private void setSlidingName(String name){
        mBinding.reproducer.reproducerSlidingPanel.slidingName.setText(name);
    }

    private void setMainEpisodeDescription(String name){
        mBinding.reproducer.mainEpsiodeDescription.setText(name);
    }

    private void observeEpisodeImage(){
        reproducerViewModel.getImageURL().observe(
                this, this::updateImages);
    }

    private void updateImages(String s){
        setSlidingEpisodeImage(s);
        setMainEpisodeImage(s);
    }

    private void setSlidingEpisodeImage(String s){
        Picasso.get().load(s).into(mBinding.reproducer.reproducerSlidingPanel.slingindEpisodeImage);
    }

    private void setMainEpisodeImage(String s){
        Picasso.get().load(s).into(mBinding.reproducer.mainEpisodeImage);
    }

    private void observeEpisodeMediaURL(){
        reproducerViewModel.getMediaURL().observe(this, s -> reproducerViewModel.setupAudio());
    }

    /**
     * To create the ExoPlayer, setPlayWhenReady and attach it to the corresponding PlayerView
     */

    private void setupMediaPlayer(){
        setExoPlayerInPlayerView();
    }

    private void setExoPlayerInPlayerView(){
        mBinding.reproducer.mainMediaReproducer.setPlayer(reproducerViewModel.getPlayer());
        reproducerViewModel.getPlayer().addListener(this);
    }

    /**
     * To setup the SlidingUpPanel, we add a Panel SlideListener that change the alpha value
     * of the sliding elements to have a smooth transition when the panel is slided.
     *
     */

    private void setupSlidingUpPanel(){
        setupPanelSlideListener();
        setupSlidingPanelSwipeLayout();
    }

    private void setupSlidingPanelSwipeLayout(){
        mBinding.reproducer.reproducerSlidingPanel.swipeLayout.setOnSwipeListener(new SwipeLayout.OnSwipeListener() {
            @Override
            public void onBeginSwipe(SwipeLayout swipeLayout, boolean moveToRight) {
                //nothing to do
            }

            @Override
            public void onSwipeClampReached(SwipeLayout swipeLayout, boolean moveToRight) {
                if(!moveToRight){
                    reproducerViewModelResetPosition();
                    setShowReproducerToFalse();
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

    private void reproducerViewModelResetPosition(){
        reproducerViewModel.resetPosition();
    }

    private void setShowReproducerToFalse(){
        reproducerViewModel.setShowReproducer(false);
    }

    private void setupPanelSlideListener(){

        mBinding.slidingLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
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
        mBinding.reproducer.reproducerSlidingPanel.slingindEpisodeImage.setAlpha(newAlphaValue);
    }

    /**
     * To change the alpha value of the slingindEpisodeImage
     */

    private void changeAlphaValueSlidingName(float newAlphaValue){
        mBinding.reproducer.reproducerSlidingPanel.slidingName.setAlpha(newAlphaValue);
    }

    /**
     * To change the alpha value of the slingindEpisodeImage
     */

    private void changeAlphaValueSlidingMediaReproducer(float newAlphaValue){
        mBinding.reproducer.reproducerSlidingPanel.slidingMediaReproducer.setAlpha(newAlphaValue);
    }

    private void hideSlidingPanel(){
        setSlidingPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }

    private void showSlidingPanel(){
        resetSwipeLayout();
        setSlidingPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    private void resetSwipeLayout(){
        mBinding.reproducer.reproducerSlidingPanel.swipeLayout.reset();
    }

    private void setSlidingPanelState(SlidingUpPanelLayout.PanelState state){
        mBinding.slidingLayout.setPanelState(state);
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

   @Override
    public void onLoadingChanged(boolean isLoading) {

    }

      @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            mBinding.reproducer.reproducerSlidingPanel.slidingMediaReproducer.setBackgroundDrawable(getDrawable(R.drawable.ic_pause));
        } else if((playbackState == ExoPlayer.STATE_READY)){
            mBinding.reproducer.reproducerSlidingPanel.slidingMediaReproducer.setBackgroundDrawable(getDrawable(R.drawable.ic_play_arrow));
        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }



    @Override
    public void onPause() {
        super.onPause();
        reproducerViewModel.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        reproducerViewModel.onResume();
    }

}
