package com.example.assignment3sqliteandsharedpreferences;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final List<Task> taskList;

    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false); // Reusing item_event.xml layout
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.titleText.setText(task.getTitle());
        holder.noteText.setText(task.getDescription());
        holder.timeText.setText(task.getDateTime());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, noteText, timeText;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.eventTitle);
            noteText = itemView.findViewById(R.id.eventNote);
            timeText = itemView.findViewById(R.id.eventTime);
        }
    }
}
