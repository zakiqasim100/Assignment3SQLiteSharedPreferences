package com.example.assignment3sqliteandsharedpreferences;

import android.database.Cursor;
import android.os.Bundle;
import android.view.*;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class NotificationFragment extends Fragment {

    private RecyclerView notificationRecyclerView;
    private NotificationAdapter adapter;
    private List<String[]> notificationList;
    private DatabaseHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        db = new DatabaseHelper(getContext());
        notificationRecyclerView = view.findViewById(R.id.notificationRecyclerView);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        notificationList = new ArrayList<>();

        simulateNotificationsIfEmpty(); // One-time dummy insertion
        loadNotifications();

        if (notificationList.isEmpty()) {
            Toast.makeText(getContext(), "No notifications found", Toast.LENGTH_SHORT).show();
        }

        adapter = new NotificationAdapter(notificationList);
        notificationRecyclerView.setAdapter(adapter);

        return view;
    }


    private void simulateNotificationsIfEmpty() {
        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT COUNT(*) FROM Notifications", null);
        if (cursor != null && cursor.moveToFirst() && cursor.getInt(0) == 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            db.getWritableDatabase().execSQL("INSERT INTO Notifications (message, datetime) VALUES " +
                    "('Welcome to the app!', '" + sdf.format(new Date()) + "')," +
                    "('Task reminder feature coming soon!', '" + sdf.format(new Date()) + "')," +
                    "('New features available in the profile tab.', '" + sdf.format(new Date()) + "')");
        }
        if (cursor != null) cursor.close();
    }

    private void loadNotifications() {
        notificationList.clear();
        Cursor cursor = db.getReadableDatabase().rawQuery(
                "SELECT message, datetime FROM Notifications ORDER BY datetime DESC", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String message = cursor.getString(0);
                String datetime = cursor.getString(1);
                notificationList.add(new String[]{message, datetime});
            } while (cursor.moveToNext());
            cursor.close();
        }
    }
}
