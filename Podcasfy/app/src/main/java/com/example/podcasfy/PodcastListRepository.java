package com.example.podcasfy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.podcasfy.model.AppDataBase;
import com.example.podcasfy.model.Podcast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Cache;

@Singleton
public class PodcastListRepository {

    private  MutableLiveData<List<Podcast>> podcasts;

    PodcastListRepository(){}

    public LiveData<List<Podcast>> getPodcasts(){

        if (podcasts != null) {
            return podcasts;
        }

        podcasts = new MutableLiveData<>();

        List<Podcast> podcastList = new ArrayList<>();

        Podcast p1 = new Podcast("NOMBRE LARGO QUE TE CAGAS LAS PATAS ABAJO","description","url",
                "https://static-2.ivoox.com/canales/3/8/0/0/2671546770083_MD.jpg","provider");

        Podcast p2 = new Podcast("name2","description2","url",
                "https://static-2.ivoox.com/canales/3/8/0/0/2671546770083_MD.jpg","provider2");

        podcastList.add(p1);
        podcastList.add(p2);

        podcasts.setValue(podcastList);

        return podcasts;
    }
}
