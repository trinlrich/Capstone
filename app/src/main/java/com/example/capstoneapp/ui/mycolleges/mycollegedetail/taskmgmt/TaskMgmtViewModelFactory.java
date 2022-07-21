package com.example.capstoneapp.ui.mycolleges.mycollegedetail.taskmgmt;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TaskMgmtViewModelFactory implements ViewModelProvider.Factory {
    String userId;
    Integer collegeId;

    public TaskMgmtViewModelFactory(String userId, Integer collegeId) {
        this.userId = userId;
        this.collegeId = collegeId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == TaskMgmtViewModel.class) {
            return (T) new TaskMgmtViewModel(userId, collegeId);
        }
        return null;
    }
}

