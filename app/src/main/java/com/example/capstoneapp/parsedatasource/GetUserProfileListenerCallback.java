package com.example.capstoneapp.parsedatasource;

import com.example.capstoneapp.model.ParseFirebaseUser;

import java.util.List;

public interface GetUserProfileListenerCallback {
    void onCompleted(List<ParseFirebaseUser> users);
}
