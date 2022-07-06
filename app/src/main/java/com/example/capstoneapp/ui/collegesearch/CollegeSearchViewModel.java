package com.example.capstoneapp.ui.collegesearch;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstoneapp.College;
import com.example.capstoneapp.GetCollegeListListenerCallback;
import com.example.capstoneapp.GetUserProfileListenerCallback;
import com.example.capstoneapp.ParseFirebaseUser;
import com.example.capstoneapp.Utilities;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class CollegeSearchViewModel extends ViewModel {

    public static final String TAG = "CollegeSearchViewModel";
    MutableLiveData<List<College>> allColleges = new MutableLiveData<>();

    public void getCollegesList() {
        Utilities.getCollegesListFromParse(new GetCollegeListListenerCallback() {
            @Override
            public void onCompleted(List<College> colleges) {
                if (colleges == null) {
                    Log.i(TAG, "No colleges found");
                    allColleges.setValue(null);
                } else {
                    Log.i(TAG, "Colleges found");
                    allColleges.setValue(colleges);
                }
            }
        });
    }
}