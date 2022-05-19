package com.example.androidreviewapp.fragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidreviewapp.Game;
import com.example.androidreviewapp.GameRepository;

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