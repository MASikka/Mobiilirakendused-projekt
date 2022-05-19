package com.example.androidreviewapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

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
        holder.name.setText(game.getName());
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
        private final TextInputEditText name;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.etGameName);
        }
    }
}
