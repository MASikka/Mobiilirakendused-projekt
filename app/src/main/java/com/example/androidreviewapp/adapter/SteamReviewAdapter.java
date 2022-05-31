package com.example.androidreviewapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidreviewapp.R;
import com.example.androidreviewapp.model.Review;

import java.util.ArrayList;

public class SteamReviewAdapter extends RecyclerView.Adapter<SteamReviewAdapter.SteamReviewViewHolder> {

    private ArrayList<Review> reviewList;

    public SteamReviewAdapter() {
        this.reviewList = new ArrayList<>();
    }

    @NonNull
    @Override
    public SteamReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_app_reviews, parent, false);
        return new SteamReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SteamReviewViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.email.setText(review.getUserEmail());
        holder.review.setText(review.getReviewText());
        holder.recommended.setChecked(review.getRecommends());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public void setReviewList(final ArrayList<Review> reviewList){
        this.reviewList = reviewList;
        notifyDataSetChanged();
    }

    static class SteamReviewViewHolder extends RecyclerView.ViewHolder{

        private final TextView email;
        private final TextView review;
        private final CheckBox recommended;

        public SteamReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.txtViewEmail);
            review = itemView.findViewById(R.id.txtViewReview);
            recommended = itemView.findViewById(R.id.checkBoxRecommends);
        }
    }
}
