package com.example.capstoneapp.parsedatasource;



import com.example.capstoneapp.model.ApplicationStep;

import java.util.List;

public interface ApplicationStepsCallback {
    void onCompleted(List<ApplicationStep> applicationSteps, Boolean error);
}
