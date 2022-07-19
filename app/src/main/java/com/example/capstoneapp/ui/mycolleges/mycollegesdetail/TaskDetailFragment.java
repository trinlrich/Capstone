package com.example.capstoneapp.ui.mycolleges.mycollegesdetail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.capstoneapp.R;
import com.example.capstoneapp.model.CollegeApplicationTask;
import com.example.capstoneapp.ui.UiUtils;

import java.util.ArrayList;
import java.util.List;

public class TaskDetailFragment extends Fragment {

    private static final String TAG = "TaskDetailFragment";
    private CollegeApplicationTask task;
    private List<String> statusList;
    private TextView tvTaskDetailName;
    private RelativeLayout spinnerLayout;
    private Spinner statusSpinner;
    private TextView tvRelativeTimeUntil;

    public TaskDetailFragment(CollegeApplicationTask task) {
        this.task = task;
    }

    public static TaskDetailFragment newInstance(CollegeApplicationTask task) {
        return new TaskDetailFragment(task);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        statusList = new ArrayList<>(CollegeApplicationTask.STATUSES.values());
        tvTaskDetailName = view.findViewById(R.id.tvTaskDetailName);
        spinnerLayout = view.findViewById(R.id.spinnerLayout);
        statusSpinner = view.findViewById(R.id.statusSpinner);
        tvRelativeTimeUntil = view.findViewById(R.id.tvRelativeTimeUntil);

        UiUtils.setViewText(getContext(), tvTaskDetailName, task.getTaskTitle());
//        Log.i(TAG, task.calculateTimeUntil());
//        UiUtils.setViewText(getContext(), tvRelativeTimeUntil, task.calculateTimeUntil());

        spinnerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusSpinner.performClick();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, statusList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(adapter);
        statusSpinner.setSelection(task.getTaskState());
        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateColor(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void updateColor(String status) {
        Log.i(TAG, "Status: " + status);
        statusSpinner.setBackgroundColor(CollegeApplicationTask.getStatusColor(status));
        switch (status) {
            case "To Do":
                spinnerLayout.setBackground(getResources().getDrawable(R.drawable.spinner_todo_background));
                break;
            case "In Progress":
                spinnerLayout.setBackground(getResources().getDrawable(R.drawable.spinner_inprogress_background));
                break;
            case "Complete":
                spinnerLayout.setBackground(getResources().getDrawable(R.drawable.spinner_complete_background));
                break;
            default:
                break;
        }

    }
}
