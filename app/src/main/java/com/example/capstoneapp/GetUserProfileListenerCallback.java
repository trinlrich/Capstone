package com.example.capstoneapp;

import java.util.List;

public interface GetUserProfileListenerCallback {

    public void onCompleted(List<ParseFirebaseUser> users);
}
