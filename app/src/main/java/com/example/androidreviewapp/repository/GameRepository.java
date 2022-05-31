package com.example.androidreviewapp.repository;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;

import com.example.androidreviewapp.R;
import com.example.androidreviewapp.model.Game;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class GameRepository {
    private static final String SEARCH_URL = "http://api.steampowered.com/ISteamApps/GetAppList/v2/?format=json";
    private static final String DETAILS_URL = "https://store.steampowered.com/api/appdetails?appids=%s&language=english";
    private final Application application;
    private final MutableLiveData<ArrayList<Game>> gameLiveData;
    private final MutableLiveData<ArrayList<Game>> searchResultLiveData;
    private final ArrayList<Game> arrayList = new ArrayList<>();
    private final ArrayList<Game> searchResultList = new ArrayList<>();

    public GameRepository(Application application) {
        this.application = application;
        this.gameLiveData = new MutableLiveData<>();
        this.searchResultLiveData = new MutableLiveData<>();
    }

    public boolean isNotEmptyArrayList(){
        if (arrayList.size() > 0){
            return true;
        }
        return false;
    }

    public int getArrayListSize(){
        return arrayList.size();
    }

    public void sortGameSearch(String name, Boolean alphabetPref, Boolean lengthPref, Boolean startPref, ArrayList<Game> searchList) {
        arrayList.clear();
        ArrayList<String> tempList = new ArrayList<>();
        for (Game game: searchList) {
            if (tempList.contains(game.getName())){
                continue;
            }

            if (startPref){
                int nameLen = name.length();
                if (!game.getName().substring(0, nameLen).toLowerCase(Locale.getDefault()).equals(name)){
                    continue;
                }
            }

            arrayList.add(game);
            tempList.add(game.getName());
        }
        if (alphabetPref){
            Collections.sort(arrayList, (g1, g2) -> g1.getName().compareTo(g2.getName()));
        }
        if (lengthPref){
            Collections.sort(arrayList, (g1, g2) -> g1.getName().length() - g2.getName().length());
        }
        gameLiveData.setValue(arrayList);
    }

    public void getGameSearch(String name, Boolean alphabetPref, Boolean lengthPref, Boolean startPref){
        Log.i("api search", "tehakse game search");
        Ion.with(application)
                .load(SEARCH_URL)
                .asJsonObject()
                .setCallback(((e, result) -> {
                    parseResults(result, name, alphabetPref, lengthPref, startPref);
                }));
    }

    public void getGameDetails(String gameId){
        Log.i("api search", "tehakse game details search");
        Ion.with(application)
                .load(String.format(DETAILS_URL, gameId))
                .asJsonObject()
                .setCallback((e, result) -> {
                    parseDetailsResults(result, gameId);
                });
    }

    private String cleanBackslashes(String text){
        text = text.replaceAll("\\\\t", "");
        text = text.replaceAll("\\\\n", "");
        text = text.replaceAll("\\\\r", "");
        text = text.replaceAll("\\\\\"", "\"");
        return text;
    }

    private void parseDetailsResults(JsonObject result, String gameId) {
        arrayList.clear();
        Log.i("parseResults-gameDetails", gameId);
        JsonObject app = result.getAsJsonObject(gameId);
        JsonObject data = app.getAsJsonObject("data");
        String type = data.get("type").toString();
        String name = data.get("name").toString();
        name = removeAbles(name);
        Log.i("parseResults-gameDetails", type);

        String detailedDescription = "";
        if (data.get("detailed_description") != null){
            detailedDescription = data.get("detailed_description").toString();
            detailedDescription = removeAbles(detailedDescription);
            detailedDescription = cleanBackslashes(detailedDescription);
        }

        String shortDescription = "";
        if (data.get("short_description") != null){
            shortDescription = data.get("short_description").toString();
            shortDescription = removeAbles(shortDescription);
            shortDescription = cleanBackslashes(shortDescription);
        }

        String aboutTheGame = "";
        if (data.get("about_the_game") != null){
            aboutTheGame = data.get("about_the_game").toString();
            aboutTheGame = removeAbles(aboutTheGame);
            aboutTheGame = cleanBackslashes(aboutTheGame);
        }

        String supportedLanguages = "";
        if (data.get("supported_languages") != null){
            supportedLanguages = data.get("supported_languages").toString();
            supportedLanguages = removeAbles(supportedLanguages);
        }

        JsonObject pcRequirements = null;
        String minimumRequirements = null;
        String recommendedRequirements = null;
        if (data.getAsJsonObject("pc_requirements") != null){
            pcRequirements = data.getAsJsonObject("pc_requirements");
            if (pcRequirements.get("minimum") != null){
                minimumRequirements = pcRequirements.get("minimum").toString().replaceAll("^\"|\"$", "");
                minimumRequirements = cleanBackslashes(minimumRequirements);
            }
            if(pcRequirements.get("recommended") != null){
                recommendedRequirements = pcRequirements.get("recommended").toString().replaceAll("^\"|\"$", "");
                recommendedRequirements = cleanBackslashes(recommendedRequirements);
            }
        }


        ArrayList<String> developersList = new ArrayList<>();
        if (data.getAsJsonArray("developers") != null){
            JsonArray developers = data.getAsJsonArray("developers");
            for (int i = 0; i < developers.size(); i++){
                String developer = developers.get(i).toString().replaceAll("^\"|\"$", "");

                developersList.add(developer);
            }
        }

        ArrayList<String> publishersList = new ArrayList<>();
        if (data.getAsJsonArray("publishers") != null){
            JsonArray publishers = data.getAsJsonArray("publishers");
            for (int i = 0; i < publishers.size(); i++){
                String publisher = publishers.get(i).toString().replaceAll("^\"|\"$", "");
                publishersList.add(publisher);
            }
        }
        String initialPrice = "";
        String finalPrice = "";
        if (data.getAsJsonObject("price_overview") != null){
            JsonObject priceOverview = data.getAsJsonObject("price_overview");

            if (priceOverview.get("initial_formatted") != null){
                initialPrice = priceOverview.get("initial_formatted").toString().replaceAll("^\"|\"$", "");
            }

            if (priceOverview.get("final_formatted") != null){
                finalPrice = priceOverview.get("final_formatted").toString().replaceAll("^\"|\"$", "");
            }
        } else {
            initialPrice = "free";
        }


        JsonObject platforms = data.getAsJsonObject("platforms");
        Boolean isWindows = false;
        Boolean isMac = false;
        Boolean isLinux = false;
        if (platforms.get("windows") != null){
            isWindows = platforms.get("windows").getAsBoolean();
        }
        if (platforms.get("mac") != null){
            isMac = platforms.get("mac").getAsBoolean();
        }
        if (platforms.get("linux") != null){
            isLinux = platforms.get("linux").getAsBoolean();
        }

        String metacriticScore = null;
        if (data.getAsJsonObject("metacritic") != null){
            JsonObject metacritic = data.getAsJsonObject("metacritic");
            metacriticScore = metacritic.get("score").toString();
        }

        ArrayList<String> genresList = new ArrayList<>();
        JsonArray genres = data.getAsJsonArray("genres");
        for (int i = 0; i < genres.size(); i++){
            JsonObject genre = (JsonObject) genres.get(i);
            String genreName = genre.get("description").toString();
            genreName = removeAbles(genreName);
            genresList.add(genreName);
        }

        ArrayList<String> screenshotsList = new ArrayList<>();
        if (data.getAsJsonArray("screenshots") != null){
            JsonArray screenshots = data.getAsJsonArray("screenshots");
            for (int i = 0; i < screenshots.size(); i++){
                JsonObject screenshot = (JsonObject) screenshots.get(i);
                String screenshotLink = screenshot.get("path_full").toString();
                screenshotsList.add(screenshotLink);
            }
        }

        ArrayList<String> moviesList = new ArrayList<>();
        if (data.getAsJsonArray("movies") != null){
            JsonArray movies = data.getAsJsonArray("movies");
            for (int i = 0; i < movies.size(); i++){
                JsonObject movie = (JsonObject) movies.get(i);
                JsonObject webm = movie.getAsJsonObject("webm");
                String movieLink = webm.get("max").toString();
                moviesList.add(movieLink);
            }
        }

        JsonObject releaseDate = data.getAsJsonObject("release_date");
        String date = releaseDate.getAsJsonPrimitive("date").toString().replaceAll("^\"|\"$", "");

        Game game = new Game(gameId, name, type, detailedDescription, shortDescription, aboutTheGame,
                supportedLanguages, minimumRequirements, recommendedRequirements,
                developersList, publishersList, initialPrice, finalPrice,
                isWindows, isMac, isLinux, metacriticScore,
                genresList, screenshotsList, moviesList, date);

        Log.i("gameDetails-id", gameId);
        Log.i("gameDetails-name", name);
        Log.i("gameDetails-type", type);
        Log.i("gameDetails-detailed-description", detailedDescription);
        Log.i("gameDetails-short-description", shortDescription);
        Log.i("gameDetails-about-the-game", aboutTheGame);
        Log.i("gameDetails-supported-languages", supportedLanguages);
        if (minimumRequirements != null){
            Log.i("gameDetails-minimum-requirements", minimumRequirements);
        }
        if(recommendedRequirements != null){
            Log.i("gameDetails-recommended-requirements", recommendedRequirements);
        }
        for (int i = 0; i < developersList.size(); i++){
            Log.i("gameDetails-developers", i + " - " + developersList.get(i));
        }
        for (int i = 0; i < publishersList.size(); i++){
            Log.i("gameDetails-publishers", i + " - " + publishersList.get(i));
        }
        if (initialPrice != null){
            Log.i("gameDetails-initial-price", initialPrice);
        }
        if (finalPrice != null){
            Log.i("gameDetails-final-price", finalPrice);
        }
        Log.i("gameDetails-is-windows", isWindows.toString());
        Log.i("gameDetails-is-mac", isMac.toString());
        Log.i("gameDetails-is-linux", isLinux.toString());
        if (metacriticScore != null){
            Log.i("gameDetails-metacritic-score", metacriticScore);
        }
        for (int i = 0; i < genresList.size(); i++){
            Log.i("gameDetails-genres", i + " - " + genresList.get(i));
        }
        if (screenshotsList.size() > 0){
            for (int i = 0; i < screenshotsList.size(); i++){
                Log.i("gameDetails-screenshots", i + " - " + screenshotsList.get(i));
            }
        }
        if (moviesList.size() > 0){
            for (int i = 0; i < moviesList.size(); i++){
                Log.i("gameDetails-movies", i + " - " + moviesList.get(i));
            }
        }
        Log.i("gameDetails-release-date", date);


        arrayList.add(game);
        gameLiveData.setValue(arrayList);
    }

    private void parseResults(JsonObject result, String name, Boolean alphabetPref, Boolean lengthPref, Boolean startPref) {
        if (name == null || name.equals("")){
            return;
        }
        arrayList.clear();
        ArrayList<String> tempList = new ArrayList<>();
        Log.i("parseResults-name", name);
        JsonObject applist = result.getAsJsonObject("applist");
        int appsSize = applist.getAsJsonArray("apps").size();
        Log.i("parseResults-size", Integer.toString(appsSize));
        for (int i = 0; i < appsSize; i++){
            JsonObject app = (JsonObject) applist.getAsJsonArray("apps").get(i);
            String appName = app.get("name").toString();
            int appId = app.get("appid").getAsInt();
            if (appName.toLowerCase(Locale.getDefault()).contains(name)){
                if (!appName.toLowerCase(Locale.getDefault()).contains("soundtrack")){
                    if (!appName.toLowerCase(Locale.getDefault()).contains("dlc")){
                        if (tempList.contains(appName)){
                            continue;
                        }
                        // Only include apps that begin with the name
                        /*
                        int nameLen = name.length();

                        if (!appName.substring(1, nameLen+1).toLowerCase(Locale.getDefault()).equals(name)){
                            continue;
                        }
                        */
                        Game game = new Game(Integer.toString(appId), removeAbles(appName));
                        searchResultList.add(game);

                        if (startPref){
                            int nameLen = name.length();

                            if (!appName.substring(1, nameLen+1).toLowerCase(Locale.getDefault()).equals(name)){
                                continue;
                            }
                        }

                        Log.i("parseResults-found-contains", appName);
                        Log.i("parseResults-found-contains", Integer.toString(appId));

                        arrayList.add(game);
                        tempList.add(appName);
                    }
                }
            }
        }
        for(int i = 0; i < arrayList.size(); i++){
            Log.i("arraylist-details1", i + " - " + arrayList.get(i).getName());
        }

        if (alphabetPref){
            Collections.sort(arrayList, (g1, g2) -> g1.getName().compareTo(g2.getName()));
        }
        //Collections.sort(arrayList, (g1, g2) -> g1.getName().compareTo(g2.getName()));

        // Sort list by name length, so that smallest names start at top
        //Collections.sort(arrayList, (g1, g2) -> g1.getName().length() - g2.getName().length());
        if (lengthPref){
            Collections.sort(arrayList, (g1, g2) -> g1.getName().length() - g2.getName().length());
        }
        for(int i = 0; i < arrayList.size(); i++){
            Log.i("arraylist-details2", i + " - " + arrayList.get(i).getName());
        }

        gameLiveData.setValue(arrayList);
        searchResultLiveData.setValue(searchResultList);

    }

    private String removeAbles(String text) {
        //return text;
        return text.substring(1, text.length()-1);
    }


    public MutableLiveData<ArrayList<Game>> getGameLiveData(){
        return gameLiveData;
    }

    public MutableLiveData<ArrayList<Game>> getSearchResultLiveData() {
        return searchResultLiveData;
    }
}
