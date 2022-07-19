package com.example.capstoneapp.ui.mycolleges.mycollegedetail;


import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstoneapp.model.CollegeApplicationTask;
import com.example.capstoneapp.parsedatasource.ApplicationTaskCallback;
import com.example.capstoneapp.parsedatasource.UpdateApplicationTaskCallback;
import com.example.capstoneapp.parsedatasource.Utilities;
import com.parse.ParseObject;

import java.util.List;


public class MyCollegeDetailViewModel extends ViewModel {
    public static final String TAG = "MyCollegeDetailViewModel";
    private MutableLiveData<List<CollegeApplicationTask>> collegeTasksLiveData = new MutableLiveData<>();


    private final String userId;
    private final Integer collegeId;

    public MyCollegeDetailViewModel(String userId, Integer collegeId) {
        Log.d(TAG, "UserId " + userId );
        Log.d(TAG, "CollegeId " + collegeId );
        // Load all application steps from Repository
        this.userId = userId;
        this.collegeId = collegeId;

    }

    public LiveData<List<CollegeApplicationTask>> getCollegeTasksLiveData() {
        return collegeTasksLiveData;
    }
    public void getAllApplicationSteps(String userId, Integer collegeId) {
        Utilities.getAllApplicationTasks(userId, collegeId, new ApplicationTaskCallback() {
            @Override
            public void onCompleted(List<CollegeApplicationTask> applicationSteps, Boolean error) {
                collegeTasksLiveData.setValue(applicationSteps);
            }
        });
    }

}
