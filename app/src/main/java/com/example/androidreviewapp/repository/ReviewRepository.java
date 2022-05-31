package com.example.androidreviewapp.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.androidreviewapp.model.Game;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.androidreviewapp.model.Review;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ReviewRepository {
    private Application application;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "Review Firebase";
    private MutableLiveData<Review> reviewMutableLiveData;
    private MutableLiveData<Boolean> reviewExistsMutableLiveData;
    private MutableLiveData<ArrayList<Review>> reviewsLiveData;
    private final ArrayList<Review> reviewArrayList = new ArrayList<>();
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public ReviewRepository(Application application) {
        this.application = application;
        reviewMutableLiveData = new MutableLiveData<>();
        reviewExistsMutableLiveData = new MutableLiveData<>();
        reviewsLiveData = new MutableLiveData<>();

    }
    public ReviewRepository(){}

    public MutableLiveData<Review> getReviewMutableLiveData() {
        return reviewMutableLiveData;
    }

    public MutableLiveData<Boolean> getReviewExistsMutableLiveData() {
        return reviewExistsMutableLiveData;
    }
    public MutableLiveData<ArrayList<Review>> getReviewsLiveData(){
        return reviewsLiveData;
    }

    public void GetReviews(String gameId){
        reviewArrayList.clear();
        db.collection("reviews")
                .whereEqualTo("gameId", gameId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.i(TAG, document.getId() + " => " + document.getData());
                                Review review = new Review(document.get("reviewText").toString(),document.get("userEmail").toString(),Boolean.valueOf(document.get("recommends").toString()));
                                reviewArrayList.add(review);
                                reviewsLiveData.setValue(reviewArrayList);
                            }
                        } else {
                            Log.i(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    public void GetUserReview(String gameId){
        FirebaseUser user = auth.getCurrentUser();
        String userId = user.getUid();
        String id = gameId+userId;
        DocumentReference docRef = db.collection("reviews").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.i(TAG, "DocumentSnapshot data: " + document.getData());
                        reviewExistsMutableLiveData.setValue(true);
                        reviewMutableLiveData.setValue(document.toObject(Review.class));
                    } else {
                        Log.i(TAG, "No such document");
                        reviewExistsMutableLiveData.setValue(false);
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public void PostReview(Review review){
        FirebaseUser user = auth.getCurrentUser();
        String userId = user.getUid();
        String id = review.getGameId()+userId;
        db.collection("reviews").document(id).set(review);
    }

    public void DeleteReview(String gameId){
        FirebaseUser user = auth.getCurrentUser();
        String userId = user.getUid();
        String id = gameId+userId;
        db.collection("reviews").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }
}
