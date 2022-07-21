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
import com.example.capstoneapp.model.College;
import com.example.capstoneapp.model.CollegeFilter;
import com.example.capstoneapp.ui.collegesearch.filter.FilterUtils;

import java.util.ArrayList;
import java.util.List;

public class TypeFragment extends Fragment {

    public static final String TAG = "TypeFragment";

    private SharedPreferences preferences;
    private CollegeFilter type;
    private ListView listView;
    private String typeString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferences = getContext().getSharedPreferences(getString(R.string.filter_key), Context.MODE_PRIVATE);
        typeString = getString(R.string.type_key);

        // Create state CollegeFilter
        type = new CollegeFilter(typeString);

        listView = view.findViewById(R.id.type_list_view);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(this::onItemClick);

        initListViewData();
    }

    private void initListViewData() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_checked, getTypes());
        listView.setAdapter(arrayAdapter);
        //Set existing filters and default if no existing filters
        setFilters();
    }

    private void setFilters() {
        CollegeFilter existingFilter = FilterUtils.getFilter(getContext(), typeString);
        listView.setItemChecked(existingFilter.getPosition(), true);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: " +position);
        FilterUtils.putFilter(getContext(), type, listView.getItemAtPosition(position).toString(), position);
    }

    private List<String> getTypes() {
        List<String> types = new ArrayList<>();
        types.add("All");
        types.addAll(College.getCollegeTypes().values());
        return types;
    }
}
