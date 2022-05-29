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

    public PersonalReviewViewModel(@NonNull Application application) {
        super(application);
        reviewRepository = new ReviewRepository();
        firebaseRepository = new FirebaseRepository(application);
        userMutableLiveData = firebaseRepository.getUserMutableLiveData();
        loggedOutMutableLiveData = firebaseRepository.getLoggedOutMutableLiveData();
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutMutableLiveData() { return loggedOutMutableLiveData;}

    public void postReview(Review review){
        Log.i("a","siin");
        reviewRepository.PostReview(review);
    }
    public String getUserEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userEmail = user.getEmail();
            return userEmail;
        } else {
            return "nothing";
        }
    }
}