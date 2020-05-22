package com.example.podcasfy.model;

public class PodcastEpisode {

    // This is the path where the mp3 file is stored in the device
    private String path;

    // Podcast Episode name
    private String name;

    // Podcast Episode description
    private String description;

    // Podcast Episode image
    private String imageURL;

    // Podcast Episode media (audio)
    private String mediaURL;

    // Date when the Podcast Episode was listened to
    private String date;

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMediaURL(String mediaURL) {
        this.mediaURL = mediaURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getMediaURL() {
        return mediaURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getDate() {
        return date;
    }
}
