package com.example.capstoneapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.capstoneapp.R;
import com.example.capstoneapp.model.College;
import com.example.capstoneapp.model.CollegeFilter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SharedPreferenceUtils {

    public static final String TAG = "SharedPreferenceUtils";

    public static CollegeFilter getFilter(Context context, String filterString) {
        // Retrieve filter preferences
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.filter_key), Context.MODE_PRIVATE);
        // Retrieve json string from preferences
        String filterJson = preferences.getString(filterString, null);
        // Convert json string in preferences back to CollegeFilter object and return
        return new Gson().fromJson(filterJson, CollegeFilter.class);
    }

    public static List<CollegeFilter> getFilterList(Context context, String filterString) {
        // Retrieve filter preferences
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.filter_key), Context.MODE_PRIVATE);
        // Retrieve json string from preferences
        String filterJson = preferences.getString(filterString, null);
        // Convert json string in preferences back to CollegeFilter object and return
//        return new Gson().fromJson(filterJson, ArrayList.class);
        return new Gson().fromJson(filterJson, new TypeToken<List<CollegeFilter>>() {}.getType());
    }

    public static String getFilterValue(Context context, String filterString) {
        return getFilter(context, filterString).getValue();
    }

    public static void putDefaultFilter(Context context, CollegeFilter filter) {
        // Retrieve filter preferences
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.filter_key), Context.MODE_PRIVATE);
        // Convert filter object to json string
        String filterJson = new Gson().toJson(filter);
        // Add to preferences
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(filter.getKey(), filterJson);
        editor.apply();
    }

    public static void putFilter(Context context, CollegeFilter filter, String filterValue, int position) {
        Log.i(TAG, "Putting Filter: " + filterValue + " [" + position + "]");
        // Retrieve filter preferences
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.filter_key), Context.MODE_PRIVATE);
        // Set filter attributes
        filter.setValue(filterValue);
        filter.setPosition(position);
        // Get SharedPreferences editor
        SharedPreferences.Editor editor = preferences.edit();
        // Convert object to JSON string to put in preferences
        String filterJson = new Gson().toJson(filter);
        // Put filter as json string
        editor.putString(filter.getKey(), filterJson);
        // Apply preference changes
        editor.apply();
    }

    public static void putFilter(Context context, List<CollegeFilter> filters) {
        Log.i(TAG, "Putting list of filters...");
        // Retrieve filter preferences
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.filter_key), Context.MODE_PRIVATE);
        // Get SharedPreferences editor
        SharedPreferences.Editor editor = preferences.edit();
        // Convert object to JSON string to put in preferences
        String filterJson = new Gson().toJson(filters, new TypeToken<List<CollegeFilter>>() {}.getType());
        // Put filter as json string
        editor.putString(filters.get(0).getKey(), filterJson);
        // Apply preference changes
        editor.apply();
    }

    public static Boolean getIsFirstTimeVisit(Context context, String userId) {
        // Retrieve filter preferences
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.screen_entered_first_time), Context.MODE_PRIVATE);
        // Find user data in preferences and return boolean value
        for (Map.Entry<String, ?> entry : preferences.getAll().entrySet()) {
            if (entry.getKey().contains(userId)) {
                return Boolean.parseBoolean(entry.getValue().toString());
            }
        }
        return true;
    }

    public static void putIsFirstTimeVisit(Context context, String userId, Boolean isFirstTime) {
        Log.i(TAG, "Putting first time visit preference: " + isFirstTime);
        // Retrieve Screen Entered First Time preferences
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.screen_entered_first_time), Context.MODE_PRIVATE);
        // Get SharedPreferences editor
        SharedPreferences.Editor editor = preferences.edit();
        // Put filter as json string
        editor.putBoolean(context.getString(R.string.screen_entered_first_time) + "-" + userId, isFirstTime);
        // Apply preference changes
        editor.apply();
    }
}
