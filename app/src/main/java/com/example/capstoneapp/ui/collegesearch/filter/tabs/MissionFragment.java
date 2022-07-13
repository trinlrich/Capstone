package com.example.capstoneapp.ui.collegesearch.filter.tabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.capstoneapp.R;
import com.example.capstoneapp.ui.collegesearch.filter.CollegeFilter;
import com.google.gson.Gson;

public class MissionFragment extends Fragment {

    public static final String TAG = "MissionFragment";

    SharedPreferences preferences;
    CollegeFilter mission;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mission, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferences = getContext().getSharedPreferences(getString(R.string.filter_key), Context.MODE_PRIVATE);

        // Create state CollegeFilter
        mission = new CollegeFilter();
        mission.setKey(getString(R.string.mission_key));

        listView = view.findViewById(R.id.mission_list_view);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(this::onItemClick);

        initListViewData();
    }

    private void initListViewData() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_checked, getMissions());
        listView.setAdapter(arrayAdapter);
        //Set existing filters and default if no existing filters
        setFilters();
    }

    private void setFilters() {
        String missionJson = preferences.getString(mission.getKey(), null);
        CollegeFilter existingFilter = new Gson().fromJson(missionJson, CollegeFilter.class);
        listView.setItemChecked(existingFilter.getPosition(), true);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: " +position);
        mission.setValue(listView.getItemAtPosition(position).toString());
        mission.setPosition(position);

        SharedPreferences.Editor editor = preferences.edit();
        // Convert object to JSON string to put in preferences
        String stateJson = new Gson().toJson(mission);
        editor.putString(mission.getKey(), stateJson);
        editor.commit();
    }

    private String[] getMissions() {
        String allMissions = "All";
        String hbcu = "HBCU";
        String pbi = "PBI";
        String annhi = "ANNHI";
        String tribal = "TRIBAL";
        String aanapii = "AANAPII";
        String hsi = "HSI";
        String nanti = "NANTI";
        String menOnly = "MENONLY";
        String womenOnly = "WOMENONLY";

        String[] missions = new String[]{allMissions, hbcu, pbi, aanapii, annhi, tribal, hsi, nanti, menOnly, womenOnly};

        return missions;
    }
}