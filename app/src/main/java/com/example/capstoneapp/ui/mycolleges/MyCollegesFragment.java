package com.example.capstoneapp.ui.mycolleges;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devhoony.lottieproegressdialog.LottieProgressDialog;
import android.widget.TextView;
import com.example.capstoneapp.R;
import com.example.capstoneapp.model.College;
import com.example.capstoneapp.ui.collegesearch.CollegeSearchViewModel;

import java.util.List;

public class MyCollegesFragment extends Fragment {

    public static final String TAG = "MyCollegesFragment";
    // This is a shared viewmodel
    private CollegeSearchViewModel viewModel;

    private MyCollegesAdapter favCollegesAdapter;
    private RecyclerView favCollegesRV;
    private RecyclerView.LayoutManager layoutManager;
    private LottieProgressDialog loadingProgressBar;

    private TextView tvNoFavColleges;
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

        tvNoFavColleges = view.findViewById(R.id.tvNoFavColleges);

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

        // Colleges observer
        Observer<List<College>> favCollegesObserver = colleges -> {
            if ((colleges == null) || (colleges.size() == 0)){
                Log.e(TAG, "No Fav colleges found");
                tvNoFavColleges.setVisibility(View.VISIBLE);
            } else {
                Log.i(TAG, "Fav Colleges found");
                tvNoFavColleges.setVisibility(View.GONE);
                favCollegesAdapter.setFavColleges(colleges);
            }
        };
        viewModel.getAllFavCollegesLiveData().observe(getViewLifecycleOwner(), favCollegesObserver);

        // Progress update observer
        Observer<Boolean> progressUpdateObserver = visible -> {
            if (visible) {
                showProgress();
                tvNoFavColleges.setVisibility(View.GONE);
            } else {
                stopProgress();
            }
        };
        viewModel.getShowProgress().observe(getViewLifecycleOwner(), progressUpdateObserver);
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
}
