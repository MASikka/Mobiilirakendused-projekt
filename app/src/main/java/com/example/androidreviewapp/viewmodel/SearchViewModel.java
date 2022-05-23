package com.example.androidreviewapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.androidreviewapp.model.Game;
import com.example.androidreviewapp.repository.GameRepository;

import java.util.ArrayList;

public class SearchViewModel extends AndroidViewModel {

    private final GameRepository gameRepository;
    private final MutableLiveData<ArrayList<Game>> gameLiveData;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        gameRepository = new GameRepository(application);
        gameLiveData = gameRepository.getGameLiveData();
    }

    public MutableLiveData<ArrayList<Game>> getGameLiveData(){
        return gameLiveData;
    }

    public void getGameSearch(String name) {
        gameRepository.getGameSearch(name);
    }
}