package com.example.podcasfy.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.podcasfy.api.Provider;
import com.example.podcasfy.model.Episode;
import com.example.podcasfy.repository.PodcastListRepository;
import com.example.podcasfy.model.Podcast;
import com.example.podcasfy.utils.PodcastCallBack;

import java.util.List;
import java.util.Objects;

public class PodcastListViewModel extends AndroidViewModel implements PodcastCallBack {

    private PodcastListRepository podcastRepository;

    // Podcast per each provider + subscriptions + search
    private MutableLiveData<List<Podcast>> spainRecommendedPodcastList;
    private MutableLiveData<List<Podcast>> ukRecommendedPodcastList;
    private MutableLiveData<List<Podcast>> subscribedPodcastList;
    private MutableLiveData<List<Podcast>> searchedPodcast;

    // Episodes per given Podcast
    private MutableLiveData<List<Episode>> episodeList;

    // Downloaded episodes
    private MutableLiveData<List<Episode>> episodesDownloadedList;

    // Used to triggered a search when the user sets a query
    private MutableLiveData<String> searchQuery;


    public PodcastListViewModel (Application application){
        super(application);

        podcastRepository = new PodcastListRepository(application.getApplicationContext(),this);

        episodeList = new MutableLiveData<>();
        searchQuery = new MutableLiveData<>();
        searchedPodcast = new MutableLiveData<>();
    }


    public MutableLiveData<List<Podcast>> getSubscribedPodcastList() {

        if(subscribedPodcastList == null){
            subscribedPodcastList = podcastRepository.getSubscribedPodcastList();
        }

        return subscribedPodcastList;
    }

    public MutableLiveData<List<Podcast>> getSpainRecommendedPodcastList(){

        if(spainRecommendedPodcastList == null){
            spainRecommendedPodcastList = podcastRepository.getSpainRecommendedPodcastList();
        }
        return spainRecommendedPodcastList;
    }

    public MutableLiveData<List<Podcast>> getUKRecommended() {

        if(ukRecommendedPodcastList == null){
            ukRecommendedPodcastList = podcastRepository.getUKRecommended();
        }
        return ukRecommendedPodcastList;
    }

    public void searchPodcast(){
        podcastRepository.searchPodcast(searchQuery.getValue());
    }

    public MutableLiveData<List<Podcast>> getSearchedPodcastList(){

        searchedPodcast = podcastRepository.getSearchedEpisodeList();

        return podcastRepository.getSearchedEpisodeList();
    }

    public Podcast getPodcast(String provider, int pos){

        switch (provider) {
            case Provider.SPAIN:
                return spainRecommendedPodcastList.getValue().get(pos);
            case Provider.UK:
                return ukRecommendedPodcastList.getValue().get(pos);
            case Provider.SUBSCRIBED:
                return subscribedPodcastList.getValue().get(pos);
            case Provider.SEARCH:
                return searchedPodcast.getValue().get(pos);
            default:
                return null;
        }

    }

    public MutableLiveData<List<Episode>> getEpisodeList(String provider, String podcastURL){
        switch (provider) {
            case Provider.SPAIN:
                return getSpainEpisodes(podcastURL);
            case Provider.UK:
                return getUKEpisodes(podcastURL);
            case Provider.SUBSCRIBED:
            case Provider.SEARCH:
                return getSubscribedEpisodes(podcastURL);
            default:
                return null;
        }
    }

    public MutableLiveData<List<Episode>> getEpisodeList() {
        return episodeList;
    }

    private MutableLiveData<List<Episode>> getSpainEpisodes(String podcastURL){

        episodeList = podcastRepository.getEpisodeList(Provider.SPAIN, podcastURL);

        return episodeList;

    }

    private MutableLiveData<List<Episode>> getUKEpisodes(String podcastURL){
        episodeList = podcastRepository.getEpisodeList(Provider.UK, podcastURL);
        return episodeList;
    }

    private MutableLiveData<List<Episode>> getSubscribedEpisodes(String podcastURL){
        episodeList = podcastRepository.getEpisodeList(Provider.SUBSCRIBED, podcastURL);
        return episodeList;
    }

    public MutableLiveData<List<Episode>> getDownloadedEspisodes(){
        episodesDownloadedList = podcastRepository.getEpisodeList(Provider.DONWLOADS,"");

        return episodesDownloadedList;
    }


    public MutableLiveData<String> getSearchQuery() {
        return searchQuery;
    }

    public void deletePodcast(int index) {
        episodesDownloadedList.getValue().remove(index);
    }

    public void subscribeToPodcast(int pos, String provider){

        Podcast podcast;
        if(provider.equals(Provider.SPAIN)){
            podcast = spainRecommendedPodcastList.getValue().get(pos);
        } else if(provider.equals(Provider.UK)){
            podcast = ukRecommendedPodcastList.getValue().get(pos);
        } else
            podcast = searchedPodcast.getValue().get(pos);
        podcastRepository.subscribeToPodcast(podcast);
    }

    public void unsubscribeToPodcast(String id){
        podcastRepository.unsubscribeToPodcast(id);
    }

    public boolean isPodcastSubscribed(Podcast podcast){

        for(int i = 0; i < Objects.requireNonNull(subscribedPodcastList.getValue()).size(); i++){
            if(subscribedPodcastList.getValue().get(i).getName().equals(podcast.getName())){
                return true;
            }
        }
        return false;
    }

    @Override
    public void updatePodcastList(List<Podcast> podcastList, String option) {

        switch (option) {
            case Provider.SPAIN:
                spainRecommendedPodcastList.setValue(podcastList);
                break;
            case Provider.UK:
                ukRecommendedPodcastList.setValue(podcastList);
                break;
            case Provider.SUBSCRIBED:
                Log.d("ALEX__", "DB SIZE: " + podcastList.size());
                subscribedPodcastList.setValue(podcastList);
                break;
            default:
                searchedPodcast.setValue(podcastList);
                break;
        }
    }
}
