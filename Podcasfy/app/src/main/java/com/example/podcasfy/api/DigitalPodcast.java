package com.example.podcasfy.api;

import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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

    private String  getResponseFromHttpUrl(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

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
                podcast.setUrl(url + elements.get(i).select("a").select("img").attr("title"));

                podcastList.add(podcast);
            }

            return podcastList;

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
