package com.example.capstoneapp.ui.mycolleges;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.capstoneapp.R;
import com.example.capstoneapp.model.CollegeApplicationTask;
import com.example.capstoneapp.parsedatasource.Utilities;
import com.example.capstoneapp.ui.UiUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class TaskProgressView extends ConstraintLayout {

    private final HashMap<Integer, Integer> STATUS_COLORS = new HashMap<>();
    View view1,view2,view3,view4,view5,view1Line,view2Line,view3Line,view4Line;

    public TaskProgressView(Context context) {
        super(context);
        init(null, 0);
    }

    public TaskProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public TaskProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.task_progress_view, this, true);

        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view3);
        view3 = findViewById(R.id.view5);
        view4 = findViewById(R.id.view7);
        view5 = findViewById(R.id.view9);

        view1Line = findViewById(R.id.view2);
        view2Line = findViewById(R.id.view4);
        view3Line = findViewById(R.id.view6);
        view4Line = findViewById(R.id.view8);

        createStatusColorsMap();
    }
    public void setTasks(List<CollegeApplicationTask> tasks) {
        if (tasks == null)
            return;

        List<CollegeApplicationTask> mandatoryTasks = new ArrayList<>();
        // Remove tasks that are not mandatory
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTaskPriority() == 1) {
                mandatoryTasks.add(tasks.get(i));
            }
        }

        // sort the tasks as per priority. we need completed to come first
        if (mandatoryTasks.size() > 0) {
            Collections.sort(mandatoryTasks,Collections.reverseOrder());
            int color = getContext().getColor(STATUS_COLORS.get(mandatoryTasks.get(0).getTaskState()));
            view1.getBackground().setTint(color);
            view1Line.getBackground().setTint(color);
            color = getContext().getColor(STATUS_COLORS.get(mandatoryTasks.get(1).getTaskState()));
            view2.getBackground().setTint(color);
            view2Line.getBackground().setTint(color);
            color = getContext().getColor(STATUS_COLORS.get(mandatoryTasks.get(2).getTaskState()));
            view3.getBackground().setTint(color);
            view3Line.getBackground().setTint(color);
            color = getContext().getColor(STATUS_COLORS.get(mandatoryTasks.get(3).getTaskState()));
            view4.getBackground().setTint(color);
            view4Line.getBackground().setTint(color);
            color = getContext().getColor(STATUS_COLORS.get(mandatoryTasks.get(4).getTaskState()));
            view5.getBackground().setTint(color);
            invalidate();
        }
    }

    private void createStatusColorsMap() {
        STATUS_COLORS.put(0, R.color.to_do_red);
        STATUS_COLORS.put(1, R.color.in_progress_yellow);
        STATUS_COLORS.put(2, R.color.complete_green);
    }

}