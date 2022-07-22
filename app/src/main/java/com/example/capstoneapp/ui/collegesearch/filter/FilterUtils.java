package com.example.capstoneapp.ui.collegesearch.filter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.capstoneapp.R;
import com.example.capstoneapp.model.CollegeFilter;
import com.google.gson.Gson;

public class FilterUtils {

    public static final String TAG = "FilterUtils";

    public static CollegeFilter getFilter(Context context, String filterString) {
        // Retrieve filter preferences
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.filter_key), Context.MODE_PRIVATE);
        // Retrieve json string from preferences
        String filterJson = preferences.getString(filterString, null);
        // Convert json string in preferences back to CollegeFilter object and return
        return new Gson().fromJson(filterJson, CollegeFilter.class);
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
        String stateJson = new Gson().toJson(filter);
        // Put filter as json string
        editor.putString(filter.getKey(), stateJson);
        // Apply preference changes
        editor.apply();
    }
}
