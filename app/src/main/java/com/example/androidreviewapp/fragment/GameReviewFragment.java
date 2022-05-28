package com.example.androidreviewapp.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidreviewapp.R;
import com.example.androidreviewapp.viewmodel.GameReviewViewModel;

public class GameReviewFragment extends Fragment {

    private GameReviewViewModel mViewModel;

    public static GameReviewFragment newInstance() {
        return new GameReviewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.game_review_fragment, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_game_reviews);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(GameReviewViewModel.class);
        // TODO: Use the ViewModel
    }

}