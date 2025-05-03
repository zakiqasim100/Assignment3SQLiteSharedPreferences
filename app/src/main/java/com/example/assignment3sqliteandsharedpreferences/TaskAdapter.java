package com.example.assignment3sqliteandsharedpreferences;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<String[]> taskList;

    public TaskAdapter(List<String[]> taskList) {
        this.taskList = taskList;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskTitle, taskDesc, taskDateTime;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.taskTitle);
            taskDesc = itemView.findViewById(R.id.taskDesc);
            taskDateTime = itemView.findViewById(R.id.taskDateTime);
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        String[] task = taskList.get(position);
        holder.taskTitle.setText(task[0]);
        holder.taskDesc.setText(task[1]);
        holder.taskDateTime.setText(task[2]);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
