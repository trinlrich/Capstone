package com.example.capstoneapp;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(FirebaseUser.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("dKuIdqqGpirPpzFMhh1UlG7kufAlZu2QqMAeBkoh")
                .clientKey("b4vzvln9aSFPlc3edTxbtRvBmFhBA9Wv8AfUOkIs")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}