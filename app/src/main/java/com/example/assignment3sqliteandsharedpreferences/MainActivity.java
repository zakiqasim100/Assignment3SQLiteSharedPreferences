package com.example.assignment3sqliteandsharedpreferences;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.bottomNav);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, new CalendarFragment())
                .commit();

        navView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment;

            int itemId = item.getItemId();

            if (itemId == R.id.nav_history) {
                selectedFragment = new HistoryFragment();
            } else if (itemId == R.id.nav_notifications) {
                selectedFragment = new NotificationsFragment();
            } else if (itemId == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            } else {
                selectedFragment = new CalendarFragment();
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, selectedFragment)
                    .commit();
            return true;
        });

    }
}
