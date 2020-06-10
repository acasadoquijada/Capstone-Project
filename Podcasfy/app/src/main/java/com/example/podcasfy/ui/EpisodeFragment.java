package com.example.podcasfy.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.podcasfy.R;
import com.example.podcasfy.viewmodel.EpisodeViewModel;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class EpisodeFragment extends Fragment {

    private static final String PLAYER_CURRENT_POS_KEY = "player_position";
    private static final String PLAYER_IS_READY_KEY = "player_ready";
    private static final String PLAYER_CURRENT_WINDOW = "player_window";
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private LinearLayout ll;
    private TextView text;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    private EpisodeViewModel mViewModel;

    private ItemClickListener mCallback;


    public interface ItemClickListener {
        void onItemClick(int clickedItem);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.episode_fragment,container,false);

        TextView t = root.findViewById(R.id.test);
        Button b = root.findViewById(R.id.test_button);
        SlidingUpPanelLayout layout = root.findViewById(R.id.sliding_layout);



        b.setOnClickListener(v -> mCallback.onItemClick(2));
    //    playerView = root.findViewById(R.id.video_view);

      //  initializePlayer();




        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mCallback = (ItemClickListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(
                    context.toString() + "must implement onGridElementClick interface");
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
/*
        mViewModel = new EpisodeViewModel(requireActivity().getApplication());

        mViewModel.getExoPlayer("https://storage.googleapis.com/exoplayer-test-media-0/Jazz_In_Paris.mp3").
                observe(getViewLifecycleOwner(), exoPlayer -> playerView.setPlayer(exoPlayer));*/
    }

    private void initializePlayer() {
        playerView.setPlayer(player);
    }


    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(requireContext(), "exoplayer-codelab");
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }

    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playerView.getPlayer().release();
            player = null;
        }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public void onResume() {
        super.onResume();
     //   requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }


     //   requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong(PLAYER_CURRENT_POS_KEY, playbackPosition);
        outState.putBoolean(PLAYER_IS_READY_KEY, playWhenReady);

    }
}