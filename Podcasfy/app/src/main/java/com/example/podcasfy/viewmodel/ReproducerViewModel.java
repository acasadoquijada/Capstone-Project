package com.example.podcasfy.viewmodel;

import android.app.Application;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.AudioFocusManager;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import java.util.Objects;

public class ReproducerViewModel extends AndroidViewModel implements ExoPlayer.EventListener {

    private static final String TAG = ReproducerViewModel.class.getSimpleName();
    private Application application;
    private MutableLiveData<String> name;
    private MutableLiveData<String> logoURL;
    private MutableLiveData<String> mediaURL;

    private ExoPlayer player;
    private long position;
    private MediaSource mediaSource;

    private MutableLiveData<Boolean> showReproducer;

    private MutableLiveData<Boolean> playerPlaying;

    public MutableLiveData<Boolean> getPlayerPlaying() {
        return playerPlaying;
    }

    public ReproducerViewModel(Application application){
        super(application);

        this.application = application;

        showReproducer = new MutableLiveData<>();
        showReproducer.setValue(false);

        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();

        player = ExoPlayerFactory.newSimpleInstance(this.application.getApplicationContext(),trackSelector,loadControl);

       // player.addListener(this);

        position = 0;

        playerPlaying = new MutableLiveData<>();
        name = new MutableLiveData<>();
        logoURL = new MutableLiveData<>();
        mediaURL = new MutableLiveData<>();

    }

    public ExoPlayer getPlayer() {
        return player;
    }

    public void storePlayerPosition(){
        position = player.getCurrentPosition();
    }

    public MutableLiveData<Boolean> getShowReproducer(){
        return showReproducer;
    }

    public void setShowReproducer(boolean show){
        showReproducer.setValue(show);
    }

    public void setPostion(long pos){
        this.position = pos;
    }

    public void setupAudio() {

        if(mediaURL.getValue() != null && !Objects.equals(mediaURL.getValue(), "")){

            if (position != 0) {
                player.prepare(mediaSource);
                player.seekTo(position);
            } else {
                mediaSource = buildMediaSource(mediaURL.getValue());
                player.prepare(mediaSource);
                player.seekTo(position);
            }
        }
    }

    public void stopPlayer(){
        if(player != null){
            player.stop();
        }
    }

    private MediaSource buildMediaSource(String url) {
        Uri uri = Uri.parse(url);
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(application.getApplicationContext(), "exoplayer-codelab");
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }

    public LiveData<String> getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public MutableLiveData<String> getImageURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL.setValue(logoURL);
    }

    public MutableLiveData<String> getMediaURL() {
        return mediaURL;
    }

    public void setMediaURL(String mediaURL) {
        this.mediaURL.setValue(mediaURL);
    }

    public void onPause(){
        storePlayerPosition();
        stopPlayer();
    }

    public void onResume(){
        if(player != null) {
            setupAudio();
        }
    }

    public void resetPosition(){
        position = 0;
    }
    // ExoPlayer Event Listeners


    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            playerPlaying.setValue(true);
            Log.d(TAG, "onPlayerStateChanged: PLAYING");
        } else if((playbackState == ExoPlayer.STATE_READY)){
            Log.d(TAG, "onPlayerStateChanged: PAUSED");
            playerPlaying.setValue(false);
        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
    }

}
