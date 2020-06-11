package com.example.podcasfy.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.podcasfy.model.Podcast;
import com.example.podcasfy.repository.PodcastListRepository;

import java.util.List;

public class SubscribedViewModel extends AndroidViewModel {

    private LiveData<List<Podcast>> subscriptionList;
    private PodcastListRepository podcastListRepository;
    public SubscribedViewModel(@NonNull Application application) {
        super(application);
        podcastListRepository = new PodcastListRepository();
    }

    public LiveData<List<Podcast>> getSubscriptionList() {
        return podcastListRepository.getSubscriptionList();
    }
}
