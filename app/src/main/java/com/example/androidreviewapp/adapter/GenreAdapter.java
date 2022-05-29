package com.example.androidreviewapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidreviewapp.R;
import com.example.androidreviewapp.model.Genre;

import java.util.ArrayList;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder>{

    ArrayList<String> genreList;

    public GenreAdapter() {
        this.genreList = new ArrayList<>();
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_game_genre, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        String genre = genreList.get(position);
        holder.txtGenre.setText(genre);
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    public void setGenreList(final ArrayList<String> genreList){
        this.genreList = genreList;
        notifyDataSetChanged();
    }

    class GenreViewHolder extends RecyclerView.ViewHolder{
        private final TextView txtGenre;

        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            txtGenre = itemView.findViewById(R.id.txtGenre);
        }
    }
}
