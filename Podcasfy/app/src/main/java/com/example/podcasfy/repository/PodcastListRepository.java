package com.example.podcasfy.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.podcasfy.api.DigitalPodcast;
import com.example.podcasfy.api.Ivoox;
import com.example.podcasfy.model.Episode;
import com.example.podcasfy.model.Podcast;
import com.example.podcasfy.utils.EpisodeCallBack;
import com.example.podcasfy.utils.PodcastCallBack;
import com.example.podcasfy.utils.Provider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

@Singleton
public class PodcastListRepository {

    private  MutableLiveData<List<Podcast>> podcasts;
    private  MutableLiveData<List<Podcast>> subscriptionList;
    private  MutableLiveData<List<String>> podcastsName;
    private  MutableLiveData<List<String>> podcastImage;
    final private PodcastCallBack podcastCallBack;
    final private EpisodeCallBack episodeCallBack;
    private Ivoox ivoox;
    private DigitalPodcast digitalPodcast;
    private MutableLiveData<List<Podcast>> ivooxRecommended;


    public PodcastListRepository(PodcastCallBack podcastCallBack, EpisodeCallBack episodeCallBack){
        this.podcastCallBack = podcastCallBack;
        this.episodeCallBack = episodeCallBack;

        ivoox = new Ivoox();
        digitalPodcast = new DigitalPodcast();
        ivooxRecommended = new MutableLiveData<>();
    }

    public MutableLiveData<List<Podcast>> getSubscriptionList() {

        if(subscriptionList != null){
            return subscriptionList;
        }

        subscriptionList = new MutableLiveData<>();
        List<Podcast> podcastList = new ArrayList<>();

        Podcast p1 = new Podcast("Planos y Centellas","description","url",
                "https://static-2.ivoox.com/canales/3/8/0/0/2671546770083_MD.jpg","Ivoox");

        Podcast p2 = new Podcast("Amigos del Mapa","description2","url",
                "https://static-1.ivoox.com/canales/8/5/2/4/9071587544258_MD.jpg","digitalPodcast");

        podcastList.add(p1);
        podcastList.add(p2);
        podcastList.add(p2);

        subscriptionList.setValue(podcastList);

        return subscriptionList;
    }

    public MutableLiveData<List<Podcast>> getPodcasts(){

        if (podcasts != null) {
            return podcasts;
        }

        podcasts = new MutableLiveData<>();

        List<Podcast> podcastList = new ArrayList<>();

        Podcast p1 = new Podcast("Planos y Centellas","description","url",
                "https://static-2.ivoox.com/canales/3/8/0/0/2671546770083_MD.jpg","Ivoox");

        Podcast p2 = new Podcast("Amigos del Mapa","description2","url",
                "https://static-1.ivoox.com/canales/8/5/2/4/9071587544258_MD.jpg","provider2");
        Podcast p3 = new Podcast("Amigos del Mapa","description2","url",
                "https://static-1.ivoox.com/canales/8/5/2/4/9071587544258_MD.jpg","provider2");
        Podcast p4 = new Podcast("Amigos del Mapa","description2","url",
                "https://static-1.ivoox.com/canales/8/5/2/4/9071587544258_MD.jpg","provider2");
        Podcast p5 = new Podcast("Amigos del Mapa","description2","url",
                "https://static-1.ivoox.com/canales/8/5/2/4/9071587544258_MD.jpg","provider2");
        Podcast p6 = new Podcast("Amigos del Mapa","description2","url",
                "https://static-1.ivoox.com/canales/8/5/2/4/9071587544258_MD.jpg","provider2");

        podcastList.add(p1);
        podcastList.add(p2);
        podcastList.add(p3);
        podcastList.add(p4);
        podcastList.add(p5);
        podcastList.add(p6);

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

    public void testing(){
        new FetchPodcastListTask().execute("La Vida Moderna");
    }

    public void getIvooxRecommended(){
        new FetchPodcastListTask().execute("ivoox");
    }

    public void getDigitalRecommended(){
        new FetchPodcastListTask().execute("digital");
    }

    public void getIvooxEpisodes(String podcastURL){
        new FetchEpisodeListTask().execute(Provider.IVOOX,podcastURL);
    }

    public void getDigitialEpisodes(String podcastURL){
        new FetchEpisodeListTask().execute(Provider.DIGITAL,podcastURL);
    }

    class FetchPodcastListTask extends AsyncTask<String, Void, List<Podcast> > {

        private String argument;
        @Override
        protected List<Podcast> doInBackground(String... strings) {

            argument = strings[0];

            if(argument.equals("ivoox")){
                return ivoox.getRecommended();
            } else if(argument.equals("digital")){
                return digitalPodcast.getRecommended();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Podcast> podcastsList) {
            ivooxRecommended.setValue(podcastsList);
            podcastCallBack.updatePodcastList(podcastsList,argument);
        }
    }

    class FetchEpisodeListTask extends AsyncTask<String, Void, List<Episode> > {

        private String url;
        private String provider;

        @Override
        protected List<Episode> doInBackground(String... strings) {

            provider = strings[0];
            url = strings[1];

            if(provider.equals(Provider.IVOOX)){
                return ivoox.getEpisodes(url);
            } else if(provider.equals(Provider.DIGITAL)){
                Log.d("TEST","URL DIGITAL: " + url);
                return digitalPodcast.getEpisodes(url);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Episode> podcastsList) {
            episodeCallBack.updateEpisodeList(podcastsList, provider);
        }
    }

}