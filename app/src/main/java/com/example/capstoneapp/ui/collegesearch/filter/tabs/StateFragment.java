package com.example.capstoneapp.ui.collegesearch.filter.tabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.capstoneapp.R;
import com.example.capstoneapp.model.College;
import com.example.capstoneapp.ui.collegesearch.filter.CollegeFilter;
import com.example.capstoneapp.ui.collegesearch.filter.FilterUtils;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class StateFragment extends Fragment {

    public static final String TAG = "StateFragment";

    private SharedPreferences preferences;
    private CollegeFilter state;
    private ListView listView;
    private String stateString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_state, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferences = getContext().getSharedPreferences(getString(R.string.filter_key), Context.MODE_PRIVATE);
        stateString = getString(R.string.state_key);

        // Create state CollegeFilter
        state = new CollegeFilter(stateString);

        listView = view.findViewById(R.id.state_list_view);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(this::onItemClick);

        initListViewData();
    }

    private void initListViewData() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_checked, getStates());
        listView.setAdapter(arrayAdapter);
        //Set existing filters and default if no existing filters
        setExistingFilters();
    }

    private void setExistingFilters() {
        CollegeFilter existingFilter = FilterUtils.getFilter(getContext(), stateString);
        listView.setItemChecked(existingFilter.getPosition(), true);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: " +position);
        FilterUtils.putFilter(getContext(), state, listView.getItemAtPosition(position).toString(), position);
    }

    private List<String> getStates() {
        List<String> states = new ArrayList<>();
        states.addAll(College.getStates().values());
        Collections.sort(states);
        states.add(0, "All");
        return states;
    }
}
