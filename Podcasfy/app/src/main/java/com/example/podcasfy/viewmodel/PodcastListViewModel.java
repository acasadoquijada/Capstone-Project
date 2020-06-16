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
    public PodcastListViewModel (){

        podcastRepository = new PodcastListRepository(this);
        podcasts = podcastRepository.getPodcasts();
        ivooxRecommended = new MutableLiveData<>();

    }

    public MutableLiveData<List<Podcast>> getPodcasts() {

        return podcasts;
    }

    public void testing(){
        podcastRepository.testing();
    }

    public MutableLiveData<List<Podcast>> getIvooxRecommended(){
        podcastRepository.getIvooxRecommended();
        return ivooxRecommended;
    }

    @Override
    public void updatePodcastList(List<Podcast> podcastList, String option) {
        if(option.equals("ivoox")){
            ivooxRecommended.setValue(podcastList);
        }
    }
}
