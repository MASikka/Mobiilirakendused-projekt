package com.example.androidreviewapp.model;

import java.util.ArrayList;

public class Game {

    private final String id;
    private final String name;
    private final String type;
    private final String detailedDescription;
    private final String shortDescription;
    private final String aboutTheGame;
    private final String supportedLanguages;
    private final String minimumPCRequirements;
    private final String recommendedPCRequirements;
    private final ArrayList<String> developersList;
    private final ArrayList<String> publishersList;
    //private final String currency;
    private final String initialPrice;
    private final String finalPrice;
    private final boolean isWindows;
    private final boolean isMac;
    private final boolean isLinux;
    private final String metacriticScore;
    private final ArrayList<String> genresList;
    private final ArrayList<String> screenshotsList;
    private final ArrayList<String> moviesList;
    private final String releaseDate;
/*
    public Game(String id, String name, String detailedDescription, String shortDescription) {
        this.id = id;
        this.name = name;
        this.detailedDescription = detailedDescription;
        this.shortDescription = shortDescription;
    }

 */

    public Game(String id, String name, String type, String detailedDescription, String shortDescription,
                String aboutTheGame, String supportedLanguages, String minimumPCRequirements,
                String recommendedPCRequirements, ArrayList<String> developersList, ArrayList<String> publishersList,
                String initialPrice, String finalPrice, boolean isWindows, boolean isMac, boolean isLinux,
                String metacriticScore, ArrayList<String> genresList, ArrayList<String> screenshotsList,
                ArrayList<String> moviesList, String releaseDate) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.detailedDescription = detailedDescription;
        this.shortDescription = shortDescription;
        this.aboutTheGame = aboutTheGame;
        this.supportedLanguages = supportedLanguages;
        this.minimumPCRequirements = minimumPCRequirements;
        this.recommendedPCRequirements = recommendedPCRequirements;
        this.developersList = developersList;
        this.publishersList = publishersList;
        this.initialPrice = initialPrice;
        this.finalPrice = finalPrice;
        this.isWindows = isWindows;
        this.isMac = isMac;
        this.isLinux = isLinux;
        this.metacriticScore = metacriticScore;
        this.genresList = genresList;
        this.screenshotsList = screenshotsList;
        this.moviesList = moviesList;
        this.releaseDate = releaseDate;
    }

    public Game(String id, String name) {
        this.id = id;
        this.name = name;
        type = null;
        detailedDescription = null;
        shortDescription = null;
        aboutTheGame = null;
        supportedLanguages = null;
        minimumPCRequirements = null;
        recommendedPCRequirements = null;
        developersList = null;
        publishersList = null;
        initialPrice = null;
        finalPrice = null;
        isWindows = false;
        isMac = false;
        isLinux = false;
        metacriticScore = null;
        genresList = null;
        screenshotsList = null;
        moviesList = null;
        releaseDate = null;
    }



    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getAboutTheGame() {
        return aboutTheGame;
    }

    public String getSupportedLanguages() {
        return supportedLanguages;
    }

    public String getMinimumPCRequirements() {
        return minimumPCRequirements;
    }

    public String getRecommendedPCRequirements() {
        return recommendedPCRequirements;
    }

    public ArrayList<String> getDevelopersList() {
        return developersList;
    }

    public ArrayList<String> getPublishersList() {
        return publishersList;
    }

    public String getInitialPrice() {
        return initialPrice;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public boolean isWindows() {
        return isWindows;
    }

    public boolean isMac() {
        return isMac;
    }

    public boolean isLinux() {
        return isLinux;
    }

    public String getMetacriticScore() {
        return metacriticScore;
    }

    public ArrayList<String> getGenresList() {
        return genresList;
    }

    public ArrayList<String> getScreenshotsList() {
        return screenshotsList;
    }

    public ArrayList<String> getMoviesList() {
        return moviesList;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
