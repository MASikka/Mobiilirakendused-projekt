package com.example.androidreviewapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidreviewapp.R;
import com.example.androidreviewapp.model.Developer;

import java.util.ArrayList;

public class DeveloperAdapter extends RecyclerView.Adapter<DeveloperAdapter.DeveloperViewHolder>{

    ArrayList<String> developersList;

    public DeveloperAdapter() {
        this.developersList = new ArrayList<>();
    }

    @NonNull
    @Override
    public DeveloperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_game_developer, parent, false);
        return new DeveloperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeveloperAdapter.DeveloperViewHolder holder, int position) {
        String dev = developersList.get(position);
        holder.txtDeveloper.setText(dev);
    }

    @Override
    public int getItemCount() {
        return developersList.size();
    }

    public void setDeveloperList(final ArrayList<String> developersList){
        this.developersList = developersList;
        notifyDataSetChanged();
    }

    class DeveloperViewHolder extends RecyclerView.ViewHolder{
        private final TextView txtDeveloper;

        public DeveloperViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDeveloper = itemView.findViewById(R.id.txtDeveloper);
        }
    }
}
