package com.example.androidreviewapp.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.androidreviewapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.androidreviewapp.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebaseRepository {
    private static final String TAG = "FIREBASE:";
    private Application application;
    private  FirebaseAuth firebaseAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private  MutableLiveData<FirebaseUser> userMutableLiveData;
    private  MutableLiveData<Boolean> loggedOutMutableLiveData;
    private  MutableLiveData<ArrayList<User>> userLiveData;
    private  ArrayList<User> userArrayList = new ArrayList<>();

    public FirebaseRepository(Application application) {
        this.application = application;
        firebaseAuth = FirebaseAuth.getInstance();
        userMutableLiveData = new MutableLiveData<>();
        loggedOutMutableLiveData = new MutableLiveData<>();
        userLiveData = new MutableLiveData<>();

        if (firebaseAuth.getCurrentUser() != null){
            userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
            loggedOutMutableLiveData.postValue(false);
            loadUserData();
        }else{
            loggedOutMutableLiveData.postValue(true);
        }
    }

    public FirebaseRepository() {
    }

    //register user
    public void userRegistration(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(application.getMainExecutor(),
                task -> {
                    if (task.isSuccessful()){
                        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(application, "Check your email for verification", Toast.LENGTH_SHORT).show();
                                    if (firebaseAuth.getCurrentUser() != null) {
                                        String userId = firebaseAuth.getCurrentUser().getUid();
                                        DocumentReference documentReference = db.collection("users").document(userId);
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("email", email);
                                        documentReference.set(user).
                                                addOnSuccessListener(aVoid -> Log.i(TAG, "onSuccess: user data was saved"))
                                                .addOnFailureListener(e -> Log.i(TAG, "onFailure: ERROR writing db document", e));
                                        userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                                    } else {
                                        Toast.makeText(application, application.getString(R.string.error, task.getException()), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(application,  task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });
    }

    //login user
    public void login(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(application.getMainExecutor(),
                task -> {
                    if (task.isSuccessful()){
                        userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                    }else{
                        Toast.makeText(application, application.getString(R.string.error,task.getException().getMessage())
                                , Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //user log out
    public void logout(){
        firebaseAuth.signOut();
        loggedOutMutableLiveData.postValue(true);
    }

    //loads the user data from fireStore
    public void loadUserData() {
        if (firebaseAuth.getCurrentUser() != null){
            String uid = firebaseAuth.getCurrentUser().getUid();
            DocumentReference doc = db.collection("users").document(uid);
            doc.get().addOnSuccessListener(documentSnapshot -> {
                User user = documentSnapshot.toObject(User.class);
                userArrayList.add(user);
                userLiveData.setValue(userArrayList);
            }).addOnFailureListener(
                    e ->{
                        Toast.makeText(application, application.getString(R.string.error,e.getMessage())
                                , Toast.LENGTH_SHORT).show();
                    }
            );
        }
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutMutableLiveData() {
        return loggedOutMutableLiveData;
    }

    public MutableLiveData<ArrayList<User>> getUserLiveData() {
        return userLiveData;
    }
}
