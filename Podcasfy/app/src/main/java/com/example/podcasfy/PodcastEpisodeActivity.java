package com.example.podcasfy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.podcasfy.utils.OnSwipeTouchListener;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class PodcastEpisodeActivity extends AppCompatActivity {

    private PlayerView playerView;
    private SimpleExoPlayer player;
    private LinearLayout ll;
    private TextView text;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast_episode);

        playerView = findViewById(R.id.mainMediaReproducer);
        ll = findViewById(R.id.my_test);
        text = findViewById(R.id.button_test);
        playerView.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeTop() {
                Toast.makeText(getApplicationContext(), "top", Toast.LENGTH_SHORT).show();
                text.setVisibility(View.VISIBLE);
            }
            public void onSwipeRight() {
                Toast.makeText(getApplicationContext(), "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Toast.makeText(getApplicationContext(), "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(getApplicationContext(), "bottom", Toast.LENGTH_SHORT).show();
                text.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(this);
        playerView.setPlayer(player);
        Uri uri = Uri.parse("https://storage.googleapis.com/exoplayer-test-media-0/Jazz_In_Paris.mp3");
        MediaSource mediaSource = buildMediaSource(uri);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare(mediaSource, false, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(this, "exoplayer-codelab");
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }


    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT < 24 || player == null)) {
            initializePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }

    }
}
