package com.example.androidreviewapp.adapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidreviewapp.R;
import com.example.androidreviewapp.model.Game;
import com.example.androidreviewapp.model.Review;

import java.util.ArrayList;

public class AppReviewAdapter extends RecyclerView.Adapter<AppReviewAdapter.AppReviewViewHolder>{
    private ArrayList<Review> reviewList;

    public AppReviewAdapter() {
        this.reviewList = new ArrayList<>();
    }

    @NonNull
    @Override
    public AppReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_app_reviews, parent, false);
        Log.i("TAG","oncreateviewholder");
        return new AppReviewViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AppReviewViewHolder holder, int position) {
        Log.i("onbindviewholder", "teeb onbindviewholderit");
        Review review = reviewList.get(position);
        holder.email.setText(review.getUserEmail());
        holder.review.setText(review.getReviewText());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setReviewList(final ArrayList<Review> reviewList) {
        this.reviewList = reviewList;
        notifyDataSetChanged();
    }
    static class AppReviewViewHolder extends RecyclerView.ViewHolder{
        private final TextView email;
        private final TextView review;
        public AppReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.txtViewEmail);
            review = itemView.findViewById(R.id.txtViewReview);
        }
    }
}
