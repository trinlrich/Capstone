package com.example.capstoneapp.ui.collegesearch;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.capstoneapp.R;
import com.example.capstoneapp.model.College;
import com.example.capstoneapp.model.FavoriteCollege;
import com.example.capstoneapp.parsedatasource.Utilities;
import com.example.capstoneapp.ui.collegesearch.filter.FilterUtils;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CollegeSearchViewModel extends AndroidViewModel {

    public static final String TAG = "CollegeSearchViewModel";

    // Processing the filter
    public final String stateString;
    public final String typeString;
    public final String missionString;
    public final String allString;
    private final Context context;

    // This section is for All College List
    private MutableLiveData<List<College>> allCollegesLiveData = new MutableLiveData<>();
    private List<College> allColleges = new ArrayList<>();
    public LiveData<List<College>> getAllCollegesLiveData() {return allCollegesLiveData;}
    private List<College> allCollegesAfterFilter = new ArrayList<>();
    private MutableLiveData<Boolean> showProgress = new MutableLiveData<>();
    public LiveData<Boolean> getShowProgress() {
        return showProgress;
    }


    MutableLiveData<Long> maxId = new MutableLiveData<>();
    private String firebaseUid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

    // This section is for Fav College List
    private Set<Integer> favoriteCollegesSet = new HashSet<>();
    MutableLiveData<Integer> favCollegeUpdatedIndex = new MutableLiveData<>();
    MutableLiveData<Boolean> favCollegeProcessError = new MutableLiveData<>();
    public LiveData<List<College>> getAllFavCollegesLiveData() {
        return allFavCollegesLiveData;
    }
    private MutableLiveData<List<College>> allFavCollegesLiveData = new MutableLiveData<>();
    private List<College> allFavColleges = new ArrayList<>();

    public CollegeSearchViewModel(@NonNull Application application) {
        // on viewmodel create initiate fetch of all data
        super(application);
        context =  application.getApplicationContext();

        stateString = context.getString(R.string.state_key);
        typeString = context.getString(R.string.type_key);
        missionString = context.getString(R.string.mission_key);
        allString = context.getString(R.string.all_filters);

        getCollegesListForUser();
    }

    private void getCollegesListForUser() {
        startProgress();
        Utilities.getFavCollegesForUser(firebaseUid, (favColleges,error) -> {
            // create the Set of Fav Colleges
            for (FavoriteCollege college : favColleges)
                favoriteCollegesSet.add(college.getCollegeId());
            // Get the final list of Colleges which would include the fav colleges
            getCollegesList(favoriteCollegesSet);
        });
    }

    private void getCollegesList(Set<Integer> favoriteColleges) {
        Utilities.getCollegesListFromParse(favoriteColleges, colleges -> {
            if (colleges == null) {
                Log.i(TAG, "No colleges found");
                allCollegesLiveData.setValue(null);
            } else {
                Log.i(TAG, "Colleges found");
                updateCollegeDataSet(colleges);
                maxId.setValue((long) colleges.size());
            }
        });
    }

    private void updateCollegeDataSet(List<College> colleges) {
        allColleges.clear();
        allColleges.addAll(colleges);
        allCollegesLiveData.setValue(allColleges);
        updateFavCollegesDataSet(colleges);
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
        stopProgress();
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

    public void filterCollegesList() {
        // Get filter values
        String stateValue = FilterUtils.getFilterValue(context, stateString);
        String typeValue = FilterUtils.getFilterValue(context, typeString);
        String missionValue = FilterUtils.getFilterValue(context, missionString);

        Log.i(TAG, "State Filter: " + stateValue);
        Log.i(TAG, "Type Filter: " + typeValue);
        Log.i(TAG, "Mission Filter: " + missionValue);

        // Remove college that do not fit criteria
        allCollegesAfterFilter.clear();
        allCollegesAfterFilter.addAll(allColleges);
        if (isFilteringNeeded(stateValue)) {
            allCollegesAfterFilter.removeIf(college -> !college.getCollegeState().equals(stateValue));
        }
        if (isFilteringNeeded(typeValue)) {
            allCollegesAfterFilter.removeIf(college -> !college.getControl().equals(typeValue));
        }
        if (isFilteringNeeded(missionValue)) {
            allCollegesAfterFilter.removeIf(college -> !college.getMission().contains(missionValue));
        }
        allCollegesLiveData.setValue(allCollegesAfterFilter);
    }

    private boolean isFilteringNeeded(String value) {
        // False if filter is set to "All"
        return !value.equals(allString);
    }

    private void startProgress() {
        showProgress.setValue(true);
    }
    private void stopProgress() {
        showProgress.setValue(false);
    }
}
