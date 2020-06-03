package com.example.podcasfy.viewmodel;

import android.app.Application;
import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.podcasfy.model.Podcast;
import com.example.podcasfy.model.PodcastEpisode;
import com.example.podcasfy.repository.PodcastRepository;

import java.util.List;

public class PodcastViewModel extends ViewModel {

    private LiveData<Podcast> podcast;
    private LiveData<List<PodcastEpisode>> podcastEpisode;
    private String id;
    private PodcastRepository podcastRepository;

    public PodcastViewModel (){
        podcastRepository = new PodcastRepository();
    }

    public void setPodcastId(String id){
        this.id = id;
    }

    public LiveData<Podcast> getPodcast() {
        if(podcast != null){
            return  podcast;
        }
        podcast = podcastRepository.getPodcast(id); // here I set the ID
        return podcast;
    }

    public LiveData<List<PodcastEpisode>> getPodcastEpisode() {

        if(podcastEpisode != null){
            return podcastEpisode;
        }

        podcastEpisode = podcastRepository.getPodcastEpisode(id);
        return podcastEpisode;
    }
}