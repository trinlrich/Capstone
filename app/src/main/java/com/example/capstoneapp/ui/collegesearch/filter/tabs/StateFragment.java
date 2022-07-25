package com.example.capstoneapp.ui.collegesearch.filter.tabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.capstoneapp.R;
import com.example.capstoneapp.model.College;
import com.example.capstoneapp.model.CollegeFilter;
import com.example.capstoneapp.ui.collegesearch.filter.FilterUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StateFragment extends Fragment {

    public static final String TAG = "StateFragment";

    private SharedPreferences preferences;
    private ListView listView;
    private String stateString;
    private List<CollegeFilter> stateFilters =  new ArrayList<>();

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

        listView = view.findViewById(R.id.state_list_view);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
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
        List<CollegeFilter> existingFilter = FilterUtils.getFilterList(getContext(), stateString);
        for (CollegeFilter filter : existingFilter) {
            listView.setItemChecked(filter.getPosition(), true);
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: " +position);
        if (listView.getCheckedItemCount() == 0) {
            listView.setItemChecked(position, true);
            return;
        }
        if (position == 0 && listView.isItemChecked(0)) {
            resetItems();
        } else if (listView.isItemChecked(0)) {
            listView.setItemChecked(0, false);
            stateFilters.clear();
        }
        if (listView.isItemChecked(position)) {
            CollegeFilter state = new CollegeFilter(stateString);
            state.setValue(listView.getItemAtPosition(position).toString());
            state.setPosition(position);
            stateFilters.add(state);
            FilterUtils.putFilter(getContext(), stateFilters);
        }
    }

    private List<String> getStates() {
        List<String> states = new ArrayList<>();
        states.addAll(College.getStates().values());
        Collections.sort(states);
        states.add(0, "All");
        return states;
    }

    private void resetItems() {
        for (int i = 1; i < listView.getAdapter().getCount(); i++) {
            listView.setItemChecked(i, false);
        }
        stateFilters.clear();
    }

}
