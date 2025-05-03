package com.example.assignment3sqliteandsharedpreferences;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "task_manager.db";
    private static final int DATABASE_VERSION = 1;

    // Task Table
    private static final String TABLE_TASKS = "tasks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESC = "description";
    private static final String COLUMN_DATETIME = "datetime";
    private static final String COLUMN_STATUS = "status"; // 0 = upcoming, 1 = past

    // Notification Table
    private static final String TABLE_NOTIFICATIONS = "notifications";
    private static final String COLUMN_MESSAGE = "message";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTasksTable = "CREATE TABLE " + TABLE_TASKS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_DESC + " TEXT," +
                COLUMN_DATETIME + " TEXT," +
                COLUMN_STATUS + " INTEGER)";
        db.execSQL(createTasksTable);

        String createNotificationsTable = "CREATE TABLE " + TABLE_NOTIFICATIONS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_MESSAGE + " TEXT," +
                COLUMN_DATETIME + " TEXT)";
        db.execSQL(createNotificationsTable);
    }

    // Handle database upgrades
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
        onCreate(db);
    }

    // Insert task
    public void insertTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, task.getTitle());
        values.put(COLUMN_DESC, task.getDescription());
        values.put(COLUMN_DATETIME, task.getDateTime());
        values.put(COLUMN_STATUS, task.getStatus());
        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    // Fetch upcoming tasks
    public List<Task> getUpcomingTasks() {
        return getTasksByStatus(0);
    }

    // Fetch past tasks
    public List<Task> getPastTasks() {
        return getTasksByStatus(1);
    }

    private List<Task> getTasksByStatus(int status) {
        List<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_TASKS +
                " WHERE " + COLUMN_STATUS + " = ?" +
                " ORDER BY " + COLUMN_DATETIME + (status == 1 ? " DESC" : " ASC");

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(status)});

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(0));
                task.setTitle(cursor.getString(1));
                task.setDescription(cursor.getString(2));
                task.setDateTime(cursor.getString(3));
                task.setStatus(cursor.getInt(4));
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return taskList;
    }

    // Insert dummy notification
    public void insertNotification(String message, String datetime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MESSAGE, message);
        values.put(COLUMN_DATETIME, datetime);
        db.insert(TABLE_NOTIFICATIONS, null, values);
        db.close();
    }

    // Get all notifications
    public List<String> getAllNotifications() {
        List<String> notifications = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_MESSAGE + " FROM " + TABLE_NOTIFICATIONS, null);
        if (cursor.moveToFirst()) {
            do {
                notifications.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notifications;
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tasks", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String dateTime = cursor.getString(cursor.getColumnIndexOrThrow("datetime"));
                int status = cursor.getInt(cursor.getColumnIndexOrThrow("status"));

                Task task = new Task(id, title, description, dateTime, status);
                taskList.add(task);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return taskList;
    }

}
