package com.example.assignment3sqliteandsharedpreferences;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.*;

public class PastFragment extends Fragment {

    private RecyclerView pastRecyclerView;
    private TaskAdapter taskAdapter;
    private List<String[]> taskList;
    private DatabaseHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past, container, false);

        db = new DatabaseHelper(getContext());
        pastRecyclerView = view.findViewById(R.id.pastTaskRecyclerView);
        pastRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        taskList = new ArrayList<>();

        loadPastTasks();
        taskAdapter = new TaskAdapter(taskList);
        pastRecyclerView.setAdapter(taskAdapter);

        return view;
    }

    private void loadPastTasks() {
        taskList.clear();
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
        Cursor cursor = db.getPastTasks(now);
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
