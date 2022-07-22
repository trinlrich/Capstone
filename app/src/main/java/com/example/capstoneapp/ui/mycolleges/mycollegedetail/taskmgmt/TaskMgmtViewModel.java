package com.example.capstoneapp.ui.mycolleges.mycollegedetail.taskmgmt;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstoneapp.model.CollegeApplicationTask;
import com.example.capstoneapp.parsedatasource.ApplicationTaskCallback;
import com.example.capstoneapp.parsedatasource.UpdateApplicationTaskCallback;
import com.example.capstoneapp.parsedatasource.Utilities;
import com.google.firebase.auth.FirebaseAuth;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TaskMgmtViewModel extends ViewModel  {
    public static final String TAG = "TaskMgmtViewModel";
    private MutableLiveData<List<CollegeApplicationTask>> collegeTasksLiveData = new MutableLiveData<>();
    private List<CollegeApplicationTask> applicationTasks = new ArrayList<>();
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
                applicationTasks.addAll(applicationSteps);
                collegeTasksLiveData.setValue(applicationTasks);
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

    public CollegeApplicationTask createDefaultTask() {
        CollegeApplicationTask task = new CollegeApplicationTask();
        task.setFirebaseUid(userId);
        task.setCollegeId(collegeId);
        task.setTaskTitle("New Task");
        task.setTaskDescription("");
        task.setTaskPriority(0);
        task.setTaskState(0);
        task.setTaskStartDate(new Date().getTime());
        task.setTaskEndDate(System.currentTimeMillis());
        task.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                } else {
                    Log.i(TAG, "Task save was successful");
                }
            }
        });
        applicationTasks.add(task);
        collegeTasksLiveData.setValue(applicationTasks);
        return task;
    }
}
