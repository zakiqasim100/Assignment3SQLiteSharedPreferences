package com.example.assignment3sqliteandsharedpreferences;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kizitonwose.calendarview.CalendarView;
import com.kizitonwose.calendarview.model.CalendarDay;
import com.kizitonwose.calendarview.model.DayOwner;

import java.time.YearMonth;

import com.kizitonwose.calendarview.ui.DayBinder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CalendarFragment extends Fragment {

    private CalendarView calendarView;
    private RecyclerView eventRecyclerView;
    private TextView selectedDateText;
    private EventAdapter adapter;
    private List<Event> allEvents;
    private List<Event> selectedDateEvents;
    private LocalDate selectedDate;
    private DatabaseHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendarView = view.findViewById(R.id.calendarView);
        eventRecyclerView = view.findViewById(R.id.eventRecyclerView);
        selectedDateText = view.findViewById(R.id.selectedDateText);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db = new DatabaseHelper(getContext());
        selectedDate = LocalDate.now();

        setupCalendar();

        FloatingActionButton fab = view.findViewById(R.id.fabAddEvent);
        fab.setOnClickListener(v -> {
            AddEventBottomSheet sheet = new AddEventBottomSheet();
            sheet.show(getChildFragmentManager(), "addEvent");
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadEventsFromDatabase();
        updateEventList(selectedDate);
    }

    private void loadEventsFromDatabase() {
        allEvents = new ArrayList<>();

        List<Task> tasks = db.getAllTasks();
        for (Task task : tasks) {
            allEvents.add(new Event(
                    task.getTitle(),
                    task.getDescription(),
                    task.getDateTime().split(" ")[0], // Extract only date
                    task.getDateTime().split(" ")[1], // Extract only time
                    0xFF4CAF50 // Default green color for now
            ));
        }
    }

    private void setupCalendar() {
        calendarView.setDayBinder(new DayBinder<DayViewContainer>() {
            @NonNull
            @Override
            public DayViewContainer create(@NonNull View view) {
                return new DayViewContainer(view);
            }

            @Override
            public void bind(@NonNull DayViewContainer container, CalendarDay day) {
                TextView dayText = container.textView;
                dayText.setText(String.valueOf(day.getDate().getDayOfMonth()));

                if (day.getOwner() == DayOwner.THIS_MONTH) {
                    dayText.setAlpha(1f);
                    dayText.setBackgroundResource(day.getDate().equals(selectedDate)
                            ? R.drawable.selected_day_bg : 0);

                    dayText.setOnClickListener(v -> {
                        selectedDate = day.getDate();
                        calendarView.notifyCalendarChanged();
                        updateEventList(selectedDate);
                    });
                } else {
                    dayText.setAlpha(0.3f);
                    dayText.setOnClickListener(null);
                }
            }
        });

        calendarView.setup(
                YearMonth.now(),
                YearMonth.now().plusMonths(12),
                DayOfWeek.MONDAY
        );
        calendarView.scrollToMonth(YearMonth.now());
    }

    private void updateEventList(LocalDate date) {
        if (allEvents == null) return;

        selectedDateEvents = new ArrayList<>();
        String clickedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        for (Event e : allEvents) {
            if (e.date.equals(clickedDate)) {
                selectedDateEvents.add(e);
            }
        }

        selectedDateText.setText("Events on " + clickedDate);
        adapter = new EventAdapter(selectedDateEvents);
        eventRecyclerView.setAdapter(adapter);
    }
}
