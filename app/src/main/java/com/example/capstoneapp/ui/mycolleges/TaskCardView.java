package com.example.capstoneapp.ui.mycolleges;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.capstoneapp.R;
import com.example.capstoneapp.model.CollegeApplicationTask;
import com.example.capstoneapp.ui.UiUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class TaskCardView extends CardView {

    private static final String TAG = "TaskCardView";
    Context context;
    CollegeApplicationTask task;
    LinearLayout layout;
    TextView tvTaskCardTitle;
    ImageView ivClockIcon;
    TextView tvTaskCardDeadline;
    CardView deadlineCard;

    private final HashMap<Integer, Integer> STATUS_COLORS = new HashMap<>();
    public static final int CLOSE_DEADLINE_CONSTANT = 7;

    public TaskCardView(Context context) {
        super(context);
        this.context = context;
        init(null, 0);
    }

    public TaskCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs, 0);
    }

    public TaskCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.task_card_view, this, true);

        layout = findViewById(R.id.taskCardLayout);
        tvTaskCardTitle = findViewById(R.id.tvTaskCardTitle);
        ivClockIcon = findViewById(R.id.ivClockIcon);
        tvTaskCardDeadline = findViewById(R.id.tvTaskCardDeadline);
        deadlineCard = findViewById(R.id.deadlineCard);

    }
    public void setTaskInfo(CollegeApplicationTask task){
        if (task == null) {
            return;
        }
        this.task = task;
        UiUtils.setViewText(context, tvTaskCardTitle, task.getTaskTitle());
        UiUtils.setViewText(context, tvTaskCardDeadline, getDateNoTime(task.getTaskEndDate()));
        setTaskColor();
        if (task.getTaskState() == 0 || task.getTaskState() == 1) {
            setDeadlineColor(task.getTaskState(), task.getTaskEndDate());
        }
    }

    public void setTaskColor() {
        layout.setBackgroundColor(task.getStatusColor(task.getTaskState()));
    }

    public void setDeadlineColor(int state, Long taskEndDate) {
        long now = System.currentTimeMillis();
        long diff = (taskEndDate - now) / (1000 * 3600 * 24);
        if ((state == 0 || state == 1) && diff < CLOSE_DEADLINE_CONSTANT) {
            deadlineCard.setCardBackgroundColor(context.getColor(R.color.to_do_red));
            ivClockIcon.setImageResource(R.drawable.clock_white_outline_24);
            tvTaskCardDeadline.setTextColor(context.getColor(R.color.white));
        } else {
            deadlineCard.setCardBackgroundColor(context.getColor(R.color.grey));
        }
    }

    private String getDateNoTime(Long dateLong) {
        Date date = new Date(dateLong);
        SimpleDateFormat df2 = new SimpleDateFormat("MMMM d, yyyy");
        return df2.format(date);
    }

}