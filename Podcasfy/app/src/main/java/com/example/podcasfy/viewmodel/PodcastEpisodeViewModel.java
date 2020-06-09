package com.example.podcasfy.viewmodel;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

public class PodcastEpisodeViewModel extends AndroidViewModel {

    private MutableLiveData<ExoPlayer> exoPlayer;
    private String uri;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private Context context;

    public PodcastEpisodeViewModel(@NonNull Application application) {
        super(application);

        Log.d("BIXO__","CREATE");
        context = application.getApplicationContext();
        exoPlayer = new MutableLiveData<>();
        ExoPlayer player = ExoPlayerFactory.newSimpleInstance(context);
        exoPlayer.setValue(player);
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(context, "exoplayer-codelab");
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public LiveData<ExoPlayer> getExoPlayer(String media) {

            ExoPlayer player = ExoPlayerFactory.newSimpleInstance(context);
            exoPlayer.setValue(player);

            Uri uri = Uri.parse(media);
            MediaSource mediaSource = buildMediaSource(uri);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
            player.prepare(mediaSource, false, false);

            return exoPlayer;
    }
}
