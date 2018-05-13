package com.example.jon_snow.tanyadokterhewan.Activity;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

public class TanyaDokterHewanChat extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        //PICASSO
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
        Picasso build = builder.build();
        build.setLoggingEnabled(true);
        Picasso.setSingletonInstance(build);


    }
}
