package com.example.podcasfy.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.podcasfy.api.Provider;
import com.example.podcasfy.model.Episode;
import com.example.podcasfy.repository.PodcastListRepository;
import com.example.podcasfy.model.Podcast;

import java.util.List;

public class PodcastListViewModel extends AndroidViewModel {

    private PodcastListRepository podcastRepository;

    // Podcast per each provider + subscriptions + search
    private MutableLiveData<List<Podcast>> spainRecommendedPodcastList;
    private MutableLiveData<List<Podcast>> ukRecommendedPodcastList;
    private LiveData<List<Podcast>> subscribedPodcastList;
    private MutableLiveData<List<Podcast>> searchedPodcast;

    // Episodes per given Podcast
    private MutableLiveData<List<Episode>> episodeList;

    // Downloaded episodes
    private MutableLiveData<List<Episode>> episodesDownloadedList;

    // Used to triggered a search when the user sets a query
    private MutableLiveData<String> searchQuery;


    public PodcastListViewModel (Application application){
        super(application);

        podcastRepository = new PodcastListRepository(application.getApplicationContext());

        episodeList = new MutableLiveData<>();
        searchQuery = new MutableLiveData<>();
        searchedPodcast = new MutableLiveData<>();
    }


    public LiveData<List<Podcast>> getSubscribedPodcastList() {

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

        if(subscribedPodcastList.getValue() != null){
            for(int i = 0; i < subscribedPodcastList.getValue().size(); i++){
                if(subscribedPodcastList.getValue().get(i).getName().equals(podcast.getName())){
                    return true;
                }
            }
        }
        return false;
    }
}
