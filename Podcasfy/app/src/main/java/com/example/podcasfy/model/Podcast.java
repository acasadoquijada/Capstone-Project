package com.example.podcasfy.model;

public class Podcast {

    private String name;
    private String description;
    private String url;
    private String mediaURL;
    private String provider;

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
}
