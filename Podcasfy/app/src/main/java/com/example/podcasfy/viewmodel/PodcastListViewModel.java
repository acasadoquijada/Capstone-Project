package com.example.podcasfy.viewmodel;

import android.util.Log;

import androidx.fragment.app.DialogFragment;
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

    private MutableLiveData<List<Podcast>> spainRecommended;
    private MutableLiveData<List<Podcast>> ukRecommended;
    private MutableLiveData<List<Episode>> spainPodcastEpisodeList;
    private MutableLiveData<List<Episode>> ukPodcastEpisodeList;

    public PodcastListViewModel (){

        podcastRepository = new PodcastListRepository(this, this);
        podcasts = podcastRepository.getPodcasts();

        spainPodcastEpisodeList = new MutableLiveData<>();
        ukPodcastEpisodeList = new MutableLiveData<>();
    }

    public MutableLiveData<List<Podcast>> getPodcasts() {
        return podcasts;
    }

    public MutableLiveData<List<Podcast>> getSpainRecommended(){

        if(spainRecommended == null){
            spainRecommended = new MutableLiveData<>();
            podcastRepository.getSpainRecommended();
        }

        return spainRecommended;
    }

    public MutableLiveData<List<Podcast>> getUKRecommended() {

        if(ukRecommended == null){
            ukRecommended = new MutableLiveData<>();
            podcastRepository.getUKRecommended();
        }

        return ukRecommended;
    }

    public MutableLiveData<List<Episode>> getSpainEpisodes(String podcastURL){
        podcastRepository.getSpainEpisodes(podcastURL);
        return spainPodcastEpisodeList;
    }

    public MutableLiveData<List<Episode>> getUKEpisodes(String podcastURL){
        podcastRepository.getUKEpisodes(podcastURL);
        return ukPodcastEpisodeList;
    }

    @Override
    public void updatePodcastList(List<Podcast> podcastList, String option) {
        if(option.equals(Provider.SPAIN)){
            spainRecommended.setValue(podcastList);
        } else if(option.equals(Provider.UK)){
            ukRecommended.setValue(podcastList);
        }
    }

    @Override
    public void updateEpisodeList(List<Episode> episodeList, String option) {
        if(option.equals(Provider.SPAIN)) {
            spainPodcastEpisodeList.setValue(episodeList);
        } else if(option.equals(Provider.UK)){
            ukPodcastEpisodeList.setValue(episodeList);
        }
    }
}
