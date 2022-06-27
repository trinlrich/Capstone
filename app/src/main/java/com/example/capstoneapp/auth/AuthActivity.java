package com.example.capstoneapp.auth;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.capstoneapp.MainActivity;
import com.example.capstoneapp.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
//import com.google.firebase.FirebaseApp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
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
         FirebaseApp.initializeApp(this);

        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_auth);

        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // Create an observer
        Observer<AuthViewModel.AuthenticationState> authObserver = authState -> {
            Log.i(TAG, "Observer Triggered");
            contentHasLoaded = true;
            if (authState.equals(AuthViewModel.AuthenticationState.AUTHENTICATED)) {
                Log.i(TAG, "Start Home Screen");
                startHomeScreen();
                finish();
            } else if (authState.equals(AuthViewModel.AuthenticationState.UNAUTHENTICATED)) {
                Log.e(TAG, "Not Authenticated");
                createFirebaseSignInIntent();
            } else {
                Log.e(TAG, "New $authState state that doesn't require any UI change");
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
                new AuthUI.IdpConfig.EmailBuilder().build());

        // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();
        signInLauncher.launch(signInIntent);
    }

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(new FirebaseAuthUIActivityResultContract(),
            this::onSignInResult
    );

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onLogoutClick(View view) {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(task -> {
                    Log.i(TAG, "Logged Out");
                    Intent intent = new Intent(AuthActivity.this, AuthActivity.class);
                    finish();
                });
    }
}