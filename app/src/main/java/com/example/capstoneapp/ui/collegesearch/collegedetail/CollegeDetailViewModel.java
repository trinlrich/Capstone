package com.example.capstoneapp.ui.collegesearch.collegedetail;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstoneapp.model.College;
import com.example.capstoneapp.parsedatasource.GetCollegeListListenerCallback;
import com.example.capstoneapp.parsedatasource.Utilities;

import java.util.List;

public class CollegeDetailViewModel extends ViewModel {
    public static final String TAG = "CollegeDetailViewModel";

    public MutableLiveData<College> college = new MutableLiveData<>();
    private String collegeId;

    public void getCollegeInfo() {
        Utilities.getCollegeFromParse(new GetCollegeListListenerCallback() {
            @Override
            public void onCompleted(List<College> colleges) {
                if (colleges == null) {
                    Log.i(TAG, "No colleges found");
                    college.setValue(null);
                } else {
                    Log.i(TAG, "Colleges found");
                    college.setValue(colleges.get(0));
                }
            }
        });
    }
    // TODO: Implement the ViewModel
}
