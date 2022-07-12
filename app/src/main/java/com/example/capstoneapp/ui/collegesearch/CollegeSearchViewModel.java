package com.example.capstoneapp.ui.collegesearch;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstoneapp.model.College;
import com.example.capstoneapp.model.FavoriteCollege;
import com.example.capstoneapp.parsedatasource.GetCollegeListListenerCallback;
import com.example.capstoneapp.parsedatasource.GetFavCollegesCallback;
import com.example.capstoneapp.parsedatasource.Utilities;
import com.google.firebase.auth.FirebaseAuth;
import java.util.List;

public class CollegeSearchViewModel extends ViewModel {

    public static final String TAG = "CollegeSearchViewModel";
    MutableLiveData<List<College>> allColleges = new MutableLiveData<>();
    MutableLiveData<Long> maxId = new MutableLiveData<>();
    private String firebaseUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private List<FavoriteCollege> favoriteColleges ;

    public void getCollegesListForUser(){
        Utilities.getFavCollegesForUser(firebaseUid, favColleges -> {
            // what ever may be the value of fav colleges pass it to next stage
            favoriteColleges = favColleges;
            getCollegesList(favoriteColleges);
        });
    }
    private void getCollegesList(List<FavoriteCollege> favoriteColleges) {
        Utilities.getCollegesListFromParse(Long.valueOf(0), favoriteColleges, new GetCollegeListListenerCallback() {
            @Override
            public void onCompleted(List<College> colleges) {
                if (colleges == null) {
                    Log.i(TAG, "No colleges found");
                    allColleges.setValue(null);
                } else {
                    Log.i(TAG, "Colleges found");
                    allColleges.setValue(colleges);
                    maxId.setValue(Long.valueOf(colleges.size()));
                }
            }
        });
    }

    public void loadNextDataFromParse(Long offset) {
        Utilities.getCollegesListFromParse(offset, favoriteColleges, new GetCollegeListListenerCallback() {
            @Override
            public void onCompleted(List<College> colleges) {
                if (colleges == null) {
                    Log.i(TAG, "No colleges found");
                } else {
                    allColleges.setValue(colleges);
                    maxId.setValue(maxId.getValue() + offset);
                }
            }
        });
    }
}
