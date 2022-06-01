package com.example.androidreviewapp.adapter;

import android.media.Image;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidreviewapp.R;
import com.example.androidreviewapp.model.Game;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

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
        String gameType = game.getType();
        String initialPrice = "none";
        if (game.getInitialPrice() != null){
            initialPrice = game.getInitialPrice();
        }
        String finalPrice = game.getFinalPrice();
        String metaScore = game.getMetacriticScore();
        String systemReq = game.getRecommendedPCRequirements();
        String systemReqMin = game.getMinimumPCRequirements();
        String releaseDate = game.getReleaseDate();
        Boolean isWindowsSupported = game.isWindows();
        Boolean isMacSupported = game.isMac();
        Boolean isLinuxSupported = game.isLinux();
        ArrayList<String> genresList = game.getGenresList();
        Log.i("Tags: ", String.valueOf(genresList));


        // detailed description
        holder.wvDetailedDescription.getSettings().setLoadWithOverviewMode(true);
        holder.wvDetailedDescription.getSettings().setUseWideViewPort(true);
        holder.wvDetailedDescription.getSettings().setDefaultFontSize(40);
        holder.wvDetailedDescription.loadData(detailedDescription, "text/html", "UTF-8");

        // short description
        holder.wvShortDescription.getSettings().setLoadWithOverviewMode(true);
        holder.wvShortDescription.getSettings().setUseWideViewPort(true);
        holder.wvShortDescription.getSettings().setDefaultFontSize(40);
        holder.wvShortDescription.loadData(shortDescription, "text/html", "UTF-8");

        // supported languages
        holder.wvSupportedLanguages.getSettings().setLoadWithOverviewMode(true);
        holder.wvSupportedLanguages.getSettings().setUseWideViewPort(true);
        holder.wvSupportedLanguages.getSettings().setDefaultFontSize(40);
        holder.wvSupportedLanguages.loadData(supportedLanguages, "text/html", "UTF-8");

        // recommended requirements
        holder.wvSystemReq.getSettings().setLoadWithOverviewMode(true);
        holder.wvSystemReq.getSettings().setUseWideViewPort(true);
        holder.wvSystemReq.getSettings().setDefaultFontSize(40);
        holder.wvSystemReq.loadData(systemReq, "text/html", "UTF-8");

        // minimum requirements
        holder.wvSystemReqMin.getSettings().setLoadWithOverviewMode(true);
        holder.wvSystemReqMin.getSettings().setUseWideViewPort(true);
        holder.wvSystemReqMin.getSettings().setDefaultFontSize(40);
        holder.wvSystemReqMin.loadData(systemReqMin, "text/html", "UTF-8");




        Log.i("initial: ", game.getInitialPrice());

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

        //screenshot layout
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(
                holder.screenshotRecyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);


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

        //screenshots
        layoutManager3.setInitialPrefetchItemCount(game.getScreenshotsList().size());
        ScreenshotAdapter screenshotAdapter = new ScreenshotAdapter();
        screenshotAdapter.setScreenshotsList(game.getScreenshotsList());
        holder.screenshotRecyclerView.setLayoutManager(layoutManager3);
        holder.screenshotRecyclerView.setAdapter(screenshotAdapter);
        holder.screenshotRecyclerView.setRecycledViewPool(viewPool);


        //textview
        holder.txtGameName.setText("App type: " + gameType);
        Log.i("Init: ", initialPrice);
        if(initialPrice.equals(":") || TextUtils.isEmpty(initialPrice)) {
            holder.txtInitialPrice.setText(R.string.initialprice_unavailable);
        } else {
            holder.txtInitialPrice.setText(initialPrice);
        }
        //holder.txtInitialPrice.setText(initialPrice);
        holder.txtFinalPrice.setText(finalPrice);
        holder.txtReleaseDate.setText(releaseDate);

        Log.i("Meta: ", metaScore);

        if (metaScore.equals("")) {
            holder.txtMetaScore.setVisibility(View.GONE);
            holder.txtMetaCritic.setVisibility(View.GONE);
        } else {
            holder.txtMetaScore.setText(metaScore);
        }

        if(isWindowsSupported){
            holder.isWindowsSupported.setText(R.string.windows_supported);
        } else {
            holder.isWindowsSupported.setText(R.string.windows_not_supported);
        }
        if(isMacSupported){
            holder.isMacSupported.setText(R.string.Mac_supported);
        } else {
            holder.isMacSupported.setText(R.string.mac_no_supported);
        }
        if(isLinuxSupported){
            holder.isLinuxSupported.setText(R.string.linux_supported);

        } else {
            holder.isLinuxSupported.setText(R.string.linux_not_supported);
        }



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

        private final WebView wvSupportedLanguages;
        private final WebView wvDetailedDescription;
        private final WebView wvShortDescription;
        private final RecyclerView genreRecyclerView;
        private final RecyclerView devRecyclerView;
        private final RecyclerView publisherRecyclerView;
        private final RecyclerView screenshotRecyclerView;
        private final WebView wvSystemReq;
        private final WebView wvSystemReqMin;
        private final TextView txtGameName;
        private final TextView txtInitialPrice;
        private final TextView txtFinalPrice;
        private final TextView txtReleaseDate;
        private final TextView txtMetaScore;
        private final TextView isWindowsSupported;
        private final TextView isLinuxSupported;
        private final TextView isMacSupported;
        private final TextView txtMetaCritic;


        public GameDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            wvSupportedLanguages = itemView.findViewById(R.id.wvSupportedLanguages);
            wvDetailedDescription = itemView.findViewById(R.id.wvDetailedDescription);
            wvShortDescription = itemView.findViewById(R.id.wvShortDescription);
            genreRecyclerView = itemView.findViewById(R.id.recyclerview_game_genres);
            devRecyclerView = itemView.findViewById(R.id.recyclerview_game_dev);
            publisherRecyclerView = itemView.findViewById(R.id.recyclerview_game_publisher);
            screenshotRecyclerView = itemView.findViewById(R.id.recyclerview_game_screenshots);
            wvSystemReq = itemView.findViewById(R.id.wvSystemReq);
            wvSystemReqMin = itemView.findViewById(R.id.wvSystemReqMin);
            txtGameName = itemView.findViewById(R.id.txtGameName);
            txtInitialPrice = itemView.findViewById(R.id.txtInitial);
            txtFinalPrice = itemView.findViewById(R.id.txtFinal);
            txtReleaseDate = itemView.findViewById(R.id.txtRelease);
            txtMetaScore = itemView.findViewById(R.id.txtMetaScore);
            isWindowsSupported = itemView.findViewById(R.id.isWindowsSupported);
            isMacSupported = itemView.findViewById(R.id.isMacSupported);
            isLinuxSupported = itemView.findViewById(R.id.isLinuxSupported);
            txtMetaCritic = itemView.findViewById(R.id.txtMetacritic);
        }
    }

}
