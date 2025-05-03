package com.example.assignment3sqliteandsharedpreferences;

public class Event {
    public String title;
    public String description;
    public String date; // Format: "yyyy-MM-dd"
    public String time; // Format: "hh:mm AM/PM"
    public int color;   // e.g., 0xFF2196F3 for blue

    public Event(String title, String description, String date, String time, int color) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.color = color;
    }
}
