package com.example.capstoneapp.parsedatasource;



import com.example.capstoneapp.model.CollegeApplicationTask;

import java.util.List;

public interface ApplicationTaskCallback {
    void onCompleted(List<CollegeApplicationTask> applicationTasks, Boolean error);
}
