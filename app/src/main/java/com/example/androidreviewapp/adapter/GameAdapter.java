package com.example.androidreviewapp.adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidreviewapp.R;
import com.example.androidreviewapp.model.Game;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private ArrayList<Game> gameList;

    public GameAdapter() {
        this.gameList = new ArrayList<>();
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game game = gameList.get(position);
        String gameId = game.getId();

        Log.i("onbindviewholder", "teeb onbindviewholderit");

        Bundle args = new Bundle();

        args.putString("gameId", gameId);

        holder.nameButton.setText(game.getName());
        holder.nameButton.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_searchFragment_to_gameDetails2Fragment, args)
        );
    }

    public void setGameList(final ArrayList<Game> gameList) {
        this.gameList = gameList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    static class GameViewHolder extends RecyclerView.ViewHolder{
        private final Button nameButton;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            nameButton = itemView.findViewById(R.id.btnGameDetails);
        }
    }
}
