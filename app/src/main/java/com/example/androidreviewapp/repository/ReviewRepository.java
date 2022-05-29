package com.example.androidreviewapp.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.androidreviewapp.model.Review;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ReviewRepository {
    private Application application;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "Review Firebase";
    private MutableLiveData<Review> reviewMutableLiveData;
    private  MutableLiveData<Boolean> reviewExistsMutableLiveData;

    public ReviewRepository(Application application) {
        this.application = application;
        reviewMutableLiveData = new MutableLiveData<>();
        reviewExistsMutableLiveData = new MutableLiveData<>();

    }
    public ReviewRepository(){}

    public MutableLiveData<Review> getReviewMutableLiveData() {
        return reviewMutableLiveData;
    }

    public MutableLiveData<Boolean> getReviewExistsMutableLiveData() {
        return reviewExistsMutableLiveData;
    }

    //return array?
    public void GetReviews(String gameId){
        db.collection("reviews")
                .whereEqualTo("gameId", gameId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                //TODO do something with received reviews

                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    public void GetUserReview(String userEmail, String gameId){

        db.collection("reviews")
                .whereEqualTo("userEmail", userEmail)
                .whereEqualTo("gameId", gameId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                reviewExistsMutableLiveData.setValue(true);
                                reviewMutableLiveData.setValue(document.toObject(Review.class));
                                Log.i("test",getReviewExistsMutableLiveData().toString());
                                Log.i("test",getReviewMutableLiveData().toString());
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            reviewExistsMutableLiveData.setValue(false);
                            Log.i(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });Log.i("t","taha jõudis");
    }

    public void PostReview(Review review){
        db.collection("reviews")
                .add(review)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //TODO what to do when review posted
                        Log.i(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void DeleteReview(String Id){
        db.collection("reviews").document(Id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //TODO what to do when review deleted
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
