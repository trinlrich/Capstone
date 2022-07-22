package com.example.capstoneapp.ui.mycolleges.mycollegedetail.taskmgmt;

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

public class TaskMgmtViewModel extends ViewModel  {
    public static final String TAG = "TaskMgmtViewModel";
    private MutableLiveData<List<CollegeApplicationTask>> collegeTasksLiveData = new MutableLiveData<>();
    private final String userId;
    private final Integer collegeId;

    public TaskMgmtViewModel(String userId, Integer collegeId) {
        this.userId = userId;
        this.collegeId = collegeId;
        getAllApplicationSteps(userId, collegeId);
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

    public void updateApplicationStepState(ParseObject task, Integer state){
        task.put(CollegeApplicationTask.TASK_KEY_STATE,state);
        Utilities.updateApplicationTask(task, new UpdateApplicationTaskCallback() {
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

    public void createNewApplicationStep() {
        CollegeApplicationTask task = new CollegeApplicationTask();
    }
}
