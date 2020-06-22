package com.example.podcasfy.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.podcasfy.api.Provider;
import com.example.podcasfy.model.AppDataBase;
import com.example.podcasfy.model.Episode;
import com.example.podcasfy.model.Podcast;
import com.example.podcasfy.utils.PodcastCallBack;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;


// Follow this SO post
// https://stackoverflow.com/questions/51601046/should-i-make-asynctask-member-of-livedata-or-repository-class-as-replacement/51631757
@SuppressLint("StaticFieldLeak")

@Singleton
public class PodcastListRepository {

    final private PodcastCallBack podcastCallBack;

    private MutableLiveData<List<Podcast>> spainRecommendedPodcastList;
    private MutableLiveData<List<Podcast>> ukRecommendedPodcastList;
    private MutableLiveData<List<Podcast>> subscribedPodcastList;
    private MutableLiveData<List<Podcast>> searchedPodcastList;

    private MutableLiveData<List<Episode>> episodeList;


    private Provider provider;

    private Context context;

    public PodcastListRepository(Context context, PodcastCallBack podcastCallBack){
        this.context = context;
        this.podcastCallBack = podcastCallBack;

        searchedPodcastList = new MutableLiveData<>();
        episodeList = new MutableLiveData<>();

        provider = new Provider();

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

    public MutableLiveData<List<Podcast>> getSubscribedPodcastList(){
        if(subscribedPodcastList == null){
            subscribedPodcastList = new MutableLiveData<>();
            new getPodcastListTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,Provider.SUBSCRIBED);
        }

        return subscribedPodcastList;
    }

    public void searchPodcast(String query){
        new getSearchedPodcastListTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,query);
    }

    public MutableLiveData<List<Podcast>> getSearchedEpisodeList(){
        return searchedPodcastList;
    }

    public MutableLiveData<List<Episode>> getEpisodeList(String provider, String podcastURL){

        if(provider.equals(Provider.SPAIN)){
            getSpainEpisodes(podcastURL);
        } else if(provider.equals(Provider.UK)){
            getUKEpisodes(podcastURL);
        } else if(provider.equals(Provider.SUBSCRIBED)){
            getSubscribedEpisodes(podcastURL);
        } else {
            getDownloadedEpisodes();
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
                case Provider.SUBSCRIBED:
                    return AppDataBase.getInstance(context).podcastDAO().getPodcasts();
            }
            return null;
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
                case Provider.SUBSCRIBED:
                    subscribedPodcastList.setValue(podcastsList);
                    break;
            }
        }
    }

    class getEpisodeListTask extends AsyncTask<String, Void, List<Episode> > {

        private String url;
        private String podcastProvider;

        @Override
        protected List<Episode> doInBackground(String... strings) {

            podcastProvider = strings[0];
            url = strings[1];

            if (podcastProvider.equals(Provider.DONWLOADS)) {
                return generateEpisodes();
            }
            else
                return provider.getEpisodes(url);
        }

        @Override
        protected void onPostExecute(List<Episode> podcastsList) {
            episodeList.setValue(podcastsList);
     //       episodeCallBack.updateEpisodeList(podcastsList, podcastProvider, url);
        }
    }

    class DeletePodcastDatabaseTask extends AsyncTask<String,Void, List<Podcast>> {
        @Override
        protected List<Podcast> doInBackground(String... strings) {

            AppDataBase.getInstance(context).podcastDAO().delete(strings[0]);

            return AppDataBase.getInstance(context).podcastDAO().getPodcasts();
        }

        @Override
        protected void onPostExecute(List<Podcast> podcastList) {
            podcastCallBack.updatePodcastList(podcastList,Provider.SUBSCRIBED);
        }
    }
        class AddPodcastDatabaseTask extends AsyncTask<Podcast,Void, List<Podcast>>{

        @Override
        protected List<Podcast> doInBackground(Podcast... podcasts) {

            Podcast podcast = podcasts[0];
            AppDataBase.getInstance(context).podcastDAO().insertPodcast(podcast);

            // THIS SHOULD BE IMPROVE. We should return only the new podcast, not re-querying the
            // database
            return AppDataBase.getInstance(context).podcastDAO().getPodcasts();
        }

        @Override
        protected void onPostExecute(List<Podcast> podcastList) {
            podcastCallBack.updatePodcastList(podcastList,Provider.SUBSCRIBED);
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

    class getSearchedPodcastListTask extends AsyncTask<String, Void, List<Podcast> > {

        @Override
        protected List<Podcast> doInBackground(String... strings) {
            String query = strings[0];
            return provider.searchPodcast(query);
        }

        @Override
        protected void onPostExecute(List<Podcast> podcastsList) {
            searchedPodcastList.setValue(podcastsList);
        }
    }

}