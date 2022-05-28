package com.example.androidreviewapp.model;

public class Review {
    private String reviewText;
    private Boolean recommends;
    private Integer likeAmount;
    private String userEmail;
    private String gameId;
    private String Id;
    public Review() {
    }

    public Review(String reviewText, Boolean recommends,String userEmail,String gameId) {
        this.reviewText = reviewText;
        this.recommends = recommends;
        this.userEmail = userEmail;
        this.gameId = gameId;
        this.likeAmount = 0;
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
    public Integer getLikeAmount(){
        return likeAmount;
    }
    public void setReviewText(String reviewText){
        this.reviewText = reviewText;
    }
    public void setRecommends(Boolean recommends){
        this.recommends = recommends;
    }
    public void setLikeAmount(Integer likeAmount){
        this.likeAmount = likeAmount;
    }
    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }
    public void setGameID(String gameId) {
        this.gameId = gameId;
    }
    public String getId(){
        return Id;
    }
}
