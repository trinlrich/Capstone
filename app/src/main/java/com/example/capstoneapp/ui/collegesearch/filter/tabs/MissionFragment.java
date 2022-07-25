package com.example.capstoneapp.ui.collegesearch.filter.tabs;

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
import com.example.capstoneapp.model.College;
import com.example.capstoneapp.model.CollegeFilter;
import com.example.capstoneapp.ui.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class MissionFragment extends Fragment {

    public static final String TAG = "MissionFragment";

    private CollegeFilter mission;
    private ListView listView;
    private String missionString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mission, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        missionString = getString(R.string.mission_key);

        // Create state CollegeFilter
        mission = new CollegeFilter(missionString);

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
        CollegeFilter existingFilter = SharedPreferenceUtils.getFilter(getContext(), missionString);
        listView.setItemChecked(existingFilter.getPosition(), true);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: " +position);
        SharedPreferenceUtils.putFilter(getContext(), mission, listView.getItemAtPosition(position).toString(), position);
    }

    private List<String> getMissions() {
        List<String> missions = new ArrayList<>();
        missions.add("All");
        for (String mission : College.getMissions().values()) {
            missions.add(mission.substring(0, mission.length() - 2));
        }
        return missions;
    }
}
