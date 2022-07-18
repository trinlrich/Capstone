package com.example.capstoneapp;

import android.app.Application;

import com.example.capstoneapp.model.College;
import com.example.capstoneapp.model.FavoriteCollege;
import com.example.capstoneapp.model.ParseFirebaseUser;
import com.example.capstoneapp.model.CollegeTask;
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
        ParseObject.registerSubclass(CollegeTask.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("SZyMtZYjJoUVYVuplJZRp5D8o3qyppfkXuzYvhz9")
                .clientKey("Rnu29yFcZdh1qqbikHPszcYNjgIYUCCpwuuZGyCL")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
