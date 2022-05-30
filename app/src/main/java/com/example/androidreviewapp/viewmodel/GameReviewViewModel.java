package com.example.androidreviewapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.androidreviewapp.model.Game;
import com.example.androidreviewapp.model.Review;
import com.example.androidreviewapp.repository.FirebaseRepository;
import com.example.androidreviewapp.repository.ReviewRepository;
import com.google.firebase.auth.FirebaseUser;
;import java.util.ArrayList;

public class GameReviewViewModel extends AndroidViewModel {
    private final ReviewRepository reviewRepository;
    private FirebaseRepository firebaseRepository;
    private final MutableLiveData<FirebaseUser> userMutableLiveData;
    private final MutableLiveData<Boolean> loggedOutMutableLiveData;
    private final MutableLiveData<ArrayList<Review>> reviewsLiveData;

    public GameReviewViewModel(@NonNull Application application) {
        super(application);
        reviewRepository = new ReviewRepository(application);
        firebaseRepository = new FirebaseRepository(application);
        reviewsLiveData = reviewRepository.getReviewsLiveData();

        userMutableLiveData = firebaseRepository.getUserMutableLiveData();
        loggedOutMutableLiveData = firebaseRepository.getLoggedOutMutableLiveData();
    }
    public MutableLiveData<ArrayList<Review>> getReviewsLiveData(){
        return reviewsLiveData;
    }
    public void logOut(){firebaseRepository.logout();}
    public void getReviews(String gameId){
        reviewRepository.GetReviews(gameId);
    }
    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }
    public MutableLiveData<Boolean> getLoggedOutMutableLiveData() { return loggedOutMutableLiveData;}
}