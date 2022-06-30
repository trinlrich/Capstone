package com.example.capstoneapp.ui.survey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstoneapp.MainActivity;
import com.example.capstoneapp.R;
import com.parse.ParseUser;

import java.util.HashMap;

public class SurveyActivity extends AppCompatActivity {

    public static final String TAG = "SurveyActivity";

    protected ParseUser currentUser;

    private SurveyViewModel viewModel;

    private EditText firstNameText;
    private EditText lastNameText;
    private EditText degreeSeekingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        Log.i(TAG, "survey started");

        viewModel = new ViewModelProvider(this).get(SurveyViewModel.class);

        firstNameText = findViewById(R.id.firstNameText);
        lastNameText = findViewById(R.id.lastNameText);
        degreeSeekingText = findViewById(R.id.degreeSeekingText);

        final Observer<Boolean> saveUserStateObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean saveUserState) {
                if (saveUserState.equals(false)) {
                    makeToast();
                } else if (saveUserState.equals(true)) {
                    startHomeScreen();;
                } else {
                    Log.e(TAG, "New $signInState state that doesn't require any UI change");
                }
            }
        };
        viewModel.isUserSaved.observe(this, saveUserStateObserver);
    }

    public void onDoneClick(View view) {
        HashMap userInfo = new HashMap();
        userInfo.put(SurveyViewModel.DictionaryKeys.FIRST_NAME, firstNameText.getText().toString());
        userInfo.put(SurveyViewModel.DictionaryKeys.LAST_NAME, lastNameText.getText().toString());
        userInfo.put(SurveyViewModel.DictionaryKeys.DEGREE_SEEKING, degreeSeekingText.getText().toString());

        viewModel.saveUser(userInfo);
    }

    private void startHomeScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void makeToast() {
        Toast.makeText(this, "Error saving profile.", Toast.LENGTH_SHORT).show();
    }
}