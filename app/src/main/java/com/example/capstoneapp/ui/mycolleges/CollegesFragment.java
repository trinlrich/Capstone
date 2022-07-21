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
import android.widget.ProgressBar;

import com.devhoony.lottieproegressdialog.LottieProgressDialog;
import com.example.capstoneapp.R;
import com.example.capstoneapp.model.College;
import com.example.capstoneapp.ui.collegesearch.CollegeSearchViewModel;

import java.util.List;

public class CollegesFragment extends Fragment {

    public static final String TAG = "CollegesFragment";
    // This is a shared viewmodel
    private CollegeSearchViewModel viewModel;

    private MyCollegesAdapter favCollegesAdapter;
    private RecyclerView favCollegesRV;
    private RecyclerView.LayoutManager layoutManager;
    private LottieProgressDialog progressBar;

    public static CollegesFragment newInstance() {
        return new CollegesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_colleges, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(CollegeSearchViewModel.class);

        if (getActivity() != null) {
            getActivity().setTitle(R.string.colleges_title);
        }

        // Set up recycler view
        favCollegesRV = view.findViewById(R.id.favCollegesRV);
        favCollegesAdapter = new MyCollegesAdapter(getContext());
        layoutManager = new LinearLayoutManager(getContext());
        favCollegesRV.setLayoutManager(layoutManager);
        favCollegesRV.setAdapter(favCollegesAdapter);
        setupProgress();

        // Colleges observer
        Observer<List<College>> favCollegesObserver = colleges -> {
            if ((colleges == null) || (colleges.size() == 0)){
                Log.e(TAG, "No Fav colleges found");
            } else {
                Log.i(TAG, "Fav Colleges found");
                stopProgress();
                favCollegesAdapter.setFavColleges(colleges);
            }
        };
        showProgress();
        viewModel.getAllFavCollegesLiveData().observe(getViewLifecycleOwner(), favCollegesObserver);
    }

    private void showProgress(){
        progressBar.show();
    }

    private void stopProgress(){
        if (progressBar.isShowing())
            progressBar.cancel();
    }

    private void setupProgress(){
        String title = getString(R.string.progress_title);
        progressBar = new LottieProgressDialog(getActivity(),true,null,null,null,null,LottieProgressDialog.SAMPLE_5, title, null);

    }
}
