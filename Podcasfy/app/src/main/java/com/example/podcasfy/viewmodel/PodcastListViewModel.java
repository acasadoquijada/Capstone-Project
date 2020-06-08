package com.example.podcasfy.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.podcasfy.repository.PodcastListRepository;
import com.example.podcasfy.model.Podcast;

import java.util.List;

import javax.inject.Inject;

public class PodcastListViewModel extends ViewModel {

    private LiveData<List<Podcast>> podcasts;
    private LiveData<List<String>> podcastNames;
    private LiveData<List<String>> podcastImages;
    private PodcastListRepository podcastRepository;

    @Inject
    public PodcastListViewModel (){
        podcastRepository = new PodcastListRepository();
        podcasts = podcastRepository.getPodcasts();
        podcastNames = podcastRepository.getPodcastsName();
        podcastImages = podcastRepository.getPodcastsImage();

    }

    public LiveData<List<Podcast>> getPodcasts() {



        return podcasts;
    }
    public LiveData<List<String>> getPodcastNames(){return podcastNames;}
    public LiveData<List<String>> getPodcastImages(){return podcastImages;}

}
