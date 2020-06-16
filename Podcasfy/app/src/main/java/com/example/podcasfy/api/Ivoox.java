package com.example.podcasfy.api;


import android.util.Log;

import com.example.podcasfy.model.Podcast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Ivoox {

    private static final String BASE_URL = "https://www.ivoox.com/en/";
    private static final String BASE_URL_SUFFIX = "_sw_1_1.html";

    public List<Podcast> getPodcast(String podcastName){

        String url = BASE_URL + podcastName + BASE_URL_SUFFIX;

        Log.d("URL_W", url);


        Document doc = null;



        List<Podcast> ivooxPodcasts = new ArrayList<>();
        try {
            doc = Jsoup.connect(url).get();
            Elements names = doc.select("meta[itemprop=name]");
            Elements descriptions = doc.select("meta[itemprop=description]");
            Elements urls = doc.select("meta[itemprop=url]");

            Elements images = doc.select("img[class=main]");

            //Log.d("URL_W","names: " + String.valueOf(names.size()));
            //Log.d("URL_W","description: " + String.valueOf(descriptions.size()));
            //Log.d("URL_W","urls: " + String.valueOf(urls.size()));
            //Log.d("URL_W","images: " + String.valueOf(images.size()));

            // we don't care about the first one
            names.remove(0);

            Log.d("API___", "names size: " + names.size());
            Log.d("API___", "descriptions size: " + descriptions.size());


            for(int i = 0; i < names.size(); i++){
                Podcast ivooxPodcast = new Podcast();

                Log.d("URL_W",names.get(i).attr("content"));
                Log.d("URL_W",descriptions.get(i).attr("content"));
                Log.d("URL_W",urls.get(i).attr("content"));
                Log.d("URL_W",images.get(i).attr("src"));

                ivooxPodcast.setName(names.get(i).attr("content"));
                ivooxPodcast.setDescription(descriptions.get(i).attr("content"));
                ivooxPodcast.setUrl(urls.get(i).attr("content"));
                ivooxPodcast.setMediaURL(images.get(i).attr("src"));

                ivooxPodcasts.add(ivooxPodcast);
            }

            return ivooxPodcasts;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Podcast> getRecommended(){
        final String url = "https://www.ivoox.com/";

        List<Podcast> podcastList = new ArrayList<>();
        Document doc = null;

        try{
            doc = Jsoup.connect(url).get();

            // From here I get the URL and title
            Elements elementsURLandTitle = doc.select("h3.m-top-10");

            // From here I get the image
            Elements elementsImages = doc.select("figure.effeckt-caption");

            if(elementsURLandTitle != null && elementsImages != null){
                Log.d("API__", "size: "+elementsURLandTitle.size());

                for(int i = 0; i < elementsURLandTitle.size(); i++){

                    Podcast podcast = new Podcast();

                    // Set name
                    podcast.setName(elementsURLandTitle.get(i).select("a").attr("title"));

                    // Set URL
                    podcast.setUrl(elementsURLandTitle.get(i).select("a").attr("href"));

                    // Set image
                    podcast.setMediaURL(elementsImages.get(i).select("img").attr("src"));

                    // Set provider
                    podcast.setProvider("Ivoox");

                    podcastList.add(podcast);

                }
            }

            return podcastList;

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
