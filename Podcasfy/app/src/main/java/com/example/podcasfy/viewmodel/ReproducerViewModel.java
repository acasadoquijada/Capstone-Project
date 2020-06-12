package com.example.podcasfy.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReproducerViewModel extends ViewModel {

    private MutableLiveData<String> name;

    public ReproducerViewModel(){
        name = new MutableLiveData<>();
    }

    public LiveData<String> getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name.setValue(name);
    }
}
