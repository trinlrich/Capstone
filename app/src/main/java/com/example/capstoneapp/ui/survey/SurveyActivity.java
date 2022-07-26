package com.example.capstoneapp.ui.survey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.devhoony.lottieproegressdialog.LottieProgressDialog;
import com.example.capstoneapp.MainActivity;
import com.example.capstoneapp.R;
import com.example.capstoneapp.ui.UiUtils;

import java.util.HashMap;

public class SurveyActivity extends AppCompatActivity {

    public static final String TAG = "SurveyActivity";
    public final static int PICK_PHOTO_CODE = 5208;

    private SurveyViewModel viewModel;

    private ImageButton btnProfile;
    private EditText firstNameText;
    private EditText lastNameText;
    private EditText degreeSeekingText;
    private LottieProgressDialog saveUserProgressBar;
    private Observer<Boolean> saveUserStateObserver;
    private Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        viewModel = new ViewModelProvider(this).get(SurveyViewModel.class);

        btnProfile = findViewById(R.id.btnProfile);
        firstNameText = findViewById(R.id.firstNameText);
        lastNameText = findViewById(R.id.lastNameText);
        degreeSeekingText = findViewById(R.id.degreeSeekingText);
        EditText emailText = findViewById(R.id.emailText);

        UiUtils.setViewImage(this, btnProfile, "", null, R.drawable.camera_white_24);
        firstNameText.setText(viewModel.getUserFirstName());
        lastNameText.setText(viewModel.getUserLastName());
        emailText.setText(viewModel.getUserEmail());

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
        userInfo.put(SurveyViewModel.DictionaryKeys.DEGREE_SEEKING, degreeSeekingText.getText().toString());
        viewModel.saveProfile(selectedImage.toString(), userInfo).observe(this, saveUserStateObserver);
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
