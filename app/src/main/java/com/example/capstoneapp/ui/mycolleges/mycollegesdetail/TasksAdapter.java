package com.example.capstoneapp.ui.mycolleges.mycollegesdetail;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneapp.R;
import com.example.capstoneapp.model.CollegeTask;
import com.example.capstoneapp.ui.UiUtils;
import com.example.capstoneapp.ui.collegesearch.collegedetail.CollegeDetailFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    private static final String TAG = "TasksAdapter";
    private final Context context;
    private List<CollegeTask> tasks = new ArrayList<>();

    public TasksAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new TasksAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CollegeTask task = tasks.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<CollegeTask> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView taskItemCard;
        private TextView tvTaskItemName;
        private TextView tvTaskItemStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            taskItemCard = itemView.findViewById(R.id.taskItemCard);
            tvTaskItemName = itemView.findViewById(R.id.tvTaskItemName);
            tvTaskItemStatus = itemView.findViewById(R.id.tvTaskItemStatus);
        }

        public void bind(CollegeTask task) {
            taskItemCard.setCardBackgroundColor(CollegeTask.getStatusColor(task.getStatusAsText()));
            UiUtils.setViewText(context, tvTaskItemName, task.getTaskName());
            UiUtils.setViewText(context, tvTaskItemStatus, task.getStatusAsText());
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            CollegeTask task = tasks.get(getAdapterPosition());
            Log.i(TAG, "Task clicked: " + task.getTaskName());
            Fragment fragment = TaskDetailFragment.newInstance(task);
            ((FragmentActivity) context).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
