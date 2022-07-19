package com.example.capstoneapp.ui.mycolleges.mycollegedetail;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.capstoneapp.R;
import com.example.capstoneapp.model.CollegeApplicationTask;

import java.util.ArrayList;
import java.util.List;

public class MyCollegeDetailFragment extends Fragment {

    private static final String USERID = "userid";
    private static final String COLLEGEID = "collegeid";
    private MyCollegeDetailViewModel myCollegeDetailViewModel;

    private String userId = "";
    private Integer collegeId = 0;

    private List<CollegeApplicationTask> applicationStepsList = new ArrayList<>();
    private Button manageTaskButton;


    public static MyCollegeDetailFragment newInstance(String userId, Integer collegeId) {
        MyCollegeDetailFragment f = new MyCollegeDetailFragment();
        Bundle args = new Bundle();
        args.putString(USERID, userId);
        args.putInt(COLLEGEID, collegeId);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_college_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments().containsKey(USERID)){
            userId = getArguments().getString(USERID);
        }
        if (getArguments().containsKey(COLLEGEID)){
            collegeId = getArguments().getInt(COLLEGEID);
        }

        MyCollegeDetailViewModelFactory  factory = new MyCollegeDetailViewModelFactory(userId,collegeId);
        myCollegeDetailViewModel = new ViewModelProvider( this, factory).get(MyCollegeDetailViewModel.class);

        myCollegeDetailViewModel.getCollegeTasksLiveData().observe(getActivity(), new Observer<List<CollegeApplicationTask>>() {
            @Override
            public void onChanged(List<CollegeApplicationTask> tasks) {
                applicationStepsList = tasks;
            }
        });

        manageTaskButton = view.findViewById(R.id.manageTaskBtn);
        manageTaskButton.setOnClickListener(v -> {
            Fragment fragment = AppStepManagementFragment.newInstance(userId,collegeId);
            ((FragmentActivity) getActivity()).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        });
        // TODO: Use the ViewModel
        myCollegeDetailViewModel.getAllApplicationSteps(userId,collegeId);
    }

}
