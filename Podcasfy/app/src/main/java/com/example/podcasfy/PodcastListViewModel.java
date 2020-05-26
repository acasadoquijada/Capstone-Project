package com.example.podcasfy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.podcasfy.model.Podcast;

import java.util.List;

import javax.inject.Inject;

public class PodcastViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private LiveData<List<Podcast>> podcasts;
    private PodcastRepository podcastRepository;

    @Inject
    public PodcastViewModel (){
        podcastRepository = new PodcastRepository();
        podcasts = podcastRepository.getPodcasts();
    }

    public LiveData<List<Podcast>> getPodcasts() {
        return podcasts;
    }
}
