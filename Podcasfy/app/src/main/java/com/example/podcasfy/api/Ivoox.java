package com.example.podcasfy.api;


import android.util.Log;

import com.example.podcasfy.model.Podcast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Ivoox {

    private static final String BASE_URL = "https://www.ivoox.com/en/";
    private static final String BASE_URL_SUFFIX = "_sw_1_1.html";

    public Podcast getPodcast(String podcastName){

        String url = BASE_URL + podcastName + BASE_URL_SUFFIX;

        Document doc = null;

        Podcast ivooxPodcast = new Podcast();

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

            Log.d("URL_W",names.get(1).attr("content"));
            Log.d("URL_W",descriptions.get(0).attr("content"));
            Log.d("URL_W",urls.get(0).attr("content"));
            Log.d("URL_W",images.get(0).attr("src"));

            ivooxPodcast.setName(names.get(1).attr("content"));
            ivooxPodcast.setDescription(descriptions.get(0).attr("content"));
            ivooxPodcast.setUrl(urls.get(0).attr("content"));
            ivooxPodcast.setMediaURL(images.get(0).attr("src"));

            return ivooxPodcast;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
