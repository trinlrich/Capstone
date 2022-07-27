package com.example.capstoneapp.ui.mycolleges.mycollegedetail.taskmgmt;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

    private TaskDetailViewModel viewModel;
    private CollegeApplicationTask task;
    private RelativeLayout spinnerLayout;
    private Spinner statusSpinner;
    private List<String> statusList;
    private ImageButton btnCloseTaskDetail;
    private EditText etTaskTitle;
    private TextView tvTaskDeadline;
    private TextView tvRelativeTimeUntil;
    private EditText etTaskNotes;
    private Button btnSave;

    private int updatedTaskStatus;

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
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        viewModel = new ViewModelProvider(requireActivity()).get(TaskDetailViewModel.class);

        statusList = new ArrayList<>(CollegeApplicationTask.STATUSES.values());
        spinnerLayout = view.findViewById(R.id.spinnerLayout);
        statusSpinner = view.findViewById(R.id.statusSpinner);
        btnCloseTaskDetail = view.findViewById(R.id.btnCloseTaskDetail);
        etTaskTitle = view.findViewById(R.id.tvTaskDetailName);
        tvTaskDeadline = view.findViewById(R.id.tvTaskDeadline);
        tvRelativeTimeUntil = view.findViewById(R.id.tvRelativeTimeUntil);
        etTaskNotes = view.findViewById(R.id.etTaskNotes);
        btnSave = view.findViewById(R.id.btnSaveTask);

        UiUtils.setViewText(getContext(), etTaskTitle, task.getTaskTitle());
        UiUtils.setViewText(getContext(), tvTaskDeadline, task.getTaskEndDateAsText());
        UiUtils.setViewText(getContext(), tvRelativeTimeUntil, task.calculateTimeUntil());
        UiUtils.setViewText(getContext(), etTaskNotes, task.getTaskDescription());
        btnCloseTaskDetail.setOnClickListener(this::onCloseClick);
        btnSave.setOnClickListener(this::onSaveClick);

        spinnerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusSpinner.performClick();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.state_spinner_item, statusList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(adapter);
        statusSpinner.setSelection(task.getTaskState());
        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updatedTaskStatus = CollegeApplicationTask.convertStatusStringToCode(parent.getItemAtPosition(position).toString());
                updateColor(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No changes if nothing selected
            }
        });
    }

    private void updateColor(String status) {
        Log.i(TAG, "Status: " + status);
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

    private void onCloseClick(View view) {
        createAndShowDialog();
    }

    private void onSaveClick(View view) {
        task.setTaskTitle(etTaskTitle.getText().toString());
        task.setTaskState(updatedTaskStatus);
        task.setTaskEndDate(CollegeApplicationTask.convertDateStringToLong(tvTaskDeadline.getText().toString()));
        task.setTaskDescription(etTaskNotes.getText().toString());
        viewModel.updateTask(task);
        getActivity().getSupportFragmentManager().popBackStack();
    }

    public void createAndShowDialog() {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.close_task_dialog_title)
                .setMessage(R.string.close_task_dialog_message)
                .setPositiveButton(R.string.close_task_dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Close the task without saving !
                        // Update the state to Parse through View Model
                        dialog.cancel();
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                })
                .setNegativeButton(R.string.close_task_dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog. do nothing
                        dialog.cancel();
                    }
                });
        // Create the AlertDialog object and show it
        builder.create().show();
    }
}