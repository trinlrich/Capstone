package com.example.capstoneapp.ui.mycolleges;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.devhoony.lottieproegressdialog.LottieProgressDialog;
import com.example.capstoneapp.R;
import com.example.capstoneapp.model.College;
import com.example.capstoneapp.model.CollegeApplicationTask;
import com.example.capstoneapp.ui.collegesearch.CollegeSearchFragment;
import com.example.capstoneapp.ui.collegesearch.CollegeSearchViewModel;
import com.example.capstoneapp.ui.collegesearch.filter.FilterFragment;

import java.util.List;
import java.util.Map;

public class MyCollegesFragment extends Fragment {

    public static final String TAG = "MyCollegesFragment";
    // This is a shared viewmodel
    private CollegeSearchViewModel viewModel;

    private MyCollegesAdapter favCollegesAdapter;
    private RecyclerView favCollegesRV;
    private RecyclerView.LayoutManager layoutManager;
    private LottieProgressDialog loadingProgressBar;
    private List<College> favColleges;

    private CardView noFavCollegesCard;
    private Button btnToCollegeSearch;

    public static MyCollegesFragment newInstance() {
        return new MyCollegesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_colleges, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(CollegeSearchViewModel.class);

        if (getActivity() != null) {
            getActivity().setTitle(R.string.my_colleges_title);
        }

        noFavCollegesCard = view.findViewById(R.id.noFavCollegesCard);
        btnToCollegeSearch = view.findViewById(R.id.btnToCollegeSearch);
        btnToCollegeSearch.setOnClickListener(this::onToCollegeSearchClick);

        // Set up recycler view
        favCollegesRV = view.findViewById(R.id.favCollegesRV);
        favCollegesAdapter = new MyCollegesAdapter(getContext(), college -> {
            // Update the state to Parse through View Model
            viewModel.updateFavCollege(college);
        });
        layoutManager = new LinearLayoutManager(getContext());
        favCollegesRV.setLayoutManager(layoutManager);
        favCollegesRV.setAdapter(favCollegesAdapter);
        setupProgress();

        // Fav Colleges Tasks Observer
        Observer<Map<Integer,List<CollegeApplicationTask>>> favCollegesTasksObserver = new Observer<Map<Integer, List<CollegeApplicationTask>>>() {
            @Override
            public void onChanged(Map<Integer, List<CollegeApplicationTask>> favCollegesTasks) {
                favCollegesAdapter.setFavColleges(favColleges,favCollegesTasks );
            }
        };
        //Colleges observer
        Observer<List<College>> favCollegesObserver = colleges -> {
            if ((colleges == null) || (colleges.size() == 0)){
                Log.e(TAG, "No Fav colleges found");
                showNoFavColleges();
            } else {
                Log.i(TAG, "Fav Colleges found");
                hideNoFavColleges();
                favColleges = colleges;
                viewModel.getAllFavCollegeTasks(favColleges).observe(getViewLifecycleOwner(),favCollegesTasksObserver);
            }
        };
        viewModel.getAllFavCollegesLiveData().observe(getViewLifecycleOwner(), favCollegesObserver);

        // Progress update observer
        Observer<Boolean> progressUpdateObserver = visible -> {
            if (visible) {
                showProgress();
                hideNoFavColleges();
            } else {
                stopProgress();
            }
        };
        viewModel.getShowProgress().observe(getViewLifecycleOwner(), progressUpdateObserver);
    }

    private void onToCollegeSearchClick(View view) {
        FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
        if (fragmentManager != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.flContainer, CollegeSearchFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void showProgress(){
        loadingProgressBar.show();
    }

    private void stopProgress(){
        if (loadingProgressBar.isShowing())
            loadingProgressBar.cancel();
    }

    private void setupProgress(){
        String title = getString(R.string.progress_title);
        loadingProgressBar = new LottieProgressDialog(getActivity(),false,null,null,null,null,LottieProgressDialog.SAMPLE_5, title, null);

    }

    private void showNoFavColleges() {
        favCollegesRV.setVisibility(View.GONE);
        noFavCollegesCard.setVisibility(View.VISIBLE);
    }

    private void hideNoFavColleges() {
        noFavCollegesCard.setVisibility(View.GONE);
        favCollegesRV.setVisibility(View.VISIBLE);
    }
}
