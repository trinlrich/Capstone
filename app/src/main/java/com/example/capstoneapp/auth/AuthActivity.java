package com.example.capstoneapp.auth;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.capstoneapp.MainActivity;
import com.example.capstoneapp.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
//import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

public class AuthActivity extends AppCompatActivity {

    private SplashScreen splashScreen;

    private FirebaseAuth auth;
    private Boolean contentHasLoaded = false;
    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        splashScreen = SplashScreen.installSplashScreen(this);
        // FirebaseApp.initializeApp(AuthActivity.this);

        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_auth);

        // Create an observer
        Observer<AuthViewModel.AuthenticationState> authObserver = new Observer<AuthViewModel.AuthenticationState>() {
            @Override
            public void onChanged(AuthViewModel.AuthenticationState authState) {
                contentHasLoaded = true;
                if (authState.equals(AuthViewModel.AuthenticationState.AUTHENTICATED)) {
                    startHomeScreen();
                    finish();
                } else if (authState.equals(AuthViewModel.AuthenticationState.UNAUTHENTICATED)) {
                    Log.e(TAG, "Not Authenticated");
                    createFirebaseSignInIntent();
                } else {
                    Log.e(TAG, "New $authState state that doesn't require any UI change");
                }
            }
        };
        viewModel.authenticationState.observe(this, authObserver);
    }

    private void startHomeScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void createFirebaseSignInIntent() {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.TwitterBuilder().build());

        // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();
        signInLauncher.launch(signInIntent);
    }

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
    }
}