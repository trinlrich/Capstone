package com.example.capstoneapp.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.os.Bundle;

import com.example.capstoneapp.R;

public class AuthActivity extends AppCompatActivity {

    private SplashScreen splashScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    }
}