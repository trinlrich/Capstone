package com.example.capstoneapp.parsedatasource;

import com.example.capstoneapp.model.CollegeApplicationTask;


import java.util.List;

public interface GetTasksListListenerCallback {
    void onCompleted(List<CollegeApplicationTask> tasks);
}
