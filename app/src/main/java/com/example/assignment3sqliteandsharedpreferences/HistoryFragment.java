package com.example.assignment3sqliteandsharedpreferences;

import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HistoryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getContext());
        textView.setText("History Page");
        textView.setTextSize(24);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
