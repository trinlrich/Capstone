package com.example.capstoneapp.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class AuthViewModel extends ViewModel {

    public enum AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED;
    }

    FirebaseUserLiveData firebaseUserLiveData = new FirebaseUserLiveData();
    LiveData<AuthenticationState> authenticationState = Transformations.map(firebaseUserLiveData, authenticatedUser -> {
        if (authenticatedUser != null) {
            return AuthenticationState.AUTHENTICATED;
        }
        return AuthenticationState.UNAUTHENTICATED;
    });
}
