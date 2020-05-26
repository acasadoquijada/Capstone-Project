package com.example.podcasfy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.podcasfy.model.Podcast;

import java.util.List;

import javax.inject.Inject;

public class PodcastListViewModel extends ViewModel {

    private LiveData<List<Podcast>> podcasts;
    private PodcastListRepository podcastRepository;

    @Inject
    public PodcastListViewModel (){
        podcastRepository = new PodcastListRepository();
        podcasts = podcastRepository.getPodcasts();
    }

    public LiveData<List<Podcast>> getPodcasts() {
        return podcasts;
    }
}
