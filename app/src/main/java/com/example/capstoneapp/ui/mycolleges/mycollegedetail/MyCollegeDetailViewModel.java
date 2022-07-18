package com.example.capstoneapp.ui.mycolleges.mycollegedetail;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstoneapp.model.ApplicationStep;
import com.example.capstoneapp.parsedatasource.ApplicationStepsCallback;
import com.example.capstoneapp.parsedatasource.Utilities;

import java.util.List;

public class MyCollegeDetailViewModel extends ViewModel {
    public static final String TAG = "MyCollegeDetailViewModel";
    private MutableLiveData<List<ApplicationStep>> applicationStepsLD = new MutableLiveData<>();
    public LiveData<List<ApplicationStep>> getApplicationStepsLD() {
        return applicationStepsLD;
    }

    public MyCollegeDetailViewModel(String userId, Integer collegeId) {
        Log.d(TAG, "UserId " + userId );
        Log.d(TAG, "CollegeId " + collegeId );

        Utilities.getAllApplicationSteps(userId, collegeId, new ApplicationStepsCallback() {
            @Override
            public void onCompleted(List<ApplicationStep> applicationSteps, Boolean error) {
                applicationStepsLD.setValue(applicationSteps);
            }
        });

    }
}
