package com.example.capstoneapp.ui.survey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.capstoneapp.MainActivity;
import com.example.capstoneapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SurveyActivity extends AppCompatActivity {

    public static final String TAG = "SurveyActivity";

    private SurveyViewModel viewModel;

    private EditText firstNameText;
    private EditText lastNameText;
    private ConstraintLayout spinnerLayout;
    private Spinner degreeSpinner;
    private List<String> degreeList;

    private Observer<Boolean> saveUserStateObserver;
    private String selectedDegree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        viewModel = new ViewModelProvider(this).get(SurveyViewModel.class);

        firstNameText = findViewById(R.id.firstNameText);
        lastNameText = findViewById(R.id.lastNameText);
        EditText emailText = findViewById(R.id.emailText);
        spinnerLayout = findViewById(R.id.degreeSpinnerLayout);
        degreeSpinner = findViewById(R.id.degreeSpinner);
        createDegreeList();

        firstNameText.setText(viewModel.getUserFirstName());
        lastNameText.setText(viewModel.getUserLastName());
        emailText.setText(viewModel.getUserEmail());

        spinnerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Spinner Clicked");
                degreeSpinner.performClick();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.degree_spinner_item, degreeList);
        adapter.setDropDownViewResource(R.layout.degree_spinner_item);
        degreeSpinner.setAdapter(adapter);
        degreeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDegree = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No changes if nothing selected
            }
        });

        saveUserStateObserver = saveUserState -> {
            if (saveUserState.equals(false)) {
                makeToast();
            } else if (saveUserState.equals(true)) {
                startHomeScreen();
            } else {
                Log.e(TAG, "New $signInState state that doesn't require any UI change");
            }
        };
    }

    private void createDegreeList() {
        degreeList = new ArrayList<>();
        degreeList.add("Associate");
        degreeList.add("Bachelor's");
        degreeList.add("Master's");
        degreeList.add("Doctoral");
    }

    public void onDoneClick(View view) {
        HashMap userInfo = new HashMap();
        userInfo.put(SurveyViewModel.DictionaryKeys.FIRST_NAME, firstNameText.getText().toString());
        userInfo.put(SurveyViewModel.DictionaryKeys.LAST_NAME, lastNameText.getText().toString());
        userInfo.put(SurveyViewModel.DictionaryKeys.DEGREE_SEEKING, selectedDegree);

        viewModel.saveUser(userInfo).observe(this, saveUserStateObserver);
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
