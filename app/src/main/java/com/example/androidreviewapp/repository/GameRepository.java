package com.example.androidreviewapp.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.androidreviewapp.model.Game;
import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Locale;

public class GameRepository {
    private static final String URL = "http://api.steampowered.com/ISteamApps/GetAppList/v0002/?format=json";
    private static final String SPECIFIC_URL = "https://store.steampowered.com/api/appdetails?appids=%s";
    private final Application application;
    private final MutableLiveData<ArrayList<Game>> gameLiveData;
    private final ArrayList<Game> arrayList = new ArrayList<>();

    public GameRepository(Application application) {
        this.application = application;
        this.gameLiveData = new MutableLiveData<>();
    }

    public void getGameSearch(String name){
        Ion.with(application)
                .load(URL)
                .asJsonObject()
                .setCallback(((e, result) -> {
                    parseResults(result, name);
                }));
    }

    private void parseResults(JsonObject result, String name) {
        if (name == null || name.equals("")){
            return;
        }
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
                        Log.i("parseResults-found-contains", appName);
                        Game game = new Game(Integer.toString(appId), removeAbles(appName));
                        arrayList.add(game);
                    }
                }
            }
        }
        gameLiveData.setValue(arrayList);

    }

    private String removeAbles(String text) {
        return text;
        //return text.substring(1, text.length()-1);
    }

    public MutableLiveData<ArrayList<Game>> getGameLiveData(){
        return gameLiveData;
    }
}
