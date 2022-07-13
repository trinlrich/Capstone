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
import com.example.capstoneapp.ui.collegesearch.filter.CollegeFilter;
import com.google.gson.Gson;

public class StateFragment extends Fragment {

    public static final String TAG = "StateFragment";

    SharedPreferences preferences;
    CollegeFilter state;
    ListView listView;

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

        // Create state CollegeFilter
        state = new CollegeFilter();
        state.setKey(getString(R.string.state_key));

        listView = view.findViewById(R.id.state_list_view);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(this::onItemClick);

        initListViewData();
    }

    private void initListViewData() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_checked, getStates());
        listView.setAdapter(arrayAdapter);
        //Set existing filters and default if no existing filters
        setFilters();
    }

    private void setFilters() {
        String existingStateJson = preferences.getString(state.getKey(), null);
        CollegeFilter existingFilter = new Gson().fromJson(existingStateJson, CollegeFilter.class);
        listView.setItemChecked(existingFilter.getPosition(), true);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: " +position);
        state.setValue(listView.getItemAtPosition(position).toString());
        state.setPosition(position);

        SharedPreferences.Editor editor = preferences.edit();
        // Convert object to JSON string to put in preferences
        String stateJson = new Gson().toJson(state);
        editor.putString(state.getKey(), stateJson);
        editor.commit();
    }

    private String[] getStates() {
        String allStates = getString(R.string.all_filters);
        // TODO::Improve state collection
        String georgia = "GA";
        String northCarolina = "NC";
        String california = "CA";
        String alabama = "AL";
        String kansas = "KS";

        String[] states = new String[]{allStates, georgia, northCarolina, california, alabama, kansas};

        return states;
    }
}