package com.example.androidreviewapp.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidreviewapp.R;
import com.example.androidreviewapp.model.Review;
import com.example.androidreviewapp.viewmodel.PersonalReviewViewModel;
import com.example.androidreviewapp.viewmodel.SearchViewModel;

public class PersonalReviewFragment extends Fragment {

    private PersonalReviewViewModel personalReviewViewModel;
    private String gameId;
    private String userEmail;
    private String reviewText;
    private Boolean recommended;
    private TextView ETReviewText;
    private CheckBox CBRecommended;
    private Button btnDelete;
    private Boolean reviewExists;
    NavController navController;
    private Review currentReview;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        PersonalReviewViewModel personalReviewViewModel = new ViewModelProvider(this).get(PersonalReviewViewModel.class);

        View view = inflater.inflate(R.layout.personal_review_fragment, container, false);
        btnDelete = view.findViewById(R.id.btn_delete_review);
        btnDelete.setOnClickListener(view1 -> {
            if(reviewExists==false){
                Toast.makeText(getActivity(), "No review to delete", Toast.LENGTH_SHORT).show();
            }if(reviewExists==true){
            personalReviewViewModel.deleteReview(gameId);
                Toast.makeText(getActivity(), "Review deleted", Toast.LENGTH_SHORT).show();
                reviewExists = false;
                btnDelete = view.findViewById(R.id.btn_delete_review);
                btnDelete.setVisibility(View.GONE);
                ETReviewText = view.findViewById(R.id.txt_personal_review);
                ETReviewText.setText("");
                CBRecommended = view.findViewById(R.id.checkBox_recommend);
                CBRecommended.setChecked(false);
            }
        });


        view.findViewById(R.id.btn_submit_review).setOnClickListener(view1 -> {
        ETReviewText = view.findViewById(R.id.txt_personal_review);
        CBRecommended = view.findViewById(R.id.checkBox_recommend);
        reviewText = ETReviewText.getText().toString().trim();
        recommended = CBRecommended.isChecked();
        if(TextUtils.isEmpty(reviewText)){
            Toast.makeText(getActivity(), "Review cannot be empty", Toast.LENGTH_SHORT).show();
        }if(TextUtils.isEmpty(userEmail)){
                Toast.makeText(getActivity(), "Not signed in", Toast.LENGTH_SHORT).show();
            }if(TextUtils.isEmpty(gameId)){
                Toast.makeText(getActivity(), "No game provided", Toast.LENGTH_SHORT).show();
            }
        else{
            Review review = new Review(reviewText,recommended,userEmail,gameId);
            personalReviewViewModel.postReview(review);
            reviewExists = true;
                personalReviewViewModel.checkIfReviewExists(gameId);

                        personalReviewViewModel.getReviewMutableLiveData().observe(getViewLifecycleOwner(), data -> {
                            currentReview = data;
                            reviewExists = true;
                            ETReviewText = view.findViewById(R.id.txt_personal_review);
                            ETReviewText.setText(currentReview.getReviewText());
                            CBRecommended = view.findViewById(R.id.checkBox_recommend);
                            CBRecommended.setChecked(currentReview.getRecommends());
                            btnDelete = view.findViewById(R.id.btn_delete_review);
                            btnDelete.setVisibility(View.VISIBLE);
                        });
            Toast.makeText(getActivity(), "Review posted", Toast.LENGTH_SHORT).show();
            }
        });

        personalReviewViewModel.getReviewExistsMutableLiveData().observe(getViewLifecycleOwner(), exists -> {
            if (exists==false){
                reviewExists = false;
                btnDelete = view.findViewById(R.id.btn_delete_review);
                btnDelete.setVisibility(View.GONE);

            }else{
                personalReviewViewModel.getReviewMutableLiveData().observe(getViewLifecycleOwner(), data -> {
                    currentReview = data;
                    reviewExists = true;
                    ETReviewText = view.findViewById(R.id.txt_personal_review);
                    ETReviewText.setText(currentReview.getReviewText());
                    CBRecommended = view.findViewById(R.id.checkBox_recommend);
                    CBRecommended.setChecked(currentReview.getRecommends());
                });
            }
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
     userEmail=personalReviewViewModel.getUserEmail();
        personalReviewViewModel.checkIfReviewExists(gameId);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        PersonalReviewViewModel personalReviewViewModel = new ViewModelProvider(this).get(PersonalReviewViewModel.class);
        personalReviewViewModel.getLoggedOutMutableLiveData().observe(getViewLifecycleOwner(), loggedOut -> {
            if (loggedOut){
                if (getView() != null)Navigation.findNavController(getView())
                        .navigate(R.id.action_personalReviewFragment_to_loginFragment);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {


        case R.id.settings:

            break;

        case R.id.logout:
            PersonalReviewViewModel personalReviewViewModel = new ViewModelProvider(this).get(PersonalReviewViewModel.class);
            personalReviewViewModel.logOut();
            break;
    }
        return(super.onOptionsItemSelected(item));
    }


}