package com.example.capstoneapp.ui.mycolleges.mycollegesdetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneapp.R;
import com.example.capstoneapp.model.CollegeTask;
import com.example.capstoneapp.ui.UiUtils;

import java.util.ArrayList;
import java.util.List;

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
        holder.bind(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<CollegeTask> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTaskItemName;
        private TextView tvTaskItemStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTaskItemName = itemView.findViewById(R.id.tvTaskItemName);
            tvTaskItemStatus = itemView.findViewById(R.id.tvTaskItemStatus);
        }

        public void bind(CollegeTask task) {
            UiUtils.setViewText(context, tvTaskItemName, task.getTaskName());
            UiUtils.setViewText(context, tvTaskItemStatus, task.getStatusAsText());
        }
    }
}
