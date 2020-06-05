package com.example.podcasfy.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.podcasfy.model.Podcast;
import com.example.podcasfy.model.PodcastEpisode;
import com.example.podcasfy.repository.PodcastRepository;

import java.util.List;

public class PodcastViewModel extends ViewModel {

    private LiveData<Podcast> podcast;
    private LiveData<List<Podcast>> podcasts;
    private LiveData<List<PodcastEpisode>> podcastEpisode;
    private String id;
    private MutableLiveData<String> queryId;
    private PodcastRepository podcastRepository;
    private List<Podcast> subs;

    public PodcastViewModel (){
        queryId = new MutableLiveData<>();
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

    public List<Podcast> searchPodcasts(String query) {

        subs = podcastRepository.searchPodcasts(query);

        return subs;
    }


    public MutableLiveData<String> getQueryId() {
        return queryId;
    }


    public LiveData<List<PodcastEpisode>> getDonwloadedEpisodes(){

        if(podcastEpisode != null){
            return podcastEpisode;
        }

        podcastEpisode = podcastRepository.getDownloadedEspisodes();
        return podcastEpisode;
    }
}
