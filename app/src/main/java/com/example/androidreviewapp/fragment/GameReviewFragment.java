package com.example.androidreviewapp.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidreviewapp.R;
import com.example.androidreviewapp.SettingsActivity;
import com.example.androidreviewapp.adapter.AppReviewAdapter;
import com.example.androidreviewapp.model.Review;
import com.example.androidreviewapp.viewmodel.GameReviewViewModel;
import com.example.androidreviewapp.viewmodel.PersonalReviewViewModel;

import java.util.ArrayList;

public class GameReviewFragment extends Fragment {

    private GameReviewViewModel gameReviewViewModel;
    private AppReviewAdapter appReviewAdapter;
    private AppReviewAdapter steamReviewAdapter;
    private ArrayList<Review> reviewList;
    private String gameId;
    private Button reviewSwitchButton;
    private RecyclerView appRecyclerView;
    private RecyclerView steamRecyclerView;
    private Boolean isSteamReviews;
    private TextView reviewCounterTextView;
    private int steamReviewCounter;
    private int appReviewCounter;
    private ProgressBar reviewsLoading;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.game_review_fragment, container, false);
        setHasOptionsMenu(true);
        appRecyclerView = view.findViewById(R.id.recyclerview_app_reviews);
        appRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        appReviewAdapter = new AppReviewAdapter();
        appRecyclerView.setAdapter(appReviewAdapter);

        steamRecyclerView = view.findViewById(R.id.recyclerview_steam_reviews);
        steamRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        steamReviewAdapter = new AppReviewAdapter();
        steamRecyclerView.setAdapter(steamReviewAdapter);

        isSteamReviews = true;
        reviewSwitchButton = view.findViewById(R.id.btnSwitchReviews);
        reviewSwitchButton.setText(getString(R.string.app_reviews));

        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("GameReviews Vaatel", "oled vaatel onviewcreated");

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        String langPref = sharedPref.getString(SettingsActivity.LANGUAGE_PREF_CHOICE,"-1");
        String filterPref = sharedPref.getString(SettingsActivity.FILTER_PREF_CHOICE, "-1");


        gameReviewViewModel = new ViewModelProvider(this).get(GameReviewViewModel.class);
        gameReviewViewModel.getLoggedOutMutableLiveData().observe(getViewLifecycleOwner(), loggedOut -> {
            if (loggedOut){
                if (getView() != null) Navigation.findNavController(getView())
                        .navigate(R.id.action_reviewsFragment_to_loginFragment);
            }
        });
        reviewsLoading = view.findViewById(R.id.reviewsLoading);
        reviewsLoading.setVisibility(View.VISIBLE);

        reviewSwitchButton = view.findViewById(R.id.btnSwitchReviews);
        reviewCounterTextView = view.findViewById(R.id.txtReviewCounter);
        reviewCounterTextView.setText(String.format(getString(R.string.steam_reviews_found_count), "0"));
        requireActivity().setTitle(getString(R.string.steam_reviews_recent_title));

        GameReviewViewModel gameReviewViewModel = new ViewModelProvider(this).get(GameReviewViewModel.class);

        if (getArguments() != null){
            gameId = getArguments().getString("gameId");
        } else Toast.makeText(getActivity(), "No game id provided, arguments null", Toast.LENGTH_SHORT).show();
        Log.i("onviewcreate",gameId);
            gameReviewViewModel.getReviews(gameId);

        gameReviewViewModel.getReviewsLiveData().observe(getViewLifecycleOwner(), reviews -> {
            appReviewAdapter.setReviewList(reviews);
            appReviewCounter = reviews.size();
        });

        gameReviewViewModel.getSteamReviews(gameId, langPref, filterPref);

        gameReviewViewModel.getSteamReviewsLiveData().observe(getViewLifecycleOwner(), steamReviews -> {
            steamReviewAdapter.setReviewList(steamReviews);
            steamReviewCounter = steamReviews.size();
            reviewCounterTextView.setText(String.format(getString(R.string.steam_reviews_found_count), String.valueOf(steamReviewCounter)));
            reviewsLoading.setVisibility(View.GONE);
        });

        reviewSwitchButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isSteamReviews){
                            isSteamReviews = false;
                            reviewSwitchButton.setText(getString(R.string.steam_reviews));
                            appRecyclerView.setVisibility(View.VISIBLE);
                            steamRecyclerView.setVisibility(View.GONE);
                            reviewCounterTextView.setText(String.format(getString(R.string.app_reviews_found_count), String.valueOf(appReviewCounter)));
                            requireActivity().setTitle(getString(R.string.app_reviews_title));
                        } else {
                            isSteamReviews = true;
                            reviewSwitchButton.setText(getString(R.string.app_reviews));
                            appRecyclerView.setVisibility(View.GONE);
                            steamRecyclerView.setVisibility(View.VISIBLE);
                            reviewCounterTextView.setText(String.format(getString(R.string.steam_reviews_found_count), String.valueOf(steamReviewCounter)));
                            requireActivity().setTitle(getString(R.string.steam_reviews_recent_title));
                        }
                    }
                }
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        String langPref = sharedPref.getString(SettingsActivity.LANGUAGE_PREF_CHOICE,"-1");
        String filterPref = sharedPref.getString(SettingsActivity.FILTER_PREF_CHOICE, "-1");
        GameReviewViewModel gameReviewViewModel = new ViewModelProvider(this).get(GameReviewViewModel.class);
        gameReviewViewModel.getSteamReviews(gameId, langPref, filterPref);
        gameReviewViewModel.getSteamReviewsLiveData().observe(getViewLifecycleOwner(), steamReviews -> {
            steamReviewAdapter.setReviewList(steamReviews);
            steamReviewCounter = steamReviews.size();
            reviewCounterTextView.setText(String.format(getString(R.string.steam_reviews_found_count), String.valueOf(steamReviewCounter)));
            reviewsLoading.setVisibility(View.GONE);
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.settings:
            Intent intent = new Intent(this.getContext(), SettingsActivity.class);
            startActivity(intent);
            break;
        case R.id.logout:
            gameReviewViewModel = new ViewModelProvider(this).get(GameReviewViewModel.class);
            gameReviewViewModel.logOut();
            break;
    }
        return(super.onOptionsItemSelected(item));
    }
}