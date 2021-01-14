package com.subhrajit.roy.usagetracker;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.subhrajit.roy.usagetracker.DateUtil.FORMAT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarViewFragment extends DialogFragment {

    public static final String TAG = "CalendarViewFragmentTag";
    public static final String SELECTED_DATE = "selectedDate";


    public CalendarViewFragment() {
        // Required empty public constructor
    }


    public static CalendarViewFragment newInstance(Date selectedDate) {
        CalendarViewFragment fragment = new CalendarViewFragment();
        Bundle args = new Bundle();

        args.putString(SELECTED_DATE, FORMAT.format(selectedDate));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Date selectedDate = new Date();
        if (getArguments() != null) {
            try {
                selectedDate = FORMAT.parse(getArguments().getString(SELECTED_DATE));
            } catch (ParseException e) {
                throw new RuntimeException("Could not parse date");
            }

        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setView(R.layout.fragment_calendar_view)
                .setNegativeButton(getString(R.string.confirm), (dialog,which) ->{} )
                .setPositiveButton(getString(R.string.cancel), (dialog,which) ->{} )
                .create();
    }
}