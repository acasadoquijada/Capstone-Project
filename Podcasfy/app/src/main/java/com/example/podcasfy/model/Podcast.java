package com.example.podcasfy.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "podcast")
public class Podcast {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String description;
    private String url;
    private String mediaURL;
    private String provider;

    public Podcast(){};

    public Podcast(String name, String description, String url, String mediaURl, String provider){
        this.name = name;
        this.description = description;
        this.url = url;
        this.mediaURL = mediaURl;
        this.provider = provider;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getMediaURL() {
        return mediaURL;
    }

    public String getProvider() {
        return provider;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMediaURL(String mediaURL) {
        this.mediaURL = mediaURL;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
