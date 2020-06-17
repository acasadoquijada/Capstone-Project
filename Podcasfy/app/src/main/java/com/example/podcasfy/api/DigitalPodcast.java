package com.example.podcasfy.api;

import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.podcasfy.model.Episode;
import com.example.podcasfy.model.Podcast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class DigitalPodcast {

    public List<Podcast> getRecommended() {
        final String url = "http://www.digitalpodcast.com/";

        List<Podcast> podcastList = new ArrayList<>();
        Document doc = null;

        try{
            doc = Jsoup.connect(url).get();

            Elements elements = doc.select("div.small-cover");

            Log.d("PODCAST__","elements size: " + elements.size());

            for (int i = 0; i < elements.size(); i++) {
                Podcast podcast = new Podcast();

                podcast.setName(elements.get(i).select("a").select("img").attr("title"));
                podcast.setMediaURL(elements.get(i).select("a").select("img").attr("src"));
                podcast.setUrl(url + elements.get(i).select("a").attr("href"));

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
            // From here I get the URL and title
            //  Elements elementsURLandTitle = doc.select("div.col-xs-12.col-sm-6.col-md-4.col-lg-3");

            // Get image
            Elements imageElement = doc.select("div.span3");
            String imageURL = imageElement.select("img").attr("src");

            Log.d("TEST", "IMAGE: " + imageURL);

            Elements elements = doc.select("div#accordion1.accordion");
            Elements podcastElement = elements.select("div.accordion-group");

            for(int i = 0; i < podcastElement.size(); i++){
                Episode episode = new Episode();

                episode.setName(podcastElement.get(i).select("strong.title").text());
                episode.setImageURL(imageURL);
                episodeList.add(episode);
                Log.d("TEST", "title: " + podcastElement.get(i).select("strong.title").text());
            }

            //Elements elementsURLandTitle = elements.select("div.col-xs-12.col-sm-6.col-md-4.col-lg-3");

            Log.d("TEST", "elements size: " + podcastElement.size());

            return episodeList;

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
