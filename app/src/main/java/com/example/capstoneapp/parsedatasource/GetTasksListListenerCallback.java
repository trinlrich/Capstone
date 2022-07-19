package com.example.capstoneapp.parsedatasource;

import com.example.capstoneapp.model.CollegeTask;

import java.util.List;

public interface GetTasksListListenerCallback {
    void onCompleted(List<CollegeTask> tasks);
}
