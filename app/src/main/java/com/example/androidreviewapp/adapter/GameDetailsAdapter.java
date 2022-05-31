package com.example.androidreviewapp.adapter;

import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidreviewapp.R;
import com.example.androidreviewapp.model.Game;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class GameDetailsAdapter extends RecyclerView.Adapter<GameDetailsAdapter.GameDetailsViewHolder>{

    private ArrayList<Game> gameList;

    private RecyclerView.RecycledViewPool viewPool =
            new RecyclerView.RecycledViewPool();

    WebView webView;
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
        String gameName = game.getName();
        //ArrayList<String> developer = game.getDevelopersList();
        ArrayList<String> publisher = game.getPublishersList();
        String systemReq = game.getRecommendedPCRequirements();
        String systemReqMin = game.getMinimumPCRequirements();
        String releaseDate = game.getReleaseDate();
        ArrayList<String> genresList = game.getGenresList();
        Log.i("Tags: ", String.valueOf(genresList));
        //holder.txtDetailedDescription.setText(detailedDescription);
        //holder.txtShortDescription.setText(shortDescription);
        holder.wvSupportedLanguages.loadData(supportedLanguages, "text/html", "UTF-8");
        holder.wvDetailedDescription.loadData(detailedDescription, "text/html", "UTF-8");
        holder.wvShortDescription.loadData(shortDescription, "text/html", "UTF-8");
        //holder.wvGameGenres.loadData(genresList.get(position), "text/html", "UTF-8");
        holder.wvGameName.loadData(gameName, "text/html", "UTF-8");
        // holder.wvDeveloper.loadData(String.valueOf(developer), "text/html", "UTF-8");
        // holder.wvPublisher.loadData(String.valueOf(publisher), "text/html", "UTF-8");
        holder.wvSystemReq.loadData(systemReq, "text/html", "UTF-8");
        holder.wvSystemReqMin.loadData(systemReqMin, "text/html", "UTF-8");
        holder.wvReleaseDate.loadData(releaseDate, "text/html", "UTF-8");

        //genre layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.genreRecyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false
        );
        //dev layout
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(
                holder.devRecyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        //publisher layout
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(
                holder.publisherRecyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);


        //genre
        layoutManager.setInitialPrefetchItemCount(game.getGenresList().size());
        GenreAdapter genreAdapter = new GenreAdapter();
        genreAdapter.setGenreList(game.getGenresList());
        holder.genreRecyclerView.setLayoutManager(layoutManager);
        holder.genreRecyclerView.setAdapter(genreAdapter);
        holder.genreRecyclerView.setRecycledViewPool(viewPool);

        //developers
        layoutManager1.setInitialPrefetchItemCount(game.getDevelopersList().size());
        DeveloperAdapter developerAdapter = new DeveloperAdapter();
        developerAdapter.setDeveloperList(game.getDevelopersList());
        holder.devRecyclerView.setLayoutManager(layoutManager1);
        holder.devRecyclerView.setAdapter(developerAdapter);
        holder.devRecyclerView.setRecycledViewPool(viewPool);

        //publishers
        layoutManager2.setInitialPrefetchItemCount(game.getPublishersList().size());
        PublisherAdapter publisherAdapter = new PublisherAdapter();
        publisherAdapter.setPublishersList(game.getPublishersList());
        holder.publisherRecyclerView.setLayoutManager(layoutManager2);
        holder.publisherRecyclerView.setAdapter(publisherAdapter);
        holder.publisherRecyclerView.setRecycledViewPool(viewPool);


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
        //private final WebView wvGameGenres;
        private final RecyclerView genreRecyclerView;
        private final RecyclerView devRecyclerView;
        private final RecyclerView publisherRecyclerView;
        private final WebView wvGameName;
        // private final WebView wvDeveloper;
        // private final WebView wvPublisher;
        private final WebView wvSystemReq;
        private final WebView wvSystemReqMin;
        private final WebView wvReleaseDate;

        public GameDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            //txtDetailedDescription = itemView.findViewById(R.id.txtDetailedDescription);
            //txtShortDescription = itemView.findViewById(R.id.txtShortDescription);
            wvSupportedLanguages = itemView.findViewById(R.id.wvSupportedLanguages);
            wvDetailedDescription = itemView.findViewById(R.id.wvDetailedDescription);
            wvShortDescription = itemView.findViewById(R.id.wvShortDescription);
            //wvGameGenres = itemView.findViewById(R.id.wvGameGenres);
            genreRecyclerView = itemView.findViewById(R.id.recyclerview_game_genres);
            devRecyclerView = itemView.findViewById(R.id.recyclerview_game_dev);
            publisherRecyclerView = itemView.findViewById(R.id.recyclerview_game_publisher);
            wvGameName = itemView.findViewById(R.id.wvGameName);
            //wvDeveloper = itemView.findViewById(R.id.wvDeveloper);
            //wvPublisher = itemView.findViewById(R.id.wvPublisher);
            wvSystemReq = itemView.findViewById(R.id.wvSystemReq);
            wvSystemReqMin = itemView.findViewById(R.id.wvSystemReqMin);
            wvReleaseDate = itemView.findViewById(R.id.wvReleaseDate);


        }
    }

}
