package com.example.podcasfy.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.podcasfy.api.Provider;
import com.example.podcasfy.model.AppDataBase;
import com.example.podcasfy.model.Episode;
import com.example.podcasfy.model.Podcast;
import com.example.podcasfy.utils.EpisodeCallBack;
import com.example.podcasfy.utils.PodcastCallBack;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

@Singleton
public class PodcastListRepository {

    final private PodcastCallBack podcastCallBack;
    final private EpisodeCallBack episodeCallBack;

    private Provider provider;

    private Context context;

    public PodcastListRepository(Context context, PodcastCallBack podcastCallBack, EpisodeCallBack episodeCallBack){
        this.context = context;
        this.podcastCallBack = podcastCallBack;
        this.episodeCallBack = episodeCallBack;

        provider = new Provider();
    }

    public void getSpainRecommended(){

        // asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
       // new FetchPodcastListTask().execute(Provider.SPAIN);
        new FetchPodcastListTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,Provider.SPAIN);
    }

    public void getUKRecommended(){
        new FetchPodcastListTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,Provider.UK);
    }

    public void getSpainEpisodes(String podcastURL){
        new FetchEpisodeListTask().execute(Provider.SPAIN,podcastURL);
    }

    public void getUKEpisodes(String podcastURL){
        new FetchEpisodeListTask().execute(Provider.UK,podcastURL);
    }

    public void getSubscribedEpisodes(String podcastURL){
        new FetchEpisodeListTask().execute(Provider.SUBSCRIBED, podcastURL);
    }

    public void searchPodcast(String query){
        new FetchSearchEpisodeListTask().execute(query);
    }

    public void getSubscribedPodcastList(){
        new FetchPodcastListTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,Provider.SUBSCRIBED);
    }

    public void getDownloadedEpisodes(){
        new FetchEpisodeListTask().execute(Provider.DONWLOADS,"a");
    }

    public void subscribeToPodcast(Podcast podcast){
        new AddPodcastDatabaseTask().execute(podcast);
    }

    public void unsubscribeToPodcast(String id){
        new DeletePodcastDatabaseTask().execute(id);
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

    class FetchPodcastListTask extends AsyncTask<String, Void, List<Podcast> > {

        private String argument;
        @Override
        protected List<Podcast> doInBackground(String... strings) {

            argument = strings[0];
            Log.d("PODCAST__", "argument: " + argument);

            if(argument.equals(Provider.SPAIN)){
                return provider.getRecommended(Provider.SPAIN);
            } else if(argument.equals(Provider.UK)){
                return provider.getRecommended(Provider.UK);
            } else if(argument.equals(Provider.SUBSCRIBED)){
                return AppDataBase.getInstance(context).podcastDAO().getPodcasts();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Podcast> podcastsList) {
            podcastCallBack.updatePodcastList(podcastsList,argument);
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

    class FetchEpisodeListTask extends AsyncTask<String, Void, List<Episode> > {

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
            episodeCallBack.updateEpisodeList(podcastsList, podcastProvider, url);
        }
    }

    class FetchSearchEpisodeListTask extends AsyncTask<String, Void, List<Podcast> > {


        @Override
        protected List<Podcast> doInBackground(String... strings) {

            String query = strings[0];
            return provider.searchPodcast(query);
        }

        @Override
        protected void onPostExecute(List<Podcast> podcastsList) {
            podcastCallBack.updatePodcastList(podcastsList, "");
        }
    }

}