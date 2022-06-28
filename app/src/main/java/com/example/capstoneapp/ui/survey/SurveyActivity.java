package com.example.capstoneapp.ui.survey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstoneapp.FirebaseUser;
import com.example.capstoneapp.MainActivity;
import com.example.capstoneapp.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class SurveyActivity extends AppCompatActivity {

    public static final String TAG = "SurveyActivity";

    protected ParseUser currentUser;

    private ViewModel viewModel;

    private EditText firstNameText;
    private EditText lastNameText;
    private EditText degreeSeekingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        viewModel = new ViewModelProvider(this).get(SurveyViewModel.class);

        firstNameText = findViewById(R.id.firstNameText);
        lastNameText = findViewById(R.id.lastNameText);
        degreeSeekingText = findViewById(R.id.degreeSeekingText);
    }

    public void onDoneClick(View view) {

    }
}