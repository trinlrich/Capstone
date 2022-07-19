package com.example.capstoneapp.ui.mycolleges.mycollegedetail;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TaskManagementViewModelFactory implements ViewModelProvider.Factory {
    String userId;
    Integer collegeId;

    public TaskManagementViewModelFactory(String userId, Integer collegeId) {
        this.userId = userId;
        this.collegeId = collegeId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == TaskManagementViewModel.class) {
            return (T) new TaskManagementViewModel(userId, collegeId);
        }
        return null;
    }
}

