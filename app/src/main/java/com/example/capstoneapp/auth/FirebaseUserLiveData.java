package com.example.capstoneapp.auth;

import androidx.annotation.NonNull;

import androidx.lifecycle.LiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseUserLiveData extends LiveData<FirebaseUser> {
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            setValue(firebaseAuth.getCurrentUser());
        }
    };

    public FirebaseUser getUser() {
        return user;
    }

    @Override
    protected void onActive() {
        super.onActive();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}
