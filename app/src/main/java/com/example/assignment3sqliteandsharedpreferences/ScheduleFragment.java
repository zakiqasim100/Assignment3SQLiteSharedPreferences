package com.example.assignment3sqliteandsharedpreferences;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.*;

public class ScheduleFragment extends Fragment {

    private RecyclerView taskRecyclerView;
    private TaskAdapter taskAdapter;
    private List<String[]> taskList;
    private DatabaseHelper db;

    private Button btnAddTask;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        db = new DatabaseHelper(getContext());
        btnAddTask = view.findViewById(R.id.btnAddTask);
        taskRecyclerView = view.findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        taskList = new ArrayList<>();

        loadUpcomingTasks();

        if (taskList.isEmpty()) {
            Toast.makeText(getContext(), "No upcoming tasks found", Toast.LENGTH_SHORT).show();
        }

        taskAdapter = new TaskAdapter(taskList);
        taskRecyclerView.setAdapter(taskAdapter);

        btnAddTask.setOnClickListener(v -> showAddTaskDialog());

        return view;
    }

    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_task, null);
        builder.setView(dialogView);

        EditText edtTitle = dialogView.findViewById(R.id.edtTitle);
        EditText edtDesc = dialogView.findViewById(R.id.edtDesc);
        Button btnPickDate = dialogView.findViewById(R.id.btnPickDate);
        Button btnSave = dialogView.findViewById(R.id.btnSaveTask);

        final String[] selectedDateTime = {""};

        btnPickDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    (view, year, month, dayOfMonth) -> {
                        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                                (view1, hour, minute) -> {
                                    calendar.set(year, month, dayOfMonth, hour, minute);
                                    selectedDateTime[0] = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(calendar.getTime());
                                    btnPickDate.setText(selectedDateTime[0]);
                                }, 12, 0, true);
                        timePickerDialog.show();
                    },
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        AlertDialog dialog = builder.create();

        btnSave.setOnClickListener(v -> {
            String title = edtTitle.getText().toString();
            String desc = edtDesc.getText().toString();
            String datetime = selectedDateTime[0];

            if (title.isEmpty() || desc.isEmpty() || datetime.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = db.insertTask(title, desc, datetime, "pending");
            if (inserted) {
                Toast.makeText(getContext(), "Task added", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                loadUpcomingTasks();
                taskAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), "Error saving task", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    private void loadUpcomingTasks() {
        taskList.clear();
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
        Cursor cursor = db.getUpcomingTasks(now);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String desc = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String datetime = cursor.getString(cursor.getColumnIndexOrThrow("datetime"));
                taskList.add(new String[]{title, desc, datetime});
            } while (cursor.moveToNext());
            cursor.close();
        }
    }
}
