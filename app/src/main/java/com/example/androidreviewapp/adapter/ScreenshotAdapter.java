package com.example.androidreviewapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidreviewapp.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ScreenshotAdapter extends RecyclerView.Adapter<ScreenshotAdapter.ScreenshotViewHolder> {

    //private Context context;

    ArrayList<String> screenshotsList;

    public ScreenshotAdapter() {this.screenshotsList = new ArrayList<>();}

    @NonNull
    @Override
    public ScreenshotAdapter.ScreenshotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_game_screenshots, parent, false);
        return new ScreenshotViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ScreenshotAdapter.ScreenshotViewHolder holder, int position) {
        String imgScreenshot = screenshotsList.get(position);
        ImageView imageView = holder.imgScreenshot;
        Log.i("Img: ", imgScreenshot);
        new Thread(new Runnable() {
                    public void run() {
                        URL url = null;
                        try {
                            url = new URL(imgScreenshot);
                            final Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            imageView.post(new Runnable() {
                                public void run() {
                                    imageView.setImageBitmap(bitmap);
                                }
                            });
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
    }

    @Override
    public int getItemCount() {
        return screenshotsList.size();
    }

    public void setScreenshotsList(final ArrayList<String> screenshotsList){
        this.screenshotsList = screenshotsList;
        notifyDataSetChanged();
    }


    public class ScreenshotViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgScreenshot;

        public ScreenshotViewHolder(@NonNull View itemView) {
            super(itemView);
            imgScreenshot = itemView.findViewById(R.id.imgScreenshot);
        }

    }
}
