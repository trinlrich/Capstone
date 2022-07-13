package com.example.capstoneapp.ui.collegesearch;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.capstoneapp.College;
import com.example.capstoneapp.GetCollegeListListenerCallback;
import com.example.capstoneapp.R;
import com.example.capstoneapp.Utilities;
import com.example.capstoneapp.ui.collegesearch.filter.CollegeFilter;
import com.google.gson.Gson;
import com.parse.ParseQuery;

import java.util.List;

public class CollegeSearchViewModel extends AndroidViewModel {

    public static final String TAG = "CollegeSearchViewModel";
    public final String filterString;
    public final String stateString;
    public final String typeString;
    public final String missionString;
    public final String allString;

    Context context;

    SharedPreferences preferences;

    MutableLiveData<List<College>> allColleges = new MutableLiveData<>();
    MutableLiveData<Long> maxId = new MutableLiveData<>();

    public CollegeSearchViewModel(@NonNull Application application) {
        super(application);
        context =  application.getApplicationContext();

        filterString = context.getString(R.string.filter_key);
        stateString = context.getString(R.string.state_key);
        typeString = context.getString(R.string.type_key);
        missionString = context.getString(R.string.mission_key);
        allString = context.getString(R.string.all_filters);

        preferences = context.getSharedPreferences(filterString, Context.MODE_PRIVATE);
    }

    public void getCollegesList() {
        // Reset allColleges to avoid stacking previous data
        allColleges.setValue(null);
        ParseQuery<College> query = ParseQuery.getQuery(College.class);
        filterCollegesList(query);
        Utilities.getCollegesListFromParse(query, Long.valueOf(0), new GetCollegeListListenerCallback() {
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
        ParseQuery<College> query = ParseQuery.getQuery(College.class);
        filterCollegesList(query);
        Utilities.getCollegesListFromParse(query, offset, new GetCollegeListListenerCallback() {
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

    public void filterCollegesList(ParseQuery<College> query) {
        // Retrieve json string from preferences
        String stateJson = preferences.getString(stateString, null);
        String typeJson = preferences.getString(typeString, null);
        String missionJson = preferences.getString(missionString, null);

        // Convert json string in preferences back to CollegeFilter object
        CollegeFilter state = new Gson().fromJson(stateJson, CollegeFilter.class);
        CollegeFilter type = new Gson().fromJson(typeJson, CollegeFilter.class);
        CollegeFilter mission = new Gson().fromJson(missionJson, CollegeFilter.class);

        // Extract value of filter
        String stateValue = state.getValue();
        String typeValue = type.getValue();
        String missionValue = mission.getValue();

        Log.i(TAG, "State Filter: " + stateValue);
        Log.i(TAG, "Type Filter: " + typeValue);
        Log.i(TAG, "Mission Filter: " + missionValue);

        // Check if filter is set to "All" else add query constraint
        if (!stateValue.equals(allString)) {
            query.whereContains(College.KEY_STATE, stateValue);
        }
        if (!typeValue.equals(allString)) {
            // Get type value's equivalent control number
            int typeControlNum = getTypeControlNum(typeValue);
            Log.i(TAG, "Type Control Num: " + typeControlNum);
            query.whereEqualTo(College.KEY_CONTROL, typeControlNum);
        }
        if (!missionValue.equals(allString)) {
            query.whereContains(College.KEY_MISSION, missionValue);
        }
    }

    private int getTypeControlNum(String value) {
        if (value.equals(context.getString(R.string.public_type))) {
            return 1;
        } else if (value.equals(context.getString(R.string.public_type))) {
            return 2;
        } else if (value.equals(context.getString(R.string.public_type))) {
            return 3;
        } else {
            return 0;
        }
    }
}