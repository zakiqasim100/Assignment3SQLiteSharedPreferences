package com.example.assignment3sqliteandsharedpreferences;

public class Task {
    private int id;
    private String title;
    private String description;
    private String dateTime; // format: "yyyy-MM-dd HH:mm"
    private int status; // 0 = upcoming, 1 = completed/past

    public Task() {
    }

    public Task(int id, String title, String description, String dateTime, int status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.status = status;
    }

    public Task(String title, String description, String dateTime, int status) {
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.status = status;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
