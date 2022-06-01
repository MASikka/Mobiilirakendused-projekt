package com.example.androidreviewapp.model;

public class ReviewScore {
    private String totalPositives;
    private String totalNegatives;

    public ReviewScore(String totalPositives, String totalNegatives) {
        this.totalPositives = totalPositives;
        this.totalNegatives = totalNegatives;
    }

    public String getTotalPositives() {
        return totalPositives;
    }

    public void setTotalPositives(String totalPositives) {
        this.totalPositives = totalPositives;
    }

    public String getTotalNegatives() {
        return totalNegatives;
    }

    public void setTotalNegatives(String totalNegatives) {
        this.totalNegatives = totalNegatives;
    }
}
