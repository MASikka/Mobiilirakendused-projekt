package com.example.androidreviewapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.androidreviewapp.model.Review;
import com.example.androidreviewapp.repository.FirebaseRepository;
import com.example.androidreviewapp.repository.ReviewRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PersonalReviewViewModel extends AndroidViewModel {
    private FirebaseRepository firebaseRepository;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<Boolean> loggedOutMutableLiveData;
    private ReviewRepository reviewRepository;
    private MutableLiveData<Review> reviewMutableLiveData;
    private MutableLiveData<Boolean> reviewExistsMutableLiveData;


    public PersonalReviewViewModel(@NonNull Application application) {
        super(application);
        reviewRepository = new ReviewRepository(application);
        firebaseRepository = new FirebaseRepository(application);
        userMutableLiveData = firebaseRepository.getUserMutableLiveData();
        loggedOutMutableLiveData = firebaseRepository.getLoggedOutMutableLiveData();

        reviewMutableLiveData = reviewRepository.getReviewMutableLiveData();
        reviewExistsMutableLiveData = reviewRepository.getReviewExistsMutableLiveData();

    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutMutableLiveData() { return loggedOutMutableLiveData;}

    public MutableLiveData<Review> getReviewMutableLiveData() {
        return reviewMutableLiveData;
    }

    public MutableLiveData<Boolean> getReviewExistsMutableLiveData() {
        return reviewExistsMutableLiveData;
    }
    public void logOut(){firebaseRepository.logout();}
    public void deleteReview(String gameId){
        reviewRepository.DeleteReview(gameId);
    }
    public void postReview(Review review){
        reviewRepository.PostReview(review);
    }
    public void checkIfReviewExists(String gameId){
        reviewRepository.GetUserReview(gameId);
    }
    public String getUserEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userEmail = user.getEmail();
            return userEmail;
        } else {
            return "";
        }
    }
}