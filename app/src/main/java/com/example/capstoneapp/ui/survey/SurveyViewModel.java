package com.example.capstoneapp.ui.survey;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstoneapp.GetUserProfileListenerCallback;
import com.example.capstoneapp.Utilities;
import com.example.capstoneapp.ParseFirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.parse.ParseException;
import com.parse.SaveCallback;

import java.util.HashMap;
import java.util.List;

public class SurveyViewModel extends ViewModel {

    public static final String TAG = "SurveyViewModel";
    private static String firebaseUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private MutableLiveData<Boolean> isUserSaved = new MutableLiveData<>();

    public enum DictionaryKeys{
        FIRST_NAME, LAST_NAME, DEGREE_SEEKING
    }

    public LiveData<Boolean> saveUser(HashMap userInfo) {
        ParseFirebaseUser user = new ParseFirebaseUser();
        user.setFirstName(userInfo.get(DictionaryKeys.FIRST_NAME).toString());
        user.setLastName(userInfo.get(DictionaryKeys.LAST_NAME).toString());
        user.setDegreeSeeking(userInfo.get(DictionaryKeys.DEGREE_SEEKING).toString());
        user. setFirebaseUid(firebaseUid);
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                } else {
                    Log.i(TAG, "Post save was successful");
                    checkForUserId(firebaseUid);
                    firebaseUid = null;
                }
            }
        });
        return isUserSaved;
    }

    private void checkForUserId(String userId) {
        Utilities.getProfileFromParse(userId, new GetUserProfileListenerCallback() {
            @Override
            public void onCompleted(List<ParseFirebaseUser> users) {
                Log.i(TAG, "in onComplete");
                if (users == null) {
                    Log.i(TAG, "FirebaseUid not found");
                    isUserSaved.setValue(false);
                } else if (users.size() > 1) {
                    // TODO:: Debug multiple records with firebaseUid error
                    Log.i(TAG, "Multiple records are fetched");
                    isUserSaved.setValue(false);
                } else {
                    Log.i(TAG, "FirebaseUid found");
                    isUserSaved.setValue(true);
                }
            }
        });
    }
}