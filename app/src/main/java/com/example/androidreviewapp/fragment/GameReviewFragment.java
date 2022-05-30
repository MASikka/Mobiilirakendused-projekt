package com.example.androidreviewapp.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androidreviewapp.R;
import com.example.androidreviewapp.adapter.AppReviewAdapter;
import com.example.androidreviewapp.model.Review;
import com.example.androidreviewapp.viewmodel.GameDetailsViewModel;
import com.example.androidreviewapp.viewmodel.GameReviewViewModel;

import java.util.ArrayList;

public class GameReviewFragment extends Fragment {

    private GameReviewViewModel gameReviewViewModel;
    private AppReviewAdapter appReviewAdapter;
    private ArrayList<Review> reviewList;
    private String gameId;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.game_review_fragment, container, false);
        setHasOptionsMenu(true);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_app_reviews);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        appReviewAdapter = new AppReviewAdapter();
        recyclerView.setAdapter(appReviewAdapter);

        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GameReviewViewModel gameReviewViewModel = new ViewModelProvider(this).get(GameReviewViewModel.class);

        if (getArguments() != null){
            gameId = getArguments().getString("gameId");
        } else Toast.makeText(getActivity(), "No game id provided, arguments null", Toast.LENGTH_SHORT).show();
        Log.i("onviewcreate",gameId);
            gameReviewViewModel.getReviews(gameId);

        gameReviewViewModel.getReviewsLiveData().observe(getViewLifecycleOwner(), reviews -> appReviewAdapter.setReviewList(reviews));
    }
}