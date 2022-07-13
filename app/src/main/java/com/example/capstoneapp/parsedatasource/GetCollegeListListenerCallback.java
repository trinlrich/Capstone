package com.example.capstoneapp.parsedatasource;

import com.example.capstoneapp.model.College;

import java.util.List;

public interface GetCollegeListListenerCallback {
    void onCompleted(List<College> colleges);
}
