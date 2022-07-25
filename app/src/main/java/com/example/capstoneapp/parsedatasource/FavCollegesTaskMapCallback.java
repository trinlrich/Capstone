package com.example.capstoneapp.parsedatasource;

import com.example.capstoneapp.model.CollegeApplicationTask;

import java.util.List;
import java.util.Map;

public interface FavCollegesTaskMapCallback {
    void onCompleted(Map<Integer, List<CollegeApplicationTask>> collegeApplicationTasksMap, Boolean error);
}
