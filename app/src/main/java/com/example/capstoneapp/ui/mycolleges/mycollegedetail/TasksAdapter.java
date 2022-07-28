package com.example.capstoneapp.ui.mycolleges.mycollegedetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneapp.R;
import com.example.capstoneapp.model.CollegeApplicationTask;
import com.example.capstoneapp.ui.UiUtils;

import java.util.ArrayList;
import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    private static final String TAG = "TasksAdapter";
    private final Context context;
    private List<CollegeApplicationTask> tasks = new ArrayList<>();

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
        CollegeApplicationTask task = tasks.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<CollegeApplicationTask> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView taskItemCard;
        private TextView tvTaskItemName;
        private TextView tvTaskItemStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            taskItemCard = itemView.findViewById(R.id.taskItemCard);
            tvTaskItemName = itemView.findViewById(R.id.tvTaskItemName);
            tvTaskItemStatus = itemView.findViewById(R.id.tvTaskItemStatus);
        }

        public void bind(CollegeApplicationTask task) {
            taskItemCard.setCardBackgroundColor(task.getStatusColor(task.getTaskState()));
            UiUtils.setViewText(context, tvTaskItemName, task.getTaskTitle());
            UiUtils.setViewText(context, tvTaskItemStatus, task.getTaskStateAsText());
        }
    }
}