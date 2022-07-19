package com.example.capstoneapp.ui.mycolleges.mycollegesdetail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstoneapp.model.CollegeTask;
import com.example.capstoneapp.parsedatasource.GetTasksListListenerCallback;
import com.example.capstoneapp.parsedatasource.Utilities;

import java.util.ArrayList;
import java.util.List;

public class MyCollegeDetailViewModel extends ViewModel {
    private static final String TAG = "MyCollegeDetailViewModel";

    private MutableLiveData<List<CollegeTask>> allTasksLiveData = new MutableLiveData<>();
    private List<CollegeTask> allTasks = new ArrayList<>();
    public MutableLiveData<List<CollegeTask>> getAllTasksLiveData() { return allTasksLiveData; }

    public MyCollegeDetailViewModel() {
        getTasksList();
    }

    private void getTasksList() {
        Utilities.getTaskListFromParse(new GetTasksListListenerCallback() {
            @Override
            public void onCompleted(List<CollegeTask> tasks) {
                allTasks.addAll(tasks);
                allTasksLiveData.setValue(allTasks);
            }
        });
    }
}