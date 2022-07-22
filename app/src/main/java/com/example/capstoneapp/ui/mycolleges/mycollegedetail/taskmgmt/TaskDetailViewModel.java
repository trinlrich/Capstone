package com.example.capstoneapp.ui.mycolleges.mycollegedetail.taskmgmt;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.capstoneapp.model.CollegeApplicationTask;
import com.example.capstoneapp.parsedatasource.UpdateApplicationTaskCallback;
import com.example.capstoneapp.parsedatasource.Utilities;

public class TaskDetailViewModel extends ViewModel {

    public static final String TAG = "TaskDetailViewModel";

    public void updateTask(CollegeApplicationTask task) {
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
}