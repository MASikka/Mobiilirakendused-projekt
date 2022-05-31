package com.example.androidreviewapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.androidreviewapp.model.Game;
import com.example.androidreviewapp.model.Review;
import com.example.androidreviewapp.repository.FirebaseRepository;
import com.example.androidreviewapp.repository.ReviewRepository;
import com.example.androidreviewapp.repository.SteamReviewRepository;
import com.google.firebase.auth.FirebaseUser;
;import java.util.ArrayList;

public class GameReviewViewModel extends AndroidViewModel {
    private final ReviewRepository reviewRepository;
    private final SteamReviewRepository steamReviewRepository;
    private FirebaseRepository firebaseRepository;
    private final MutableLiveData<FirebaseUser> userMutableLiveData;
    private final MutableLiveData<Boolean> loggedOutMutableLiveData;
    private final MutableLiveData<ArrayList<Review>> reviewsLiveData;
    private final MutableLiveData<ArrayList<Review>> steamReviewsLiveData;

    public GameReviewViewModel(@NonNull Application application) {
        super(application);
        reviewRepository = new ReviewRepository(application);
        steamReviewRepository = new SteamReviewRepository(application);
        firebaseRepository = new FirebaseRepository(application);
        reviewsLiveData = reviewRepository.getReviewsLiveData();
        steamReviewsLiveData = steamReviewRepository.getSteamReviewLiveData();

        userMutableLiveData = firebaseRepository.getUserMutableLiveData();
        loggedOutMutableLiveData = firebaseRepository.getLoggedOutMutableLiveData();
    }
    public MutableLiveData<ArrayList<Review>> getReviewsLiveData(){
        return reviewsLiveData;
    }
    public MutableLiveData<ArrayList<Review>> getSteamReviewsLiveData(){
        return steamReviewsLiveData;
    }
    public void logOut(){firebaseRepository.logout();}
    public void getReviews(String gameId){
        reviewRepository.GetReviews(gameId);
    }
    public void getSteamReviews(String gameId, String langPref) {
        steamReviewRepository.getSteamReviewSearch(gameId, langPref);
    }
    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }
    public MutableLiveData<Boolean> getLoggedOutMutableLiveData() { return loggedOutMutableLiveData;}
}