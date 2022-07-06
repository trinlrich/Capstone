package com.example.capstoneapp.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.capstoneapp.*;

import java.util.List;

public class AuthViewModel extends ViewModel {

    public static final String TAG = "AuthViewModel";

    private String userId;
    MutableLiveData<Boolean> isSignUpCompleted = new MutableLiveData<>();

    public enum AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED;
    }

    FirebaseUserLiveData firebaseUserLiveData = new FirebaseUserLiveData();
    LiveData<AuthenticationState> authenticationState = Transformations.map(firebaseUserLiveData, authenticatedUser -> {
        if (authenticatedUser != null) {
            userId = authenticatedUser.getUid();
            Log.i(TAG, "Checking for User ID");
            checkForUserId(userId);
            return AuthenticationState.AUTHENTICATED;
        } else {
            return AuthenticationState.UNAUTHENTICATED;
        }
    });

    private void checkForUserId(String userId) {
        Utilities.getProfileFromParse(userId, new GetUserProfileListenerCallback() {
            @Override
            public void onCompleted(List<ParseFirebaseUser> users) {
                Log.i(TAG, "in onComplete");
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
            }
        });
    }

    public MutableLiveData<Boolean> getSignUpCompleted() {
        if (isSignUpCompleted == null) {
            isSignUpCompleted = new MutableLiveData<Boolean>();
        }
        return isSignUpCompleted;
    }
}
