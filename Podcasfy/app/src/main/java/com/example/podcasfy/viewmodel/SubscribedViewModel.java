package com.example.podcasfy.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.podcasfy.model.Episode;
import com.example.podcasfy.model.Podcast;
import com.example.podcasfy.repository.PodcastListRepository;
import com.example.podcasfy.utils.EpisodeCallBack;
import com.example.podcasfy.utils.PodcastCallBack;

import java.util.List;

public class SubscribedViewModel extends AndroidViewModel implements PodcastCallBack, EpisodeCallBack {

    private LiveData<List<Podcast>> subscriptionList;
    private PodcastListRepository podcastListRepository;
    public SubscribedViewModel(@NonNull Application application) {
        super(application);
        podcastListRepository = new PodcastListRepository(this, this);
    }

    public LiveData<List<Podcast>> getSubscriptionList() {
        return podcastListRepository.getSubscriptionList();
    }

    @Override
    public void updatePodcastList(List<Podcast> podcastList, String option) {

    }

    @Override
    public void updateEpisodeList(List<Episode> episodeList, String option, String url) {

    }
}
