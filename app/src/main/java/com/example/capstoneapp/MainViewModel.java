package com.example.capstoneapp;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstoneapp.model.ParseFirebaseUser;
import com.example.capstoneapp.parsedatasource.Utilities;
import com.google.firebase.auth.FirebaseAuth;

public class MainViewModel extends ViewModel {

    public static final String TAG = "MainViewModel";
    MutableLiveData<ParseFirebaseUser> user = new MutableLiveData<>();
    private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public void getUserProfileInfo() {
        Utilities.getProfileFromParse(userId, users -> {
            if (users == null) {
                Log.i(TAG, "FirebaseUid not found");
                user.setValue(null);
            } else if (users.size() > 1) {
                // TODO:: Debug multiple records with firebaseUid error
                Log.i(TAG, "Multiple records are fetched");
                user.setValue(null);
            } else {
                Log.i(TAG, "FirebaseUid found");
                user.setValue(users.get(0));
            }
        });
    }
}
