package com.example.podcasfy.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.podcasfy.model.Podcast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

@Singleton
public class PodcastListRepository {

    private  MutableLiveData<List<Podcast>> podcasts;
    private  MutableLiveData<List<String>> podcastsName;
    private  MutableLiveData<List<String>> podcastImage;

    public PodcastListRepository(){}

    public LiveData<List<Podcast>> getPodcasts(){

        if (podcasts != null) {
            return podcasts;
        }

        podcasts = new MutableLiveData<>();

        List<Podcast> podcastList = new ArrayList<>();

        Podcast p1 = new Podcast("Planos y Centellas","description","url",
                "https://static-2.ivoox.com/canales/3/8/0/0/2671546770083_MD.jpg","Ivoox");

        Podcast p2 = new Podcast("name2","description2","url",
                "https://static-2.ivoox.com/canales/3/8/0/0/2671546770083_MD.jpg","provider2");

        podcastList.add(p1);
        podcastList.add(p2);

        podcasts.setValue(podcastList);

        return podcasts;
    }

    public LiveData<List<String>> getPodcastsName(){

        if(podcastsName != null){
            return podcastsName;
        }

        podcastsName = new MutableLiveData<>();
        List<String> names = new ArrayList<>();

        Podcast p1 = new Podcast("Planos y Centellas ","description","url",
                "https://static-2.ivoox.com/canales/3/8/0/0/2671546770083_MD.jpg","ivoox");

        Podcast p2 = new Podcast("Amigos del Mapa","description2","url",
                "https://static-1.ivoox.com/canales/8/5/2/4/9071587544258_MD.jpg","ivoox");

        names.add("Planos y Centellas Planos y Centellas Planos y Centellas Planos y Centellas");
        names.add("Amigos del Mapa");
        names.add("Amigos del Mapa");
        names.add("Amigos del Mapa");
        names.add("Amigos del Mapa");

        podcastsName.setValue(names);

        return podcastsName;
    }

    public LiveData<List<String>> getPodcastsImage(){
        if(podcastImage != null){
            return podcastImage;
        }

        podcastImage = new MutableLiveData<>();
        List<String> images = new ArrayList<>();

        images.add("https://static-2.ivoox.com/canales/3/8/0/0/2671546770083_MD.jpg");
        images.add("https://static-1.ivoox.com/canales/8/5/2/4/9071587544258_MD.jpg");
        images.add("https://static-1.ivoox.com/canales/8/5/2/4/9071587544258_MD.jpg");
        images.add("https://static-1.ivoox.com/canales/8/5/2/4/9071587544258_MD.jpg");
        images.add("https://static-1.ivoox.com/canales/8/5/2/4/9071587544258_MD.jpg");

        podcastImage.setValue(images);

        return podcastImage;

    }
}
