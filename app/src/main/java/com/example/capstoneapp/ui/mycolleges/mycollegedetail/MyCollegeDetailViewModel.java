package com.example.capstoneapp.ui.mycolleges.mycollegedetail;

import static com.example.capstoneapp.model.ApplicationStep.KEY_STEP_STATE;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstoneapp.model.ApplicationStep;
import com.example.capstoneapp.parsedatasource.ApplicationStepsCallback;
import com.example.capstoneapp.parsedatasource.UpdateAppStepsCallback;
import com.example.capstoneapp.parsedatasource.Utilities;
import com.parse.ParseObject;

import java.util.List;

public class MyCollegeDetailViewModel extends ViewModel {
    public static final String TAG = "MyCollegeDetailViewModel";
    private MutableLiveData<List<ApplicationStep>> applicationStepsLD = new MutableLiveData<>();
    public LiveData<List<ApplicationStep>> getApplicationStepsLD() {
        return applicationStepsLD;
    }

    private final String userId;
    private final Integer collegeId;

    public MyCollegeDetailViewModel(String userId, Integer collegeId) {
        Log.d(TAG, "UserId " + userId );
        Log.d(TAG, "CollegeId " + collegeId );
        // Load all application steps from Repository
        this.userId = userId;
        this.collegeId = collegeId;
        getAllApplicationSteps(userId, collegeId);
    }

    public void getAllApplicationSteps(String userId, Integer collegeId) {
        Utilities.getAllApplicationSteps(userId, collegeId, new ApplicationStepsCallback() {
            @Override
            public void onCompleted(List<ApplicationStep> applicationSteps, Boolean error) {
                applicationStepsLD.setValue(applicationSteps);
            }
        });
    }

    public void updateApplicationStepState(ParseObject step, Integer state){
        step.put(KEY_STEP_STATE,state);
        Utilities.updateApplicationStep(step, new UpdateAppStepsCallback() {
            @Override
            public void onCompleted(Boolean error) {
                if (error){
                    Log.w(TAG,"Error in updateApplicationStepState..");
                }else{
                    Log.d(TAG,"Success Saving State for Task...");
                }

            }
        });
    }
}
