package com.example.assignment3sqliteandsharedpreferences;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SampleEventProvider {

    public static List<Event> getSampleEvents() {
        List<Event> events = new ArrayList<>();

        events.add(new Event(
                "Morning Jog",
                "Run in the park",
                LocalDate.now().toString(),
                "06:30 AM",
                0xFF2196F3  // Blue
        ));

        events.add(new Event(
                "Project Meeting",
                "Zoom call with team",
                LocalDate.now().toString(),
                "10:00 AM",
                0xFF4CAF50  // Green
        ));

        events.add(new Event(
                "Design Review",
                "Feedback on wireframes",
                LocalDate.now().plusDays(1).toString(),
                "03:00 PM",
                0xFF9C27B0  // Purple
        ));

        return events;
    }
}
