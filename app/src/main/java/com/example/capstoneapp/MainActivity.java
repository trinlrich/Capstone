package com.example.capstoneapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.capstoneapp.auth.AuthActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLogoutClick(View view) {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(task ->
                        startActivity(new Intent(MainActivity.this, AuthActivity.class)));

        startActivity(new Intent(this, AuthActivity.class));
        finish();
    }
}