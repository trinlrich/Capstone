package com.example.capstoneapp.ui.mycolleges.mycollegesdetail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.capstoneapp.R;
import com.example.capstoneapp.model.College;

public class MyCollegeDetailFragment extends Fragment {

    private MyCollegeDetailViewModel viewModel;
    College college;

    private TasksAdapter tasksAdapter;
    private RecyclerView rvTasks;
    private RecyclerView.LayoutManager layoutManager;

    public MyCollegeDetailFragment(College college) {
        this.college = college;
    }

    public static MyCollegeDetailFragment newInstance(College college) {
        return new MyCollegeDetailFragment(college);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_college_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up recycler view
        rvTasks = view.findViewById(R.id.rvToDo);
        tasksAdapter = new TasksAdapter(getContext());
        layoutManager = new LinearLayoutManager(getContext());
        rvTasks.setLayoutManager(layoutManager);
        rvTasks.setAdapter(tasksAdapter);
    }
}