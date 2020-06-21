package com.example.podcasfy.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import java.util.Objects;

public class ReproducerViewModel extends AndroidViewModel implements ExoPlayer.EventListener {

    private Application application;
    private MutableLiveData<String> name;
    private MutableLiveData<String> logoURL;
    private MutableLiveData<String> mediaURL;

    private ExoPlayer player;
    private long position;
    private MediaSource mediaSource;

    private MutableLiveData<Boolean> showReproducer;
    private MutableLiveData<Boolean> get;

    public ReproducerViewModel(Application application){
        super(application);

        this.application = application;

        showReproducer = new MutableLiveData<>();
        showReproducer.setValue(false);

        player =  ExoPlayerFactory.newSimpleInstance(this.application.getApplicationContext());

        position = 0;

        get = new MutableLiveData<>();
        name = new MutableLiveData<>();
        logoURL = new MutableLiveData<>();
        mediaURL = new MutableLiveData<>();

    }

    public MutableLiveData<Boolean> getGet() {
        return get;
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

    public void resumePlayer(){
        if(player != null) {
            setupAudio();
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

    public MutableLiveData<String> getLogoURL() {
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

}
