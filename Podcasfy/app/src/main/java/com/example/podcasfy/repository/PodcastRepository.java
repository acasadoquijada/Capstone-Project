package com.example.podcasfy.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.podcasfy.model.Podcast;
import com.example.podcasfy.model.PodcastEpisode;

import java.util.ArrayList;
import java.util.List;

public class PodcastRepository {

    private MutableLiveData<Podcast> podcast;
    private MutableLiveData<List<PodcastEpisode>> podcastEpisodes;

    public PodcastRepository(){}

    public MutableLiveData<Podcast> getPodcast(String id) {

        // Here I would get the podcast with that ID
        if(podcast != null){
            return podcast;
        }

        podcast = new MutableLiveData<>();

        Podcast value = new Podcast("Planos y pepinillos",
                "Sandwiches y cine",
                "https://www.ivoox.com/podcast-planos-centellas_sq_f1609149_1.html",
                "https://static-2.ivoox.com/canales/3/8/0/0/2671546770083_MD.jpg",
                "ivoox");

        podcast.setValue(value);

        return podcast;
    }

    public MutableLiveData<List<PodcastEpisode>> getPodcastEpisode(String id){

        // Here I will get the episodes according to the ID
        /*
                    String name,
            String description,
            String imageURL,
            String mediaURL,
            String podcastId){
         */
        PodcastEpisode podcastEpisode = new PodcastEpisode(
                "Episode 1",
                "description1",
                "https://static-1.ivoox.com/audios/0/7/8/7/1531591027870_MD.jpg",
                "mediaURL",
                id);

        List<PodcastEpisode> epis = new ArrayList<>();

        epis.add(podcastEpisode);
        epis.add(podcastEpisode);
        epis.add(podcastEpisode);
        epis.add(podcastEpisode);
        epis.add(podcastEpisode);
        epis.add(podcastEpisode);
        epis.add(podcastEpisode);

        podcastEpisodes = new MutableLiveData<>();
        podcastEpisodes.setValue(epis);

        return podcastEpisodes;
    }
}