package com.example.androidreviewapp.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidreviewapp.R;
import com.example.androidreviewapp.model.Review;
import com.example.androidreviewapp.viewmodel.PersonalReviewViewModel;

public class PersonalReviewFragment extends Fragment {

    private PersonalReviewViewModel personalReviewViewModel;
    private String gameId;
    private String userEmail;
    private String reviewText;
    private Boolean recommended;
    private TextView ETReviewText;
    private CheckBox CBRecommended;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.personal_review_fragment, container, false);
        view.findViewById(R.id.btn_submit_review).setOnClickListener(view1 -> {
        ETReviewText = view.findViewById(R.id.txt_personal_review);
        CBRecommended = view.findViewById(R.id.checkBox_recommend);
        reviewText = ETReviewText.getText().toString().trim();
        recommended = CBRecommended.isChecked();
        if(TextUtils.isEmpty(reviewText)){
            Toast.makeText(getActivity(), "Review cannot be empty", Toast.LENGTH_SHORT).show();
        }else{
            PersonalReviewViewModel personalReviewViewModel = new ViewModelProvider(this).get(PersonalReviewViewModel.class);
            Review review = new Review(reviewText,recommended,userEmail,gameId);
            personalReviewViewModel.postReview(review);
            Toast.makeText(getActivity(), "Review posted", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(getView())
                    .navigate(R.id.action_personalReviewFragment_to_searchFragment);}
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            gameId = getArguments().getString("gameId");
        } else Toast.makeText(getActivity(), "No game provided", Toast.LENGTH_SHORT).show();
        PersonalReviewViewModel personalReviewViewModel = new ViewModelProvider(this).get(PersonalReviewViewModel.class);

        personalReviewViewModel.getLoggedOutMutableLiveData().observe(this, loggedOut -> {
            if (loggedOut){
                if (getView() != null) Navigation.findNavController(getView())
                        .navigate(R.id.action_personalReviewFragment_to_loginFragment);
            }
        });
        userEmail=personalReviewViewModel.getUserEmail();
        personalReviewViewModel.checkIfReviewExists(userEmail,gameId);
        personalReviewViewModel.getReviewExistsMutableLiveData().observe(this, exists -> {
            Log.i("search",".observe"+exists.toString());
            if (exists==false){
                Log.i("tag","does not exist");
            }else{
                Log.i("tag","exists");
            }
        });
    }

//TODO create menu
    /*@Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_gamedetails,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }*/



}