package com.example.podcasfy.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.podcasfy.api.Provider;
import com.example.podcasfy.model.Episode;
import com.example.podcasfy.repository.PodcastListRepository;
import com.example.podcasfy.model.Podcast;
import com.example.podcasfy.utils.EpisodeCallBack;
import com.example.podcasfy.utils.PodcastCallBack;

import java.util.List;

public class PodcastListViewModel extends ViewModel implements PodcastCallBack, EpisodeCallBack {

    private MutableLiveData<List<Podcast>> podcasts;
    private PodcastListRepository podcastRepository;

    private MutableLiveData<List<Podcast>> ivooxRecommended;
    private MutableLiveData<List<Podcast>> digitalRecommended;
    private MutableLiveData<List<Episode>> ivooxEpisode;
    private MutableLiveData<List<Episode>> digitalEpisode;

    public PodcastListViewModel (){

        podcastRepository = new PodcastListRepository(this, this);
        podcasts = podcastRepository.getPodcasts();
        ivooxRecommended = new MutableLiveData<>();
        digitalRecommended = new MutableLiveData<>();
        ivooxEpisode = new MutableLiveData<>();
        digitalEpisode = new MutableLiveData<>();
    }

    public MutableLiveData<List<Podcast>> getPodcasts() {
        return podcasts;
    }

    public MutableLiveData<List<Podcast>> getIvooxRecommended(){
        podcastRepository.getIvooxRecommended();
        return ivooxRecommended;
    }

    public MutableLiveData<List<Podcast>> getDigitalRecommended() {
        podcastRepository.getDigitalRecommended();
        return digitalRecommended;
    }

    public MutableLiveData<List<Episode>> getIvooxEpisodes(String podcastURL){
        podcastRepository.getIvooxEpisodes(podcastURL);
        return ivooxEpisode;
    }

    public MutableLiveData<List<Episode>> getDigitalEpisodes(String podcastURL){
        podcastRepository.getDigitialEpisodes(podcastURL);
        return digitalEpisode;
    }

    @Override
    public void updatePodcastList(List<Podcast> podcastList, String option) {
        if(option.equals("ivoox")){
            ivooxRecommended.setValue(podcastList);
        } else if(option.equals("digital")){
            digitalRecommended.setValue(podcastList);
        }
    }

    @Override
    public void updateEpisodeList(List<Episode> episodeList, String option) {

        if(option.equals(Provider.IVOOX)) {
            ivooxEpisode.setValue(episodeList);
        } else if(option.equals(Provider.DIGITAL)){
            digitalEpisode.setValue(episodeList);
        }
    }
}
