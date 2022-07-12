package com.example.capstoneapp.ui.collegesearch;

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

import com.example.capstoneapp.model.College;
import com.example.capstoneapp.EndlessRecyclerViewScrollListener;
import com.example.capstoneapp.R;

import java.util.List;

public class CollegeSearchFragment extends Fragment {

    public static final String TAG = "CollegeSearchFragment";

    private CollegeSearchViewModel viewModel;
    protected CollegesAdapter collegesAdapter;

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

        getActivity().setTitle(R.string.college_search_title);

        collegesAdapter = new CollegesAdapter(getContext(), new CollegesAdapter.FavoriteButtonClickedCallback() {
            @Override
            public void onFavButtonClicked(College college) {
                // Update the state to Parse through View Model
                viewModel.updateFavCollege(college);
            }
        });

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

        viewModel.getCollegesListForUser();

        // Colleges observer
        Observer<List<College>> collegesObserver = new Observer<List<College>>() {
            @Override
            public void onChanged(List<College> colleges) {
                if ((colleges == null) || (colleges.size() == 0)){
                    Log.e(TAG, "No colleges found");
                } else {
                    Log.i(TAG, "Colleges found");
                    collegesAdapter.setColleges(colleges);
                }
            }
        };
        viewModel.allCollegesLiveData.observe(getViewLifecycleOwner(), collegesObserver);

        // Max ID observer
        Observer<Long> maxIdObserver = new Observer<Long>() {
            @Override
            public void onChanged(Long newMaxId) {
                if (newMaxId == null) {
                    Log.e(TAG, "Max ID is null");
                } else {
                    Log.i(TAG, "Updated Max ID");
                    maxId = newMaxId;
                }
            }
        };
        viewModel.maxId.observe(getViewLifecycleOwner(), maxIdObserver);

        // favCollegeUpdatedIndex  observer
        Observer<Integer> favCollegeUpdatedIndexObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer newIndex) {
                collegesAdapter.notifyItemChanged(newIndex);
            }
        };
        viewModel.favCollegeUpdatedIndex.observe(getViewLifecycleOwner(), favCollegeUpdatedIndexObserver);
    }
}
