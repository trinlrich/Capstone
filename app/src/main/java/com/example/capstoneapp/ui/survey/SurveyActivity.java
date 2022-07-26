package com.example.capstoneapp.ui.survey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.devhoony.lottieproegressdialog.LottieProgressDialog;
import com.example.capstoneapp.MainActivity;
import com.example.capstoneapp.R;
import com.example.capstoneapp.parsedatasource.Utilities;
import com.example.capstoneapp.ui.SharedPreferenceUtils;
import com.example.capstoneapp.ui.UiUtils;
import com.facebook.share.Share;

import java.io.IOException;
import java.util.HashMap;

public class SurveyActivity extends AppCompatActivity {

    public static final String TAG = "SurveyActivity";
    public final static int PICK_PHOTO_CODE = 5208;

    private SurveyViewModel viewModel;

    private ImageButton btnProfile;
    private EditText firstNameText;
    private EditText lastNameText;
    private EditText degreeSeekingText;
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

        UiUtils.setViewImage(this, btnProfile, "", null, R.drawable.profile_black_48);
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
            UiUtils.setViewImage(this, btnProfile, selectedImage.toString(), new CircleCrop(), R.drawable.profile_black_48);
        }
    }

    public void onDoneClick(View view) {
        HashMap userInfo = new HashMap();
        userInfo.put(SurveyViewModel.DictionaryKeys.FIRST_NAME, firstNameText.getText().toString());
        userInfo.put(SurveyViewModel.DictionaryKeys.LAST_NAME, lastNameText.getText().toString());
        userInfo.put(SurveyViewModel.DictionaryKeys.DEGREE_SEEKING, degreeSeekingText.getText().toString());
        viewModel.saveProfile(selectedImage.toString(), userInfo).observe(this, saveUserStateObserver);
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
