package com.example.androidreviewapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.androidreviewapp.model.Game;
import com.example.androidreviewapp.model.User;
import com.example.androidreviewapp.repository.FirebaseRepository;
import com.example.androidreviewapp.repository.GameRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class SearchViewModel extends AndroidViewModel {

    private final GameRepository gameRepository;
    private final MutableLiveData<ArrayList<Game>> gameLiveData;

    private FirebaseRepository firebaseRepository;
    private final MutableLiveData<FirebaseUser> userMutableLiveData;
    private final MutableLiveData<Boolean> loggedOutMutableLiveData;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        gameRepository = new GameRepository(application);
        gameLiveData = gameRepository.getGameLiveData();

        firebaseRepository = new FirebaseRepository(application);
        userMutableLiveData = firebaseRepository.getUserMutableLiveData();
        loggedOutMutableLiveData = firebaseRepository.getLoggedOutMutableLiveData();
    }

    public MutableLiveData<ArrayList<Game>> getGameLiveData(){
        return gameLiveData;
    }

    public void getGameSearch(String name) {
        gameRepository.getGameSearch(name);
    }
    public void logOut(){firebaseRepository.logout();}
    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public boolean hasGameNames(){
        return gameRepository.isNotEmptyArrayList();
    }

    public int getGameNamesAmount(){
        return gameRepository.getArrayListSize();
    }
    public MutableLiveData<Boolean> getLoggedOutMutableLiveData() { return loggedOutMutableLiveData;}

}