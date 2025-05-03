package com.example.assignment3sqliteandsharedpreferences;

import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ScheduleFragment extends Fragment {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private List<Task> upcomingTasks;
    private DatabaseHelper dbHelper;
    private TextView emptyText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        recyclerView = view.findViewById(R.id.scheduleRecyclerView);
        emptyText = view.findViewById(R.id.emptyText);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dbHelper = new DatabaseHelper(getContext());

        FloatingActionButton fab = view.findViewById(R.id.fabAddSchedule);
        fab.setOnClickListener(v -> {
            AddEventBottomSheet sheet = new AddEventBottomSheet();
            sheet.show(getChildFragmentManager(), "addTask");
        });

        loadUpcomingTasks();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUpcomingTasks(); // Refresh list when returning
    }

    private void loadUpcomingTasks() {
        upcomingTasks = new ArrayList<>();
        List<Task> allTasks = dbHelper.getAllTasks();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        Date now = new Date();

        for (Task task : allTasks) {
            try {
                Date taskDate = sdf.parse(task.getDateTime());

                if (taskDate != null && taskDate.after(now)) {
                    upcomingTasks.add(task);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        adapter = new TaskAdapter(upcomingTasks);
        recyclerView.setAdapter(adapter);

        emptyText.setVisibility(upcomingTasks.isEmpty() ? View.VISIBLE : View.GONE);
    }
}
