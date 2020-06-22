package com.example.podcasfy.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.podcasfy.api.Provider;
import com.example.podcasfy.model.AppDataBase;
import com.example.podcasfy.model.Episode;
import com.example.podcasfy.model.Podcast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;


// Follow this SO post
// https://stackoverflow.com/questions/51601046/should-i-make-asynctask-member-of-livedata-or-repository-class-as-replacement/51631757
@SuppressLint("StaticFieldLeak")

@Singleton
public class PodcastListRepository {

    private MutableLiveData<List<Podcast>> spainRecommendedPodcastList;
    private MutableLiveData<List<Podcast>> ukRecommendedPodcastList;
    private LiveData<List<Podcast>> subscribedPodcastList;
    private MutableLiveData<List<Podcast>> searchedPodcastList;

    private MutableLiveData<List<Episode>> episodeList;


    private Provider provider;

    private Context context;

    public PodcastListRepository(Context context){
        this.context = context;

        searchedPodcastList = new MutableLiveData<>();
        episodeList = new MutableLiveData<>();

        provider = new Provider();

        subscribedPodcastList = AppDataBase.getInstance(context).podcastDAO().getPodcasts();

    }

    public MutableLiveData<List<Podcast>> getSpainRecommendedPodcastList(){

        if(spainRecommendedPodcastList == null){
            spainRecommendedPodcastList = new MutableLiveData<>();
            new getPodcastListTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,Provider.SPAIN);
        }

        return spainRecommendedPodcastList;
    }

    public MutableLiveData<List<Podcast>> getUKRecommended(){

        if(ukRecommendedPodcastList == null){
            ukRecommendedPodcastList = new MutableLiveData<>();
            new getPodcastListTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,Provider.UK);
        }

        return ukRecommendedPodcastList;
    }

    public LiveData<List<Podcast>> getSubscribedPodcastList(){
        return subscribedPodcastList;
    }

    public void searchPodcast(String query){

        new getPodcastListTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,Provider.SEARCH,query);
    }

    public MutableLiveData<List<Podcast>> getSearchedEpisodeList(){
        return searchedPodcastList;
    }

    public MutableLiveData<List<Episode>> getEpisodeList(String provider, String podcastURL){

        if(episodeList.getValue() != null){
            episodeList.getValue().clear();
        }

        switch (provider) {
            case Provider.SPAIN:
                getSpainEpisodes(podcastURL);
                break;
            case Provider.UK:
                getUKEpisodes(podcastURL);
                break;
            case Provider.SUBSCRIBED:
                getSubscribedEpisodes(podcastURL);
                break;
            default:
                getDownloadedEpisodes();
                break;
        }
        return episodeList;
    }


    private void getSpainEpisodes(String podcastURL){
        new getEpisodeListTask().execute(Provider.SPAIN,podcastURL);
    }

    private void getUKEpisodes(String podcastURL){
        new getEpisodeListTask().execute(Provider.UK,podcastURL);
    }

    private void getSubscribedEpisodes(String podcastURL){
        new getEpisodeListTask().execute(Provider.SUBSCRIBED, podcastURL);
    }

    private void getDownloadedEpisodes(){
        new getEpisodeListTask().execute(Provider.DONWLOADS,"");
    }

    public void subscribeToPodcast(Podcast podcast){
        new AddPodcastDatabaseTask().execute(podcast);
    }

    public void unsubscribeToPodcast(String id){
        new DeletePodcastDatabaseTask().execute(id);
    }


    class getPodcastListTask extends AsyncTask<String, Void, List<Podcast> > {

        private String argument;

        @Override
        protected List<Podcast> doInBackground(String... strings) {

            argument = strings[0];

            switch (argument) {
                case Provider.SPAIN:
                    return provider.getRecommended(Provider.SPAIN);
                case Provider.UK:
                    return provider.getRecommended(Provider.UK);
                case Provider.SEARCH:
                    String query = strings[1];
                    return provider.searchPodcast(query);
            }
            return new ArrayList<>();
        }

        @Override
        protected void onPostExecute(List<Podcast> podcastsList) {

            switch (argument) {
                case Provider.SPAIN:
                    spainRecommendedPodcastList.setValue(podcastsList);
                    break;
                case Provider.UK:
                    ukRecommendedPodcastList.setValue(podcastsList);
                    break;
                case Provider.SEARCH:
                    searchedPodcastList.setValue(podcastsList);
            }
        }
    }

    class getEpisodeListTask extends AsyncTask<String, Void, List<Episode> > {

        @Override
        protected List<Episode> doInBackground(String... strings) {

            String podcastProvider = strings[0];
            String  url = strings[1];

            if (podcastProvider.equals(Provider.DONWLOADS)) {
                return generateEpisodes();
            }
            else
                return provider.getEpisodes(url);
        }

        @Override
        protected void onPostExecute(List<Episode> podcastsList) {
            episodeList.setValue(podcastsList);
        }
    }

    class DeletePodcastDatabaseTask extends AsyncTask<String,Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            AppDataBase.getInstance(context).podcastDAO().delete(strings[0]);
            return null;
        }
    }

    class AddPodcastDatabaseTask extends AsyncTask<Podcast, Void, Void> {

        @Override
        protected Void doInBackground(Podcast... podcasts) {
            Podcast podcast = podcasts[0];
            AppDataBase.getInstance(context).podcastDAO().insertPodcast(podcast);
            return null;
        }
    }

    private List<Episode> generateEpisodes(){
        Episode episode = new Episode(
                "Planos y Centellas 3x20 - Snowpiercer (Serie Netflix)",
                "Subid con nosotros al tren y evitad que la glaciación os alcance. " +
                        "Esta semana hablamos del estreno de Netflix inspirado en la película" +
                        " homónima \"Snowpiercer\". Un misterioso asesinato amenaza con desestabilizar " +
                        "el delicado equilibrio del tren-arca, la última esperanza de la humanidad.",

                "https://static-1.ivoox.com/audios/0/7/8/7/1531591027870_MD.jpg",
                "mediaURL",
                "id");

        List<Episode> epis = new ArrayList<>();

        epis.add(episode);
        epis.add(episode);
        epis.add(episode);
        epis.add(episode);
        epis.add(episode);
        epis.add(episode);
        epis.add(episode);

        return epis;
    }

}