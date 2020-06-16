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
    private LiveData<List<String>> podcastNames;
    private LiveData<List<String>> podcastImages;
    private PodcastListRepository podcastRepository;

    public PodcastListViewModel (){

        podcastRepository = new PodcastListRepository(this);
        podcastNames = podcastRepository.getPodcastsName();
        podcastImages = podcastRepository.getPodcastsImage();
        podcasts = podcastRepository.getPodcasts();

    }

    public MutableLiveData<List<Podcast>> getPodcasts() {

        return podcasts;
    }

    public void testing(){
        podcastRepository.testing();
    }

    @Override
    public void updatePodcastList(List<Podcast> podcastList) {
        podcasts.setValue(podcastList);
    }
}
