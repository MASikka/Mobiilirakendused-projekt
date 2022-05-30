package com.example.androidreviewapp.repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.androidreviewapp.model.Review;
import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class SteamReviewRepository {

    private static final String STEAM_REVIEW_URL = "https://store.steampowered.com/appreviews/%s?json=1&language=all&filter=recent&num_per_page=100";
    private final Application application;
    private final MutableLiveData<ArrayList<Review>> steamReviewLiveData;
    private final ArrayList<Review> arrayList = new ArrayList<>();

    public SteamReviewRepository(Application application) {
        this.application = application;
        this.steamReviewLiveData = new MutableLiveData<>();
    }

    public void getSteamReviewSearch(String appId){
        Ion.with(application)
                .load(String.format(STEAM_REVIEW_URL, appId))
                .asJsonObject()
                .setCallback((e, result) -> {
                    parseResults(result, appId);
                });
    }
    private String removeAbles(String text) {
        //return text;
        return text.substring(1, text.length()-1);
    }

    private void parseResults(JsonObject result, String appId) {
        arrayList.clear();
        int reviewsArraySize = result.getAsJsonArray("reviews").size();
        for (int i = 0; i < reviewsArraySize; i++){
            JsonObject review = (JsonObject) result.getAsJsonArray("reviews").get(i);
            JsonObject author = review.getAsJsonObject("author");
            String reviewerId = author.getAsJsonPrimitive("steamid").getAsString();
            String reviewText = review.getAsJsonPrimitive("review").getAsString();
            Boolean recommendation = review.getAsJsonPrimitive("voted_up").getAsBoolean();
            Review newReview = new Review(removeAbles(reviewText), recommendation, removeAbles(reviewerId), appId);
            arrayList.add(newReview);
        }
        steamReviewLiveData.setValue(arrayList);
    }

    public MutableLiveData<ArrayList<Review>> getSteamReviewLiveData(){
        return steamReviewLiveData;
    }
}
