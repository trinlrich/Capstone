package com.example.capstoneapp.ui.collegesearch;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import com.example.capstoneapp.ui.SharedPreferenceUtils;
import com.example.capstoneapp.ui.collegesearch.filter.FilterFragment;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

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

            // if it already favorite then user wants to un-favorite it.
            // show dialog and get user confirmation
            if (viewModel.isCollegeFavorited(college)) {
                createAndShowDialog(college);
            } else
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
                svCollegeSearch.setVisibility(View.GONE);
                btnFilter.setVisibility(View.GONE);
            } else {
                loadingProgressBar.setVisibility(View.GONE);
                svCollegeSearch.setVisibility(View.VISIBLE);
                btnFilter.setVisibility(View.VISIBLE);
            }
        };
        viewModel.getShowProgress().observe(getViewLifecycleOwner(), progressUpdateObserver);

        // Check if user's first time visiting this screen and run TapTargetSequence
        if (SharedPreferenceUtils.getIsFirstTimeVisit(getContext(), FirebaseAuth.getInstance().getUid())) {
            startTapTargetSequence();
            SharedPreferenceUtils.putIsFirstTimeVisit(getContext(), FirebaseAuth.getInstance().getUid(), false);
        }
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

    public void createAndShowDialog(College college) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.unfavorite_dialog_title)
                .setMessage(R.string.unfavorite_dialog_message)
                .setPositiveButton(R.string.unfavorite_dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Un favorite the College and delete tasks!
                        // Update the state to Parse through View Model
                        viewModel.updateFavCollege(college);
                    }
                })
                .setNegativeButton(R.string.unfavorite_dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog. do nothing

                    }
                });
        // Create the AlertDialog object and show it
        builder.create().show();
    }

    @Override
    public void onPause() {
        super.onPause();
        hideKeyboard();
    }

    private void hideKeyboard() {
        if (getActivity() != null) {
            // Check if no view has focus:
            View view = getActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    private void checkUserVisited() {

    }

    private void startTapTargetSequence() {
        new TapTargetSequence(getActivity()).targets(
                createTapTargetForView(
                        getActivity().findViewById(R.id.svCollegeSearch),
                        "This is the search bar",
                        "Here you can search through the colleges by name or city",
                        R.color.accent_blue,
                        getContext().getDrawable(R.drawable.tap_target_search_icon),
                        30),
                createTapTargetForView(
                        getActivity().findViewById(R.id.btnFilter),
                        "This is the filter icon",
                        "Here you can filter the colleges by state, type, or mission",
                        R.color.accent_orange,
                        null,
                        30)
        ).start();
    }

    private TapTarget createTapTargetForView(View target, String title, String description, int accentColor, Drawable icon, int targetRadius) {
        TapTarget tapTarget = TapTarget.forView(target, title, description)
                // All options below are optional
                .outerCircleColor(accentColor)      // Specify a color for the outer circle
                .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                .targetCircleColor(R.color.white)   // Specify a color for the target circle
                .titleTextSize(20)                  // Specify the size (in sp) of the title text
                .titleTextColor(R.color.white)      // Specify the color of the title text
                .descriptionTextSize(14)            // Specify the size (in sp) of the description text
                .descriptionTextColor(accentColor)  // Specify the color of the description text
                .textColor(R.color.white)           // Specify a color for both the title and description text
                .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                .drawShadow(true)                   // Whether to draw a drop shadow or not
                .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                .tintTarget(true)                   // Whether to tint the target view's color
                .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                .targetRadius(targetRadius);                  // Specify the target radius (in dp)

        if (icon != null) {
            tapTarget.icon(icon);                   // Specify a custom drawable to draw as the target
        }
        return tapTarget;
    }
}
