package com.example.capstoneapp.ui.collegesearch;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstoneapp.model.College;
import com.example.capstoneapp.model.FavoriteCollege;
import com.example.capstoneapp.parsedatasource.Utilities;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollegeSearchViewModel extends ViewModel {

    public static final String TAG = "CollegeSearchViewModel";
    // This section is for All College List
    private MutableLiveData<List<College>> allCollegesLiveData = new MutableLiveData<>();
    private List<College> allColleges = new ArrayList<>();
    public LiveData<List<College>> getAllCollegesLiveData() {
        return allCollegesLiveData;
    }

    MutableLiveData<Long> maxId = new MutableLiveData<>();

    private Set<Integer> favoriteCollegesSet = new HashSet<>();
    MutableLiveData<Integer> favCollegeUpdatedIndex = new MutableLiveData<>();
    MutableLiveData<Boolean> favCollegeProcessError = new MutableLiveData<>();

    private String firebaseUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    // This section is for Fav College List
    public LiveData<List<College>> getAllFavCollegesLiveData() {
        return allFavCollegesLiveData;
    }
    private MutableLiveData<List<College>> allFavCollegesLiveData = new MutableLiveData<>();
    private List<College> allFavColleges = new ArrayList<>();

    public CollegeSearchViewModel() {
        // on viewmodel create initaiate fetch of all data
        getCollegesListForUser();
    }


    private void getCollegesListForUser() {
        Utilities.getFavCollegesForUser(firebaseUid, (favColleges,error) -> {
            // create the Set of Fav Colleges
            for (FavoriteCollege college : favColleges)
                favoriteCollegesSet.add(college.getCollegeId());
            // Get the final list of Colleges which would include the fav colleges
            getCollegesList(favoriteCollegesSet);
        });
    }

    private void getCollegesList(Set<Integer> favoriteColleges) {
        Utilities.getCollegesListFromParse(Long.valueOf(0), favoriteColleges, colleges -> {
            if (colleges == null) {
                Log.i(TAG, "No colleges found");
                allCollegesLiveData.setValue(null);
            } else {
                Log.i(TAG, "Colleges found");
                updateCollegeDataSet(colleges);
                maxId.setValue(Long.valueOf(colleges.size()));
            }
        });
    }
    public void loadNextDataFromParse(Long offset) {
        Utilities.getCollegesListFromParse(offset, favoriteCollegesSet, colleges -> {
            if (colleges == null) {
                Log.i(TAG, "No colleges found");
            } else {
                updateCollegeDataSet(colleges);
                maxId.setValue(maxId.getValue() + offset);
            }
        });
    }


    private void updateCollegeDataSet(List<College> colleges) {
        allColleges.addAll(colleges);
        allCollegesLiveData.setValue(allColleges);

        // Get the list of all Fav Colleges
        List<College> favColleges = new ArrayList<>();
        for (College college: colleges) {
            if (college.isFavorite())
                favColleges.add(college);
        }
        allFavColleges.addAll(favColleges);
        allFavCollegesLiveData.setValue(allFavColleges);
    }

    private void updateFavCollegesDataSet(List<College> colleges) {
        // Get the list of all Fav Colleges
        List<College> favColleges = new ArrayList<>();
        for (College college: colleges) {
            if (college.isFavorite())
                favColleges.add(college);
        }
        allFavColleges.clear();
        allFavColleges.addAll(favColleges);
        allFavCollegesLiveData.setValue(allFavColleges);
    }

    public void updateFavCollege(College selectedCollege) {
        // if college id is in fav set then remove from parse db else add it to parse db
        if (favoriteCollegesSet.contains(selectedCollege.getCollegeId())) {
            Utilities.removeFavCollegeForUser(firebaseUid, selectedCollege, (favoriteColleges, error) -> {
                Log.d(TAG, "Get Fav Colleges after Deleting....");
                if (error)
                    showErrorMessage();
                else
                    updateFavCollegeSet(favoriteColleges, selectedCollege, false);
            });
        } else {
            Utilities.addFavCollegeForUser(firebaseUid, selectedCollege, (favoriteColleges, error) -> {
                Log.d(TAG, "Get Fav Colleges after Adding....");
                if (error)
                    showErrorMessage();
                else 
                    updateFavCollegeSet(favoriteColleges, selectedCollege, true);
            });
        }

    }

    private void showErrorMessage() {
        // an error live data can be added to inform UI to display snackbar or toast
        Log.e(TAG, "Error in Adding or Deleting Fav College");
        favCollegeProcessError.setValue(true);
    }

    private void updateFavCollegeSet(List<FavoriteCollege> favoriteColleges, College selectedCollege, boolean added) {
        // Process errors if any
        if (favoriteColleges == null) {
            Log.w(TAG, "Fav College List is Null. May be some issues or errors");
            return;
        }
        // Otherwise just update the fav college set
        favoriteCollegesSet.clear();
        for (FavoriteCollege college : favoriteColleges)
            favoriteCollegesSet.add(college.getCollegeId());

        updateDataSetAndUI(selectedCollege, added);
        updateFavCollegesDataSet(allColleges);
    }

    private void updateDataSetAndUI(College selectedCollege, boolean added) {
        for (int indx = 0; indx < allColleges.size(); indx++) {
            if (allColleges.get(indx).getCollegeId() == selectedCollege.getCollegeId()) {
                allColleges.get(indx).setFavorite(added);
                favCollegeUpdatedIndex.setValue(indx);
                break;
            }
        }
    }
}
