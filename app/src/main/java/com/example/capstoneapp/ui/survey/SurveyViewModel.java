package com.example.capstoneapp.ui.survey;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstoneapp.parsedatasource.GetUserProfileListenerCallback;
import com.example.capstoneapp.parsedatasource.Utilities;
import com.example.capstoneapp.model.ParseFirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class SurveyViewModel extends ViewModel {

    public static final String TAG = "SurveyViewModel";
    private String firebaseUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private MutableLiveData<Boolean> isUserSaved = new MutableLiveData<>();

    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    public enum DictionaryKeys{
        FIRST_NAME, LAST_NAME, DEGREE_SEEKING;
    }

    public void saveProfile(ParseFirebaseUser user, File image) {
        ParseFile profileImage = new ParseFile(image);
        profileImage.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving image", e);
                } else {
                    Log.i(TAG, "Image save was successful");
                    user.setProfileImage(new ParseFile(image));
                    user.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException ex) {
                            if (ex != null) {
                                Log.e(TAG, "Error while updating", ex);
                            } else {
                                Log.i(TAG, "User update was successful");
                            }
                        }
                    });
                }
            }
        });
    }

    public LiveData<Boolean> saveUser(HashMap userInfo, File image) {
          ParseFirebaseUser user = new ParseFirebaseUser();
          user.setFirstName(userInfo.get(DictionaryKeys.FIRST_NAME).toString());
          user.setLastName(userInfo.get(DictionaryKeys.LAST_NAME).toString());
          user.setDegreeSeeking(userInfo.get(DictionaryKeys.DEGREE_SEEKING).toString());
          Log.d(TAG,String.format("Saving Firebase UID:%s , F Name: %s, L Name: %s" ,firebaseUid,user.getFirstName(),user.getLastName()));
          user.setFirebaseUid(firebaseUid);
          user.saveInBackground(e -> {
              if (e != null) {
                  Log.e(TAG, "Error while saving", e);
              } else {
                  Log.i(TAG, "User save was successful");
                  saveProfile(user, image);
                  checkForUserId(firebaseUid);
              }
          });
        return isUserSaved;
    }

    private void checkForUserId(String userId) {
        Log.d(TAG,String.format("Checkin if Firebase UID: %s exists after save" ,firebaseUid));
        Utilities.getProfileFromParse(userId, users -> {
            Log.i(TAG, "in onComplete");

            // Only two values are possible null or actual single record for the user
            if (users == null) {
                Log.i(TAG, "Error -> FirebaseUid not found");
                isUserSaved.setValue(false);
            } else {
                Log.i(TAG, "Success -> FirebaseUid found");
                isUserSaved.setValue(true);
            }
        });
    }
    public String getUserFirstName() {
        String[] result = firebaseUser.getDisplayName().split(" ",2);
        return result[0];
    }
    public String getUserLastName() {
        String[] result = firebaseUser.getDisplayName().split(" ",2);
        return result[1];
    }
    public String getUserEmail() {
        return firebaseUser.getEmail();
    }
}
