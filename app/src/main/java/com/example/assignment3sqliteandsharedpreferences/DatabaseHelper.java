package com.example.assignment3sqliteandsharedpreferences;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "TaskApp.db";

    // Table: Tasks
    public static final String TASK_TABLE = "Tasks";
    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_DESC = "description";
    public static final String COL_DATETIME = "datetime";
    public static final String COL_STATUS = "status";

    // Table: Notifications
    public static final String NOTIF_TABLE = "Notifications";
    public static final String NOTIF_ID = "id";
    public static final String NOTIF_MSG = "message";
    public static final String NOTIF_TIME = "datetime";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String taskQuery = "CREATE TABLE " + TASK_TABLE + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TITLE + " TEXT, " +
                COL_DESC + " TEXT, " +
                COL_DATETIME + " TEXT, " +
                COL_STATUS + " TEXT)";
        db.execSQL(taskQuery);

        String notifQuery = "CREATE TABLE " + NOTIF_TABLE + " (" +
                NOTIF_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOTIF_MSG + " TEXT, " +
                NOTIF_TIME + " TEXT)";
        db.execSQL(notifQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + NOTIF_TABLE);
        onCreate(db);
    }

    // Add task method
    public boolean insertTask(String title, String desc, String datetime, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE, title);
        cv.put(COL_DESC, desc);
        cv.put(COL_DATETIME, datetime);
        cv.put(COL_STATUS, status);
        long result = db.insert(TASK_TABLE, null, cv);
        return result != -1;
    }

    // Get upcoming tasks
    public Cursor getUpcomingTasks(String currentTime) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TASK_TABLE + " WHERE datetime(datetime) > datetime(?) ORDER BY datetime ASC", new String[]{currentTime});
    }

    // Get past tasks
    public Cursor getPastTasks(String currentTime) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TASK_TABLE + " WHERE datetime(datetime) <= datetime(?) ORDER BY datetime DESC", new String[]{currentTime});
    }
}
