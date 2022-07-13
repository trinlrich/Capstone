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

public class TypeFragment extends Fragment {

    public static final String TAG = "TypeFragment";

    SharedPreferences preferences;
    CollegeFilter type;
    ListView listView;

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

        // Create state CollegeFilter
        type = new CollegeFilter();
        type.setKey(getString(R.string.type_key));

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
        String existingTypeJson = preferences.getString(type.getKey(), null);
        CollegeFilter existingFilter = new Gson().fromJson(existingTypeJson, CollegeFilter.class);
        listView.setItemChecked(existingFilter.getPosition(), true);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: " +position);
        type.setValue(listView.getItemAtPosition(position).toString());
        type.setPosition(position);

        SharedPreferences.Editor editor = preferences.edit();
        // Convert object to JSON string to put in preferences
        String typeJson = new Gson().toJson(type);
        editor.putString(type.getKey(), typeJson);
        editor.commit();
    }

    private String[] getTypes() {
        String allTypes = getString(R.string.all_filters);
        String publicType = getString(R.string.public_type);
        String privateNPType = getString(R.string.private_np_type);
        String privateFPType = getString(R.string.private_fp_type);

        String[] types = new String[]{allTypes, publicType, privateNPType, privateFPType};

        return types;
    }
}