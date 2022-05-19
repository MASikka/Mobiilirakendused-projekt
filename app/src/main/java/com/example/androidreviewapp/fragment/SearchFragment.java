package com.example.androidreviewapp.fragment;

import androidx.lifecycle.ViewModelProvider;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.androidreviewapp.GameAdapter;
import com.example.androidreviewapp.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

public class SearchFragment extends Fragment {

    String game;
    NavController navController;
    TextInputEditText gameText;
    Button searchButton;
    private GameAdapter gameAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);

        requireActivity().setTitle("Games Search");

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);
        gameAdapter = new GameAdapter();

        recyclerView.setAdapter(gameAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchButton = view.findViewById(R.id.btnSearch);
        gameText = view.findViewById(R.id.etGameNameInput);
        searchButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        navController = Navigation.findNavController(view);

                        String name;
                        Log.i("gameText", gameText.getText().toString());
                        name = gameText.getText().toString().toLowerCase(Locale.getDefault()).trim();

                        Bundle args = new Bundle();
                        args.putString("gameName", name);
                        navController.navigate(R.id.action_searchFragment_self, args);
                    }
                }
        );
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            game = getArguments().getString("gameName");
            Log.i("SearchFragment", game);
        } else Toast.makeText(getActivity(), "No game name provided, arguments null", Toast.LENGTH_SHORT).show();

        SearchViewModel searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        searchViewModel.getGameSearch(game);
        searchViewModel.getGameLiveData().observe(this, games -> gameAdapter.setGameList(games));
    }
}