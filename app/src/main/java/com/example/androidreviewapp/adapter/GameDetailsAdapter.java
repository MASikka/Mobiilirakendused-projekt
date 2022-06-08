package com.example.androidreviewapp.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidreviewapp.LinePagerIndicatorDecoration;
import com.example.androidreviewapp.R;
import com.example.androidreviewapp.model.Game;

import java.util.ArrayList;

public class GameDetailsAdapter extends RecyclerView.Adapter<GameDetailsAdapter.GameDetailsViewHolder>{

    private ArrayList<Game> gameList;

    private RecyclerView.RecycledViewPool viewPool =
            new RecyclerView.RecycledViewPool();

    WebView webView;
    public GameDetailsAdapter(){
        this.gameList = new ArrayList<>();
    }


    private Context context;
    public GameDetailsAdapter(Context context){
        this.gameList = new ArrayList<>();
        this.context = context;
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
        String gameName = game.getName();
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
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            holder.wvDetailedDescription.getSettings().setDefaultFontSize(40);
        } else {
            holder.wvDetailedDescription.getSettings().setDefaultFontSize(20);
        }

        holder.wvDetailedDescription.loadData(detailedDescription, "text/html", "UTF-8");

        // short description
        holder.wvShortDescription.getSettings().setLoadWithOverviewMode(true);
        holder.wvShortDescription.getSettings().setUseWideViewPort(true);
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            holder.wvShortDescription.getSettings().setDefaultFontSize(40);
        } else {
            holder.wvShortDescription.getSettings().setDefaultFontSize(20);
        }

        holder.wvShortDescription.loadData(shortDescription, "text/html", "UTF-8");

        // supported languages
        holder.wvSupportedLanguages.getSettings().setLoadWithOverviewMode(true);
        holder.wvSupportedLanguages.getSettings().setUseWideViewPort(true);
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            holder.wvSupportedLanguages.getSettings().setDefaultFontSize(40);
        } else {
            holder.wvSupportedLanguages.getSettings().setDefaultFontSize(20);
        }

        holder.wvSupportedLanguages.loadData(supportedLanguages, "text/html", "UTF-8");

        // recommended requirements
        holder.wvSystemReq.getSettings().setLoadWithOverviewMode(true);
        holder.wvSystemReq.getSettings().setUseWideViewPort(true);
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            holder.wvSystemReq.getSettings().setDefaultFontSize(40);
        } else{
            holder.wvSystemReq.getSettings().setDefaultFontSize(20);
        }

        holder.wvSystemReq.loadData(systemReq, "text/html", "UTF-8");


        // minimum requirements
        holder.wvSystemReqMin.getSettings().setLoadWithOverviewMode(true);
        holder.wvSystemReqMin.getSettings().setUseWideViewPort(true);
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            holder.wvSystemReqMin.getSettings().setDefaultFontSize(40);
        } else {
            holder.wvSystemReqMin.getSettings().setDefaultFontSize(20);
        }

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



        //if(gameType.equals("episode")) {
        //            holder.txtDev.setText("");
        //        } else {
        //
        //        }


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

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(holder.screenshotRecyclerView);

        holder.screenshotRecyclerView.addItemDecoration(new LinePagerIndicatorDecoration());


        //textview

        holder.txtGameType.setText(R.string.app_type);
        holder.txtAppType.setText(gameType);
        holder.txtGameName.setText(gameName);
        holder.txtGameNameHeading.setText(R.string.game_name);
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
            holder.txtMetaScoreGreen.setVisibility(View.GONE);
            holder.txtMetaScoreYellow.setVisibility(View.GONE);
            holder.txtMetaScoreRed.setVisibility(View.GONE);
            holder.txtMetaCritic.setVisibility(View.GONE);
        } else {
            //holder.txtMetaScoreGreen.setText(metaScore);
            if (Integer.parseInt(metaScore) > 74){
                holder.txtMetaScoreGreen.setText(metaScore);
                holder.txtMetaScoreYellow.setVisibility(View.GONE);
                holder.txtMetaScoreRed.setVisibility(View.GONE);
            } else if (Integer.parseInt(metaScore) > 50 && Integer.parseInt(metaScore) <= 74){
                holder.txtMetaScoreYellow.setText(metaScore);
                holder.txtMetaScoreGreen.setVisibility(View.GONE);
                holder.txtMetaScoreRed.setVisibility(View.GONE);
            } else {
                holder.txtMetaScoreRed.setText(metaScore);
                holder.txtMetaScoreYellow.setVisibility(View.GONE);
                holder.txtMetaScoreGreen.setVisibility(View.GONE);
            }
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
        private final TextView txtGameType;
        private final TextView txtInitialPrice;
        private final TextView txtFinalPrice;
        private final TextView txtReleaseDate;
        private final TextView txtMetaScoreGreen;
        private final TextView txtMetaScoreYellow;
        private final TextView txtMetaScoreRed;
        private final TextView isWindowsSupported;
        private final TextView isLinuxSupported;
        private final TextView isMacSupported;
        private final TextView txtSysReq;
        private final TextView txtMetaCritic;
        private final TextView txtAppType;
        private final TextView txtGameName;
        private final TextView txtGameNameHeading;


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
            txtGameType = itemView.findViewById(R.id.txtGameName);
            txtInitialPrice = itemView.findViewById(R.id.txtInitial);
            txtFinalPrice = itemView.findViewById(R.id.txtFinal);
            txtReleaseDate = itemView.findViewById(R.id.txtRelease);
            txtMetaScoreGreen = itemView.findViewById(R.id.txtMetaScoreGreen);
            txtMetaScoreYellow = itemView.findViewById(R.id.txtMetaScoreYellow);
            txtMetaScoreRed = itemView.findViewById(R.id.txtMetaScoreRed);
            isWindowsSupported = itemView.findViewById(R.id.isWindowsSupported);
            isMacSupported = itemView.findViewById(R.id.isMacSupported);
            isLinuxSupported = itemView.findViewById(R.id.isLinuxSupported);
            txtSysReq = itemView.findViewById(R.id.txtSysReq);
            txtMetaCritic = itemView.findViewById(R.id.txtMetacritic);
            txtAppType = itemView.findViewById(R.id.txtGameType);
            txtGameName = itemView.findViewById(R.id.txtGameName1);
            txtGameNameHeading = itemView.findViewById(R.id.txtGameName2);
        }
    }

}
