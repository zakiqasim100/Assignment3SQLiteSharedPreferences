package com.example.assignment3sqliteandsharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("UserPrefs", 0);
        boolean isDark = prefs.getBoolean("isDark", false);
        AppCompatDelegate.setDefaultNightMode(
                isDark ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Schedule");
                    tab.setIcon(R.drawable.ic_schedule);
                    break;
                case 1:
                    tab.setText("Past");
                    tab.setIcon(R.drawable.ic_past);
                    break;
                case 2:
                    tab.setText("Notification");
                    tab.setIcon(R.drawable.ic_notification);
                    break;
                case 3:
                    tab.setText("Profile");
                    tab.setIcon(R.drawable.ic_profile);
                    break;
            }
        }).attach();

    }
}
