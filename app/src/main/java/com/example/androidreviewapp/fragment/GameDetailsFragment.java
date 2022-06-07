package com.example.androidreviewapp.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidreviewapp.SettingsActivity;
import com.example.androidreviewapp.adapter.GameDetailsAdapter;
import com.example.androidreviewapp.viewmodel.GameDetailsViewModel;
import com.example.androidreviewapp.R;
import com.example.androidreviewapp.viewmodel.SearchViewModel;

public class GameDetailsFragment extends Fragment {

    String gameId;
    TextView txtGameId;
    private GameDetailsViewModel gameDetailsViewModel;
    private String detailedDescription;
    public GameDetailsAdapter gameDetailsAdapter;
    NavController navController;
    private ProgressBar gameDetailsLoading;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_details_fragment, container, false);
        setHasOptionsMenu(true);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_game_details);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);
        gameDetailsAdapter = new GameDetailsAdapter();

        recyclerView.setAdapter(gameDetailsAdapter);

        //View gameTagView = inflater.inflate(R.layout.layout_game_details, container, false);
        //        RecyclerView tagRecyclerView = gameTagView.findViewById(R.id.recyclerview_game_tags);


        //LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        //        layoutManager.setReverseLayout(true);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        GameDetailsViewModel gameDetailsViewModel = new ViewModelProvider(this).get(GameDetailsViewModel.class);
        gameDetailsViewModel.getLoggedOutMutableLiveData().observe(this, loggedOut -> {
            if (loggedOut){
                if (getView() != null)Navigation.findNavController(getView())
                        .navigate(R.id.action_gameDetails2Fragment_to_loginFragment);
            }
        });
        if (getArguments() != null){
            gameId = getArguments().getString("gameId");
        } else Toast.makeText(getActivity(), "No game id provided, arguments null", Toast.LENGTH_SHORT).show();
        //GameDetailsViewModel gameDetailsViewModel = new ViewModelProvider(this).get(GameDetailsViewModel.class);
        gameDetailsViewModel = new ViewModelProvider(this).get(GameDetailsViewModel.class);
        gameDetailsViewModel.getGameDetails(gameId);
        gameDetailsViewModel.getGameLiveData().observe(this, games -> gameDetailsAdapter.setGameDetailsList(games));


         */
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_gamedetails,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gameDetailsLoading = view.findViewById(R.id.gameDetailsLoading);
        gameDetailsLoading.setVisibility(View.VISIBLE);
        //txtGameId = view.findViewById(R.id.txtGameId);
   ///     String detailedDescription = gameDetailsViewModel.getGameLiveData().getValue().get(0).getDetailedDescription();
        //txtGameId.setText(gameId);
        //Log.i("description-got", detailedDescription);
        //txtGameId.setText(detailedDescription);
        GameDetailsViewModel gameDetailsViewModel = new ViewModelProvider(this).get(GameDetailsViewModel.class);
        gameDetailsViewModel.getLoggedOutMutableLiveData().observe(getViewLifecycleOwner(), loggedOut -> {
            if (loggedOut){
                if (getView() != null)Navigation.findNavController(getView())
                        .navigate(R.id.action_gameDetails2Fragment_to_loginFragment);
            }
        });
        if (getArguments() != null){
            gameId = getArguments().getString("gameId");
        } else Toast.makeText(getActivity(), "No game id provided, arguments null", Toast.LENGTH_LONG).show();
        gameDetailsViewModel = new ViewModelProvider(this).get(GameDetailsViewModel.class);
        if (!gameDetailsViewModel.hasGameDetails()){
            gameDetailsViewModel.getGameDetails(gameId);
        }
        gameDetailsViewModel.getGameLiveData().observe(getViewLifecycleOwner(), games -> {
            if (games.get(0).getType().equals("empty")){
                Toast.makeText(getActivity(), "This app has no details, going back to search!", Toast.LENGTH_LONG).show();
                //getActivity().finish();
                //getActivity().getSupportFragmentManager().popBackStack();
                getActivity().onBackPressed();


            }
            gameDetailsAdapter.setGameDetailsList(games);
            requireActivity().setTitle(String.format(getString(R.string.details_view_title), games.get(0).getName()));
            gameDetailsLoading.setVisibility(View.GONE);
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.personalreview:
            navController = Navigation.findNavController(getView());
            Bundle args = new Bundle();
            args.putString("gameId",gameId);
            navController.navigate(R.id.action_gameDetails2Fragment_to_personalReviewFragment, args);
            break;

        case R.id.allreviews:
            navController = Navigation.findNavController(getView());
            Bundle arguments = new Bundle();
            arguments.putString("gameId",gameId);
            navController.navigate(R.id.action_gameDetails2Fragment_to_reviewsFragment, arguments);
            break;
        case R.id.settings:
            Intent intent = new Intent(this.getContext(), SettingsActivity.class);
            startActivity(intent);
            break;
        case R.id.logout:
            GameDetailsViewModel gameDetailsViewModel1 = new ViewModelProvider(this).get(GameDetailsViewModel.class);
            gameDetailsViewModel1.logOut();
            break;
    }
        return(super.onOptionsItemSelected(item));
    }


}