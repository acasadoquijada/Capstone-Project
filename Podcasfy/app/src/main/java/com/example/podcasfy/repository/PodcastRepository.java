package com.example.podcasfy.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.podcasfy.model.Episode;
import com.example.podcasfy.model.Podcast;

import java.util.ArrayList;
import java.util.List;

public class PodcastRepository {

    private MutableLiveData<Podcast> podcast;
    private MutableLiveData<List<Episode>> podcastEpisodes;
    private MutableLiveData<List<Podcast>> podcasts;

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

    public MutableLiveData<List<Episode>> getPodcastEpisode(String id){

        Episode episode = new Episode(
                "Planos y Centellas 3x20 - Snowpiercer (Serie Netflix)",
                "Subid con nosotros al tren y evitad que la glaciación os alcance. " +
                        "Esta semana hablamos del estreno de Netflix inspirado en la película" +
                        " homónima \"Snowpiercer\". Un misterioso asesinato amenaza con desestabilizar " +
                        "el delicado equilibrio del tren-arca, la última esperanza de la humanidad.",

                "https://static-1.ivoox.com/audios/0/7/8/7/1531591027870_MD.jpg",
                "mediaURL",
                id);

        List<Episode> epis = new ArrayList<>();

        epis.add(episode);
        epis.add(episode);
        epis.add(episode);
        epis.add(episode);
        epis.add(episode);
        epis.add(episode);
        epis.add(episode);

        podcastEpisodes = new MutableLiveData<>();
        podcastEpisodes.setValue(epis);

        return podcastEpisodes;
    }

    public List<Podcast> searchPodcasts(String query){

        Podcast value = new Podcast("Planos y pepinillos",
                "Sandwiches y cine",
                "https://www.ivoox.com/podcast-planos-centellas_sq_f1609149_1.html",
                "https://static-2.ivoox.com/canales/3/8/0/0/2671546770083_MD.jpg",
                "ivoox");

        Podcast value2 = new Podcast("Planos y pepinillos",
                "Sandwiches y cine",
                "https://www.ivoox.com/podcast-planos-centellas_sq_f1609149_1.html",
                "https://static-2.ivoox.com/canales/3/8/0/0/2671546770083_MD.jpg",
                "spotify");

        List<Podcast> p = new ArrayList<>();

        p.add(value);
        p.add(value2);

        return p;
    }

    public MutableLiveData<List<Episode>> getDownloadedEspisodes(){
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


        podcastEpisodes = new MutableLiveData<>();
        podcastEpisodes.setValue(epis);

        return podcastEpisodes;
    }
}
