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
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidreviewapp.adapter.GameDetailsAdapter;
import com.example.androidreviewapp.viewmodel.GameDetailsViewModel;
import com.example.androidreviewapp.R;

public class GameDetailsFragment extends Fragment {

    String gameId;
    TextView txtGameId;
    private GameDetailsViewModel gameDetailsViewModel;
    private String detailedDescription;
    public GameDetailsAdapter gameDetailsAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_details_fragment, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_game_details);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);
        gameDetailsAdapter = new GameDetailsAdapter();

        recyclerView.setAdapter(gameDetailsAdapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){
            gameId = getArguments().getString("gameId");
        } else Toast.makeText(getActivity(), "No game id provided, arguments null", Toast.LENGTH_SHORT).show();
        //GameDetailsViewModel gameDetailsViewModel = new ViewModelProvider(this).get(GameDetailsViewModel.class);
        gameDetailsViewModel = new ViewModelProvider(this).get(GameDetailsViewModel.class);
        gameDetailsViewModel.getGameDetails(gameId);
        gameDetailsViewModel.getGameLiveData().observe(this, games -> gameDetailsAdapter.setGameDetailsList(games));

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //txtGameId = view.findViewById(R.id.txtGameId);
   ///     String detailedDescription = gameDetailsViewModel.getGameLiveData().getValue().get(0).getDetailedDescription();
        //txtGameId.setText(gameId);
        //Log.i("description-got", detailedDescription);
        //txtGameId.setText(detailedDescription);
    }
}