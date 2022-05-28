package com.example.androidreviewapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidreviewapp.R;
import com.example.androidreviewapp.model.Game;

import java.util.ArrayList;

public class GameDetailsAdapter extends RecyclerView.Adapter<GameDetailsAdapter.GameDetailsViewHolder>{

    private ArrayList<Game> gameList;

    public GameDetailsAdapter(){
        this.gameList = new ArrayList<>();
    }




    @NonNull
    @Override
    public GameDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_game_details, parent, false);


        return new GameDetailsViewHolder(view);




    }

    @Override
    public void onBindViewHolder(@NonNull GameDetailsViewHolder holder, int position) {
        Game game = gameList.get(position);
        String detailedDescription = game.getDetailedDescription();
        String shortDescription = game.getShortDescription();
        String supportedLanguages = game.getSupportedLanguages();
        ArrayList<String> genresList = game.getGenresList();
        Log.i("Tags: ", String.valueOf(genresList));
        //holder.txtDetailedDescription.setText(detailedDescription);
        //holder.txtShortDescription.setText(shortDescription);
        holder.wvSupportedLanguages.loadData(supportedLanguages, "text/html", "UTF-8");
        holder.wvDetailedDescription.loadData(detailedDescription, "text/html", "UTF-8");
        holder.wvShortDescription.loadData(shortDescription, "text/html", "UTF-8");
        holder.wvGameGenres.loadData(genresList.get(position), "text/html", "UTF-8");




    }

    public void setGameDetailsList(final ArrayList<Game> gameList){
        this.gameList = gameList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    static class GameDetailsViewHolder extends RecyclerView.ViewHolder{

        //private final TextView txtDetailedDescription;
        //private final TextView txtShortDescription;
        private final WebView wvSupportedLanguages;
        private final WebView wvDetailedDescription;
        private final WebView wvShortDescription;
        private final WebView wvGameGenres;

        public GameDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            //txtDetailedDescription = itemView.findViewById(R.id.txtDetailedDescription);
            //txtShortDescription = itemView.findViewById(R.id.txtShortDescription);
            wvSupportedLanguages = itemView.findViewById(R.id.wvSupportedLanguages);
            wvDetailedDescription = itemView.findViewById(R.id.wvDetailedDescription);
            wvShortDescription = itemView.findViewById(R.id.wvShortDescription);
            wvGameGenres = itemView.findViewById(R.id.wvGameGenres);

        }
    }
}
