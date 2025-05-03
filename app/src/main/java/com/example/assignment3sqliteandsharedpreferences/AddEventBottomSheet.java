package com.example.assignment3sqliteandsharedpreferences;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEventBottomSheet extends BottomSheetDialogFragment {

    private EditText titleInput, descriptionInput;
    private TextView dateTimeDisplay;
    private Button saveButton;

    private Calendar selectedDateTime;

    public AddEventBottomSheet() {
        selectedDateTime = Calendar.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bottom_sheet_add_event, container, false);

        titleInput = view.findViewById(R.id.inputTitle);
        descriptionInput = view.findViewById(R.id.inputDescription);
        dateTimeDisplay = view.findViewById(R.id.dateTimeDisplay);
        saveButton = view.findViewById(R.id.btnSaveTask);

        dateTimeDisplay.setText(formatDateTime(selectedDateTime));
        dateTimeDisplay.setOnClickListener(v -> showDateTimePicker());

        saveButton.setOnClickListener(v -> saveEvent());

        return view;
    }

    private void showDateTimePicker() {
        Calendar now = Calendar.getInstance();

        new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            selectedDateTime.set(Calendar.YEAR, year);
            selectedDateTime.set(Calendar.MONTH, month);
            selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            new TimePickerDialog(getContext(), (timeView, hourOfDay, minute) -> {
                selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                selectedDateTime.set(Calendar.MINUTE, minute);
                dateTimeDisplay.setText(formatDateTime(selectedDateTime));
            }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false).show();

        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)).show();
    }

    private String formatDateTime(Calendar calendar) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(calendar.getTime());
    }

    private void saveEvent() {
        String title = titleInput.getText().toString().trim();
        String description = descriptionInput.getText().toString().trim();
        String dateTime = formatDateTime(selectedDateTime);

        if (TextUtils.isEmpty(title)) {
            titleInput.setError("Title required");
            return;
        }

        // Convert to SQLite Task model
        Task task = new Task(title, description, dateTime, 0); // 0 = upcoming
        DatabaseHelper db = new DatabaseHelper(getContext());
        db.insertTask(task);

        Toast.makeText(getContext(), "Task saved", Toast.LENGTH_SHORT).show();
        dismiss();
    }
}
