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
                .applicationId("4noPUd2D0lushfYtLZYwHVsRFr6oDvCXXeVecKZZ")
                .clientKey("VwgmxojSkCFuFrhk7k4WHXesaCKKEoYhijAkW4pq")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
