package com.example.capstoneapp.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.capstoneapp.parsedatasource.Utilities;

public class AuthViewModel extends ViewModel {

    public static final String TAG = "AuthViewModel";

    MutableLiveData<Boolean> isSignUpCompleted = new MutableLiveData<>();

    public enum AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED
    }

    private FirebaseUserLiveData firebaseUserLiveData = new FirebaseUserLiveData();
    LiveData<AuthenticationState> authenticationState = Transformations.map(firebaseUserLiveData, authenticatedUser -> {
        if (authenticatedUser != null) {
            return AuthenticationState.AUTHENTICATED;
        } else {
            return AuthenticationState.UNAUTHENTICATED;
        }
    });



    public void checkForUserId(String userId) {
        Utilities.getProfileFromParse(userId, users -> {
            if (users == null) {
                Log.i(TAG, "FirebaseUid not found");
                isSignUpCompleted.setValue(false);
            } else if (users.size() > 1) {
                // TODO:: Debug multiple records with firebaseUid error
                Log.i(TAG, "Multiple records are fetched");
                isSignUpCompleted.setValue(false);
            } else {
                Log.i(TAG, "FirebaseUid found");
                isSignUpCompleted.setValue(true);
            }
        });
    }
}
