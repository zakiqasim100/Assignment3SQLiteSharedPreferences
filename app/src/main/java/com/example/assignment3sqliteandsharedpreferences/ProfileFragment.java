package com.example.assignment3sqliteandsharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private EditText edtName, edtEmail;
    private Switch switchTheme;
    private Button btnSave;
    private SharedPreferences prefs;

    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_THEME = "isDark";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        edtName = view.findViewById(R.id.edtName);
        edtEmail = view.findViewById(R.id.edtEmail);
        switchTheme = view.findViewById(R.id.switchTheme);
        btnSave = view.findViewById(R.id.btnSaveProfile);

        prefs = requireActivity().getSharedPreferences(PREFS_NAME, 0);

        loadProfile();

        btnSave.setOnClickListener(v -> saveProfile());

        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            AppCompatDelegate.setDefaultNightMode(
                    isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
            prefs.edit().putBoolean(KEY_THEME, isChecked).apply();
        });

        return view;
    }

    private void loadProfile() {
        edtName.setText(prefs.getString(KEY_NAME, ""));
        edtEmail.setText(prefs.getString(KEY_EMAIL, ""));
        switchTheme.setChecked(prefs.getBoolean(KEY_THEME, false));
    }

    private void saveProfile() {
        prefs.edit()
                .putString(KEY_NAME, edtName.getText().toString())
                .putString(KEY_EMAIL, edtEmail.getText().toString())
                .apply();
        Toast.makeText(getContext(), "Profile saved", Toast.LENGTH_SHORT).show();
    }
}
