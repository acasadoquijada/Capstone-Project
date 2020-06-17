package com.example.podcasfy.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.podcasfy.repository.PodcastListRepository;
import com.example.podcasfy.model.Podcast;
import com.example.podcasfy.utils.PodcastCallBack;

import java.util.List;

public class PodcastListViewModel extends ViewModel implements PodcastCallBack {

    private MutableLiveData<List<Podcast>> podcasts;
    private PodcastListRepository podcastRepository;

    private MutableLiveData<List<Podcast>> ivooxRecommended;
    private MutableLiveData<List<Podcast>> digitalRecommended;

    public PodcastListViewModel (){

        podcastRepository = new PodcastListRepository(this);
        podcasts = podcastRepository.getPodcasts();
        ivooxRecommended = new MutableLiveData<>();
        digitalRecommended = new MutableLiveData<>();
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

    @Override
    public void updatePodcastList(List<Podcast> podcastList, String option) {
        if(option.equals("ivoox")){
            ivooxRecommended.setValue(podcastList);
        } else if(option.equals("digital")){
            digitalRecommended.setValue(podcastList);
        }
    }
}
