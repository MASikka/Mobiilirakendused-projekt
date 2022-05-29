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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidreviewapp.adapter.GameAdapter;
import com.example.androidreviewapp.R;
import com.example.androidreviewapp.repository.FirebaseRepository;
import com.example.androidreviewapp.viewmodel.SearchViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

public class SearchFragment extends Fragment {

    String game;
    NavController navController;
    TextInputEditText gameText;
    TextView searchAmount;
    Button searchButton;
    ProgressBar searchLoading;
    private GameAdapter gameAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        setHasOptionsMenu(true);
        if (getArguments() != null){
            requireActivity().setTitle("Games Search - '" + getArguments().getString("gameName") + "'");
        } else {
            requireActivity().setTitle("Games Search");
        }
        //requireActivity().setTitle("Games Search");
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);
        gameAdapter = new GameAdapter();

        recyclerView.setAdapter(gameAdapter);

        return view;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gameText = view.findViewById(R.id.etGameNameInput);
        searchAmount = view.findViewById(R.id.txtSearchAmount);
        searchLoading = view.findViewById(R.id.searchLoading);
        searchLoading.setVisibility(View.VISIBLE);

        if (getArguments() != null){
            game = getArguments().getString("gameName");
            gameText.setText(game);
        } else {
            searchLoading.setVisibility(View.GONE);
        }



        SearchViewModel searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        searchViewModel.getLoggedOutMutableLiveData().observe(getViewLifecycleOwner(), loggedOut -> {
            if (loggedOut){
                if (getView() != null)Navigation.findNavController(getView())
                        .navigate(R.id.action_searchFragment_to_loginFragment);
            }
        });
        if (!searchViewModel.hasGameNames()){
            searchViewModel.getGameSearch(game);
        }
        searchAmount.setText(String.format(getString(R.string.search_amount), "0"));
        searchViewModel.getGameLiveData().observe(getViewLifecycleOwner(), games -> {
            Log.i("observe", "game search");
            gameAdapter.setGameList(games);
            searchAmount.setText(String.format(getString(R.string.search_amount), String.valueOf(games.size())));
            searchLoading.setVisibility(View.GONE);
        });
        searchButton = view.findViewById(R.id.btnSearch);

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

        if (savedInstanceState == null){
            Log.i("savedInstanceState", "not saved");
        } else {
            Log.i("savedInstanceState", "saved");
        }

        if (getArguments() != null){
            game = getArguments().getString("gameName");
            Log.i("SearchFragment", game);
        } else Toast.makeText(getActivity(), "No game name provided, arguments null", Toast.LENGTH_SHORT).show();

        /*
        SearchViewModel searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        searchViewModel.getLoggedOutMutableLiveData().observe(this, loggedOut -> {
            Log.i("search",".observe"+loggedOut.toString());
            if (loggedOut){
                if (getView() != null)Navigation.findNavController(getView())
                        .navigate(R.id.action_searchFragment_to_loginFragment);
            }
        });
        searchViewModel.getGameSearch(game);
        searchViewModel.getGameLiveData().observe(this, games -> gameAdapter.setGameList(games));

 */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {


        case R.id.settings:

            return(true);

        case R.id.logout:
            SearchViewModel searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
            searchViewModel.logOut();
    }
        return(super.onOptionsItemSelected(item));
    }

}