package com.example.androidreviewapp.repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.androidreviewapp.model.Review;
import com.example.androidreviewapp.model.ReviewScore;
import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class SteamReviewRepository {

    private static final String STEAM_REVIEW_URL = "https://store.steampowered.com/appreviews/%s?json=1&language=%s&filter=%s&num_per_page=100";
    private final Application application;
    private final MutableLiveData<ArrayList<Review>> steamReviewLiveData;
    private final ArrayList<Review> arrayList = new ArrayList<>();
    private final ArrayList<ReviewScore> reviewScoreArrayList = new ArrayList<>();
    private final MutableLiveData<ArrayList<ReviewScore>> reviewScoreLiveData;

    public SteamReviewRepository(Application application) {
        this.application = application;
        this.steamReviewLiveData = new MutableLiveData<>();
        this.reviewScoreLiveData = new MutableLiveData<>();
    }

    public void getSteamReviewSearch(String appId, String langPref, String filterPref){
        Ion.with(application)
                .load(String.format(STEAM_REVIEW_URL, appId, langPref, filterPref))
                .asJsonObject()
                .setCallback((e, result) -> {
                    parseResults(result, appId);
                });
    }

    public void getSteamReviewScores(String appId){
        Ion.with(application)
                .load(String.format(STEAM_REVIEW_URL, appId, "all", "all"))
                .asJsonObject()
                .setCallback((e, result) -> {
                    parseScores(result);
                });
    }

    private void parseScores(JsonObject result) {
        reviewScoreArrayList.clear();
        JsonObject querySummary = result.getAsJsonObject("query_summary");
        int totalPositiveInt = querySummary.getAsJsonPrimitive("total_positive").getAsInt();
        int totalNegativeInt = querySummary.getAsJsonPrimitive("total_negative").getAsInt();
        String totalPositive = String.valueOf(totalPositiveInt);
        String totalNegative = String.valueOf(totalNegativeInt);
        ReviewScore reviewScore = new ReviewScore(totalPositive, totalNegative);
        reviewScoreArrayList.add(reviewScore);
        reviewScoreLiveData.setValue(reviewScoreArrayList);
    }

    private String removeAbles(String text) {
        //return text;
        if (text.length() < 2){
            return text;
        }
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
            //Review newReview = new Review(removeAbles(reviewText), recommendation, removeAbles(reviewerId), appId);
            Review newReview = new Review(reviewText, recommendation, reviewerId, appId);
            arrayList.add(newReview);
        }
        steamReviewLiveData.setValue(arrayList);
    }

    public MutableLiveData<ArrayList<Review>> getSteamReviewLiveData(){
        return steamReviewLiveData;
    }

    public MutableLiveData<ArrayList<ReviewScore>> getReviewScoreLiveData(){
        return reviewScoreLiveData;
    }
}
