package com.example.capstoneapp.ui.collegesearch;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.capstoneapp.College;
import com.example.capstoneapp.EndlessRecyclerViewScrollListener;
import com.example.capstoneapp.R;
import com.example.capstoneapp.ui.collegesearch.collegedetail.CollegeDetailFragment;
import com.example.capstoneapp.ui.collegesearch.filter.FilterFragment;

import java.util.ArrayList;
import java.util.List;

public class CollegeSearchFragment extends Fragment {

    public static final String TAG = "CollegeSearchFragment";

    private CollegeSearchViewModel viewModel;

    private Spinner spinner;
    private ArrayAdapter<College> arrayAdapter;
    private String[] cities;

    protected List<College> allColleges;
    protected CollegesAdapter collegesAdapter;

    private Button btnFilter;
    private RecyclerView rvColleges;
    private EndlessRecyclerViewScrollListener scrollListener;


    protected Long maxId;

    public static CollegeSearchFragment newInstance() {
        return new CollegeSearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_college_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CollegeSearchViewModel.class);

        FragmentActivity activity = getActivity();

        if (activity != null) {
            activity.setTitle(R.string.college_search_title);
        }

        // Recycler View
        allColleges = new ArrayList<>();
        collegesAdapter = new CollegesAdapter(getContext(), allColleges);

        btnFilter = view.findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(this::onFilterClick);

        rvColleges = view.findViewById(R.id.rvColleges);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvColleges.setAdapter(collegesAdapter);
        rvColleges.setLayoutManager(linearLayoutManager);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                viewModel.loadNextDataFromParse(maxId);
            }
        };
        rvColleges.addOnScrollListener(scrollListener);

        viewModel.getCollegesList();

        // Colleges observer
        Observer<List<College>> collegesObserver = colleges -> {
            if (colleges == null) {
                Log.e(TAG, "No colleges found");
            } else {
                Log.i(TAG, "Colleges found");
                allColleges.addAll(colleges);
                collegesAdapter.notifyDataSetChanged();
            }
        };
        viewModel.allColleges.observe(getViewLifecycleOwner(), collegesObserver);

        // Max ID observer
        Observer<Long> maxIdObserver = newMaxId -> {
            if (newMaxId == null) {
                Log.e(TAG, "Max ID is null");
            } else {
                Log.i(TAG, "Updated Max ID");
                maxId = newMaxId;
            }
        };
        viewModel.maxId.observe(getViewLifecycleOwner(), maxIdObserver);
    }

    public void onFilterClick(View view) {
        FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();

        if (fragmentManager != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.flContainer, FilterFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        }
    }
}