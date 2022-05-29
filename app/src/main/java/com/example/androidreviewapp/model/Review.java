package com.example.androidreviewapp.model;

public class Review {
    private String reviewText;
    private Boolean recommends;
    private String userEmail;
    private String gameId;
    //private String Id;
    public Review() {
    }

    public Review(String reviewText, Boolean recommends,String userEmail,String gameId) {
        this.reviewText = reviewText;
        this.recommends = recommends;
        this.userEmail = userEmail;
        this.gameId = gameId;
    }
    public String getReviewText(){
        return reviewText;
    }
    public Boolean getRecommends(){
        return recommends;
    }
    public String getUserEmail(){
        return userEmail;
    }
    public String getGameId(){
        return gameId;
    }
    public void setReviewText(String reviewText){
        this.reviewText = reviewText;
    }
    public void setRecommends(Boolean recommends){
        this.recommends = recommends;
    }
    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
    /*public String getId(){
        return Id;
    }*/

    @Override
    public String toString() {
        return "Review{" +
                "reviewText='" + reviewText + '\'' +
                ", recommends=" + recommends +
                ", userEmail='" + userEmail + '\'' +
                ", gameId='" + gameId + '\'' +
                '}';
    }
}
