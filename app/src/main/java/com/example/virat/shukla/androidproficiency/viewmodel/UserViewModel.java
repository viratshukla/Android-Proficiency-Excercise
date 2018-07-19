package com.example.virat.shukla.androidproficiency.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.virat.shukla.androidproficiency.database.ResponseModel;
import com.example.virat.shukla.androidproficiency.webrepo.WebRepository;

public class UserViewModel extends AndroidViewModel {
    private WebRepository repo;
    private LiveData<ResponseModel> response;

    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        repo = WebRepository.getInstance();
        repo.setContext(getApplication());
        repo.pullData();
        response = repo.getResponseModelLiveData();
    }

    public LiveData<ResponseModel> getResponse() {
        return response;
    }

    public void refreshData() {
        repo.pullData();
    }

}
