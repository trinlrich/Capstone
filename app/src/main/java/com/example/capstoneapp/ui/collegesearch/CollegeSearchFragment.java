package com.example.capstoneapp.ui.collegesearch;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneapp.R;
import com.example.capstoneapp.model.College;
import com.example.capstoneapp.ui.collegesearch.filter.FilterFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class CollegeSearchFragment extends Fragment {

    public static final String TAG = "CollegeSearchFragment";
    protected CollegesAdapter collegesAdapter;
    private CollegeSearchViewModel viewModel;
    private RecyclerView rvColleges;
    private ConstraintLayout rootLayout;
    private SearchView svCollegeSearch;
    private ImageButton btnFilter;
    private TextView tvNoColleges;
    private ProgressBar loadingProgressBar;

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

        // this is a shared vm , so created based on Activity
        viewModel = new ViewModelProvider(requireActivity()).get(CollegeSearchViewModel.class);
        viewModel.filterCollegesList();

        if (getActivity() != null) {
            getActivity().setTitle(R.string.college_search_title);
        }

        // Pass the callback for Fav button click
        collegesAdapter = new CollegesAdapter(getContext(), college -> {
            // Update the state to Parse through View Model
            viewModel.updateFavCollege(college);
        });
        rootLayout = view.findViewById(R.id.topLayout);
        rvColleges = view.findViewById(R.id.rvColleges);
        svCollegeSearch = view.findViewById(R.id.svCollegeSearch);
        svCollegeSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.searchFilterCollegeList(newText);
                return false;
            }
        });
        btnFilter = view.findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(this::onFilterClick);
        tvNoColleges = view.findViewById(R.id.tvNoColleges);
        loadingProgressBar = view.findViewById(R.id.progressBar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvColleges.setAdapter(collegesAdapter);
        rvColleges.setLayoutManager(linearLayoutManager);

        // Colleges observer
        Observer<List<College>> collegesObserver = colleges -> {
            if ((colleges == null) || (colleges.size() == 0)) {
                Log.e(TAG, "No colleges found");
                showNoColleges();
            } else {
                Log.i(TAG, "Colleges found");
                collegesAdapter.setColleges(colleges);
                showColleges();
                rvColleges.smoothScrollToPosition(0);
            }
        };
        viewModel.getAllCollegesLiveData().observe(getViewLifecycleOwner(), collegesObserver);

        // favCollegeUpdatedIndex  observer
        Observer<Integer> favCollegeUpdatedIndexObserver = newIndex -> collegesAdapter.notifyItemChanged(newIndex);
        viewModel.favCollegeUpdatedIndex.observe(getViewLifecycleOwner(), favCollegeUpdatedIndexObserver);

        // favCollegeProcessError  observer
        Observer<Boolean> favCollegeErrorObserver = newIndex -> Snackbar.make(rootLayout, "Error in Updating Fav", Snackbar.LENGTH_LONG).show();
        viewModel.favCollegeProcessError.observe(getViewLifecycleOwner(), favCollegeErrorObserver);

        // Progress update observer
        Observer<Boolean> progressUpdateObserver = visible -> {
            if (visible) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                tvNoColleges.setVisibility(View.GONE);
            } else
                loadingProgressBar.setVisibility(View.GONE);
        };
        viewModel.getShowProgress().observe(getViewLifecycleOwner(), progressUpdateObserver);

    }

    private void showNoColleges() {
        if (loadingProgressBar.getVisibility() != View.VISIBLE) {
            rvColleges.setVisibility(View.INVISIBLE);
            tvNoColleges.setVisibility(View.VISIBLE);
        }
    }

    private void showColleges() {
        rvColleges.setVisibility(View.VISIBLE);
        tvNoColleges.setVisibility(View.GONE);
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
