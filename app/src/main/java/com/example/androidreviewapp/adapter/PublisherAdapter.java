package com.example.androidreviewapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidreviewapp.R;

import java.util.ArrayList;

public class PublisherAdapter extends RecyclerView.Adapter<PublisherAdapter.PublisherViewHolder> {

        ArrayList<String> publishersList;

    public PublisherAdapter() {
        this.publishersList = new ArrayList<>();
    }

    @NonNull
    @Override
    public PublisherAdapter.PublisherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_game_publisher, parent, false);
        return new PublisherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PublisherAdapter.PublisherViewHolder holder, int position) {
        String publisher = publishersList.get(position);
        holder.txtPublisher.setText(publisher);
    }

    @Override
    public int getItemCount() {
        return publishersList.size();
    }

    public void setPublishersList(final ArrayList<String> publishersList){
        this.publishersList = publishersList;
        notifyDataSetChanged();
    }

    class PublisherViewHolder extends RecyclerView.ViewHolder{
        private final TextView txtPublisher;

        public PublisherViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPublisher = itemView.findViewById(R.id.txtPublisher);
        }
    }

}
