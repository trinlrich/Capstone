package com.example.capstoneapp.ui.survey;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstoneapp.FirebaseUser;
import com.example.capstoneapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.HashMap;

public class SurveyViewModel extends ViewModel {

    public static final String TAG = "SurveyViewModel";
    private static int result;
    private static String firebaseUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    static MutableLiveData<Boolean> isUserSaved = new MutableLiveData<>();

    public enum DictionaryKeys{
        FIRST_NAME, LAST_NAME, DEGREE_SEEKING
    }

    public void saveUser(HashMap userInfo) {
        FirebaseUser user = new FirebaseUser();
        Log.i(TAG, "Size: "+ userInfo.size() + " Value: " + userInfo.get(DictionaryKeys.FIRST_NAME) + " ");
        user.setFirstName(userInfo.get(DictionaryKeys.FIRST_NAME).toString());
        user.setLastName(userInfo.get(DictionaryKeys.LAST_NAME).toString());
        user.setDegreeSeeking(userInfo.get(DictionaryKeys.DEGREE_SEEKING).toString());
        user.setFirebaseUid(firebaseUid);
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    isUserSaved.setValue(false);
                    return;
                }
                Log.i(TAG, "Post save was successful");
                isUserSaved.setValue(true);
            }
        });
    }
}