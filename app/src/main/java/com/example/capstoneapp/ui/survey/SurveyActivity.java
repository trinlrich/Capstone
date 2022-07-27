package com.example.capstoneapp.ui.survey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.devhoony.lottieproegressdialog.LottieProgressDialog;
import com.example.capstoneapp.MainActivity;
import com.example.capstoneapp.R;
import com.example.capstoneapp.model.ParseFirebaseUser;
import com.example.capstoneapp.ui.UiUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SurveyActivity extends AppCompatActivity {

    public static final String TAG = "SurveyActivity";
    public final static int PICK_PHOTO_CODE = 5208;

    private SurveyViewModel viewModel;

    private ImageButton btnProfile;
    private EditText firstNameText;
    private EditText lastNameText;
    private EditText degreeSeekingText;
    private ConstraintLayout spinnerLayout;
    private Spinner degreeSpinner;
    private List<String> degreeList;

    private LottieProgressDialog saveUserProgressBar;
    private Observer<Boolean> saveUserStateObserver;
    private Uri selectedImage;

    private String selectedDegree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        viewModel = new ViewModelProvider(this).get(SurveyViewModel.class);

        btnProfile = findViewById(R.id.btnProfile);
        firstNameText = findViewById(R.id.firstNameText);
        lastNameText = findViewById(R.id.lastNameText);
        EditText emailText = findViewById(R.id.emailText);
        degreeSeekingText = findViewById(R.id.degreeSeekingText);
        spinnerLayout = findViewById(R.id.degreeSpinnerLayout);
        degreeSpinner = findViewById(R.id.degreeSpinner);
        createDegreeList();

        UiUtils.setViewImage(this, btnProfile, "", null, R.drawable.camera_white_24);
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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, degreeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

        setupProgress();
    }

    private void createDegreeList() {
        degreeList = new ArrayList<>();
        degreeList.add("Associate");
        degreeList.add("Bachelor's");
        degreeList.add("Master's");
        degreeList.add("Doctoral");
    }

    public void onPickPhoto(View view) {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , PICK_PHOTO_CODE);//one can be replaced with any action code
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            selectedImage = data.getData();
            UiUtils.setViewImage(this, btnProfile, selectedImage.toString(), new CircleCrop(), R.drawable.camera_white_24);
        }
    }

    public void onDoneClick(View view) {
        showProgress();
        HashMap userInfo = new HashMap();
        userInfo.put(SurveyViewModel.DictionaryKeys.FIRST_NAME, firstNameText.getText().toString());
        userInfo.put(SurveyViewModel.DictionaryKeys.LAST_NAME, lastNameText.getText().toString());
        userInfo.put(SurveyViewModel.DictionaryKeys.DEGREE_SEEKING, selectedDegree);
        viewModel.saveUser(userInfo, new File(selectedImage.toString())).observe(this, saveUserStateObserver);
    }

    private void startHomeScreen() {
        stopProgress();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setupProgress(){
        String title = getString(R.string.save_user_progress_title);
        saveUserProgressBar = new LottieProgressDialog(this,false,null,null,null,null,LottieProgressDialog.SAMPLE_5, title, null);

    }

    private void showProgress(){
        saveUserProgressBar.show();
    }

    private void stopProgress(){
        if (saveUserProgressBar.isShowing())
            saveUserProgressBar.cancel();
    }

    private void makeToast() {
        Toast.makeText(this, "Error saving profile.", Toast.LENGTH_SHORT).show();
    }
}
