package com.example.capstoneapp;

import android.app.Application;

import com.example.capstoneapp.model.College;
import com.example.capstoneapp.model.FavoriteCollege;
import com.example.capstoneapp.model.ParseFirebaseUser;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(ParseFirebaseUser.class);
        ParseObject.registerSubclass(College.class);
        ParseObject.registerSubclass(FavoriteCollege.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("v6IBsSIJoKwfmlreD5a3Hu4vAELTGbZTr62Pril2")
                .clientKey("1RdcvSLVYd1dClSQutlx1m4FBRkxPedVerpgpi6T")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
