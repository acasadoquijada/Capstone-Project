package com.example.podcasfy.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReproducerViewModel extends ViewModel {

    private MutableLiveData<String> name;
    private MutableLiveData<String> logoURL;
    private MutableLiveData<String> mediaURL;

    public ReproducerViewModel(){
        name = new MutableLiveData<>();
        logoURL = new MutableLiveData<>();
    }

    public LiveData<String> getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public MutableLiveData<String> getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL.setValue(logoURL);
    }

    public MutableLiveData<String> getMediaURL() {
        return mediaURL;
    }

    public void setMediaURL(String mediaURL) {
        this.mediaURL.setValue(mediaURL);
    }
}
