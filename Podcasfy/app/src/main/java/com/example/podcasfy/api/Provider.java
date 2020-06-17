package com.example.podcasfy.api;

import android.util.Log;

import com.example.podcasfy.model.Episode;
import com.example.podcasfy.model.Podcast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Provider {

    public static final String SUBSCRIBED = "subscribed";
    public static final String IVOOX = "ivoox";
    public static final String DIGITAL = "digital";

    public static final String SPAIN = "spain";
    public static final String UK = "uk";

    private static final String SPAIN_URL = "http://www.radio-espana.es/podcasts";
    private static final String SPAIN_URL_SUB = "http://www.radio-espana.es";

    private static final String UK_URL = "https://www.radio-uk.co.uk/podcasts";
    private static final String UK_URL_SUB = "https://www.radio-uk.co.uk";

    private String url;
    private String url_sub;

    public Provider(){
    }

    public List<Podcast> getRecommended(String country) {

        if(country.equals(SPAIN)){
            url = SPAIN_URL;
            url_sub = SPAIN_URL_SUB;
        } else if(country.equals(UK)){
            url = UK_URL;
            url_sub = UK_URL_SUB;
        }
        List<Podcast> podcastList = new ArrayList<>();
        Document doc = null;

        try{
            doc = Jsoup.connect(url).get();

            Elements podcastElements = doc.select("a.mdc-list-item");

            for (int i = 0; i < podcastElements.size(); i++) {
                Podcast podcast = new Podcast();

                podcast.setName(podcastElements.get(i).select("img").attr("alt"));
                podcast.setMediaURL(podcastElements.get(i).select("img").attr("src"));
                podcast.setUrl(url_sub + podcastElements.get(i).attr("href"));

                podcastList.add(podcast);
            }

            return podcastList;

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Episode> getEpisodes(String podcastURL){
        List<Episode> episodeList = new ArrayList<>();
        Document doc = null;

        try{
            doc = Jsoup.connect(podcastURL).get();

            Elements elements = doc.select("div.podcast-item");

            String imageURL = doc.select("div.content-column-left").select("img").attr("src");

            Log.d("PODCAST__","MEDIA: " + elements.get(0).select("svg[data-url$=.mp3]").attr("data-url"));

            String a = "a";
            for(int i = 0; i < elements.size(); i++){

                Episode episode = new Episode();
                String nameQuery = "episode_" + (i+1);
                episode.setName(elements.get(i).text());
                episode.setImageURL(imageURL);
                episode.setMediaURL(elements.get(i).select("svg[data-url$=.mp3]").attr("data-url"));
                Log.d("PODCAST__","MEDIA: " + elements.get(i).select("svg[data-url$=.mp3]").attr("data-url"));

                episodeList.add(episode);
            }
            return episodeList;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
