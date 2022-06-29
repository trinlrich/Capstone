package com.example.capstoneapp.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.capstoneapp.*;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class AuthViewModel extends ViewModel {

    public static final String TAG = "AuthViewModel";

    private String userId;
    private Boolean hasUid = false;
    MutableLiveData<Boolean> isSignUpCompleted = new MutableLiveData<>();

    public enum AuthenticationState {
        AUTHENTICATED, AUTH_INCOMPLETE, UNAUTHENTICATED;
    }

    FirebaseUserLiveData firebaseUserLiveData = new FirebaseUserLiveData();
    LiveData<AuthenticationState> authenticationState = Transformations.map(firebaseUserLiveData, authenticatedUser -> {
        if (authenticatedUser != null) {
            userId = authenticatedUser.getUid();
            Log.i(TAG, "Checking for User ID");
            checkForUserId(userId);
            Log.i(TAG, "User exists: " + hasUid);
            if (hasUid) {
                return AuthenticationState.AUTHENTICATED;
            }
//            return AuthenticationState.AUTH_INCOMPLETE;
        }
        return AuthenticationState.UNAUTHENTICATED;
    });

    private boolean checkForUserId(String userId) {
        Log.i(TAG, "userId: " + userId);
        ParseQuery<FirebaseUser> query = ParseQuery.getQuery(FirebaseUser.class);
        query.whereContains(FirebaseUser.KEY_FIREBASE_UID, userId);
        query.findInBackground(new FindCallback<FirebaseUser>() {
            @Override
            public void done(List<FirebaseUser> objects, ParseException e) {
                // check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    isSignUpCompleted.setValue(false);
                    return;
                }
                if (objects.size() == 0) {
                    Log.i(TAG, "FirebaseUid not found");
                    isSignUpCompleted.setValue(false);
                    return;
                }
                if (objects.size() > 1) {
                    // TODO:: Debug multiple records with firebaseUid error
                    Log.i(TAG, "Multiple records are fetched");
                    isSignUpCompleted.setValue(false);
                    return;
                }
                Log.i(TAG, "FirebaseUid found");
                isSignUpCompleted.setValue(true);
                hasUid = true;
                return;
            }
        });
        return hasUid;
    }

    public MutableLiveData<Boolean> getSignUpCompleted() {
        if (isSignUpCompleted == null) {
            isSignUpCompleted = new MutableLiveData<Boolean>();
        }
        return isSignUpCompleted;
    }
}
