package com.example.capstoneapp.auth;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.capstoneapp.MainActivity;
import com.example.capstoneapp.R;
import com.example.capstoneapp.ui.SharedPreferenceUtils;
import com.example.capstoneapp.ui.survey.SurveyActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

public class AuthActivity extends AppCompatActivity {

    private SplashScreen splashScreen;

    private FirebaseAuth auth;
    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        splashScreen = SplashScreen.installSplashScreen(this);
         FirebaseApp.initializeApp(this);

        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_auth);

        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // Create an observer for authentication state
        Observer<AuthViewModel.AuthenticationState> authObserver = authState -> {
            Log.i(TAG, "Observer Triggered");
            if (authState.equals(AuthViewModel.AuthenticationState.AUTHENTICATED)) {
                Log.i(TAG, "Checking for User ID");
                viewModel.checkForUserId(auth.getCurrentUser().getUid());
            } else if (authState.equals(AuthViewModel.AuthenticationState.UNAUTHENTICATED)) {
                Log.e(TAG, "Not Authenticated");
                createFirebaseSignInIntent();
            } else {
                Log.e(TAG, "New $authState state that doesn't require any UI change");
            }
        };
        viewModel.authenticationState.observe(this, authObserver);

        final Observer<Boolean> signInStateObserver = signInState -> {
            if (signInState) {
                Log.i(TAG, "Starting Home Screen");
                startHomeScreen();
            } else if (!signInState) {
                Log.i(TAG, "Starting Survey Screen");
                createUserSharedPreference();
                startSignInSurvey();
            }
        };
        viewModel.isSignUpCompleted.observe(this, signInStateObserver);
    }

    private void startHomeScreen() {
        Log.i(TAG, "Start main");
        Intent homeIntent = new Intent(this, MainActivity.class);
        startActivity(homeIntent);
        finish();
    }

    private void startSignInSurvey() {
        Log.i(TAG, "Start survey");
        Intent surveyIntent = new Intent(this, SurveyActivity.class);
        startActivity(surveyIntent);
        finish();
    }

    private void createFirebaseSignInIntent() {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.GitHubBuilder().build());

        // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.logo)
                .setTheme(R.style.FirebaseTheme)
                .build();
        signInLauncher.launch(signInIntent);
    }

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(new FirebaseAuthUIActivityResultContract(),
            this::onSignInResult
    );

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        // No implementation needed here as viewmodel live data is being observed for Authentication
        Log.d("onSignInResult","onSignInResult");
    }

    private void createUserSharedPreference() {
        SharedPreferenceUtils.putIsFirstTimeVisit(this, auth.getCurrentUser().getUid(), true);
    }
}
