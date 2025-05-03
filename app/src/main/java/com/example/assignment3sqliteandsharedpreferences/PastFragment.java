package com.example.assignment3sqliteandsharedpreferences;

import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PastFragment extends Fragment {

    private RecyclerView pastRecyclerView;
    private TextView emptyText;
    private TaskAdapter adapter;
    private List<Task> pastTasks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_past, container, false);

        pastRecyclerView = view.findViewById(R.id.pastRecyclerView);
        emptyText = view.findViewById(R.id.emptyPastText);
        pastRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadPastTasks();

        return view;
    }

    private void loadPastTasks() {
        DatabaseHelper db = new DatabaseHelper(getContext());
        List<Task> allTasks = db.getAllTasks();

        pastTasks = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        Date now = new Date();

        for (Task task : allTasks) {
            try {
                Date taskDate = sdf.parse(task.getDateTime());
                if (taskDate != null && taskDate.before(now)) {
                    pastTasks.add(task);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Sort with latest past tasks at the top
        Collections.sort(pastTasks, (a, b) -> {
            try {
                return sdf.parse(b.getDateTime()).compareTo(sdf.parse(a.getDateTime()));
            } catch (ParseException e) {
                return 0;
            }
        });

        if (pastTasks.isEmpty()) {
            emptyText.setVisibility(View.VISIBLE);
            pastRecyclerView.setVisibility(View.GONE);
        } else {
            emptyText.setVisibility(View.GONE);
            pastRecyclerView.setVisibility(View.VISIBLE);
            adapter = new TaskAdapter(pastTasks);
            pastRecyclerView.setAdapter(adapter);
        }
    }
}
