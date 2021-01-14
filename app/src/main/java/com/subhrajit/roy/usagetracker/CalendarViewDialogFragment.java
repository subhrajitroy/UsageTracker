package com.subhrajit.roy.usagetracker;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.View;
import android.widget.CalendarView;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import static com.subhrajit.roy.usagetracker.DateUtil.FORMAT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarViewDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarViewDialogFragment extends DialogFragment {

    public static final String TAG = "CalendarViewFragmentTag";
    public static final String SELECTED_DATE = "selectedDate";
    private static DateSelectedHandler dateSelectedHandler;
    private CalendarView calendarView;
    private Date userSelectedDate ;

    private static String LOG_TAG = CalendarViewDialogFragment.class.getSimpleName();

    public CalendarViewDialogFragment() {
        // Required empty public constructor
    }


    public static CalendarViewDialogFragment newInstance(Date selectedDate, DateSelectedHandler dateSelectedHandler) {
        CalendarViewDialogFragment.dateSelectedHandler = dateSelectedHandler;
        CalendarViewDialogFragment fragment = new CalendarViewDialogFragment();
        Bundle args = new Bundle();

        args.putString(SELECTED_DATE, FORMAT.format(selectedDate));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG,"In create");
        super.onCreate(savedInstanceState);

    }



    @Override
    public void onStart() {
        super.onStart();
        Log.i(this.getClass().getSimpleName(), "In create view");
        Date selectedDate = null;
        if (getArguments() != null) {
            try {
                selectedDate = FORMAT.parse(getArguments().getString(SELECTED_DATE));

            } catch (ParseException e) {
                throw new RuntimeException("Could not parse date");
            }
        }
        calendarView = getDialog().findViewById(R.id.calendar_view);
        selectedDate = selectedDate != null ? selectedDate : new Date();
        calendarView.setDate((selectedDate).getTime());
        CalendarView.OnDateChangeListener onDateChangeListener = (v, year, month, day) -> {
            try {
                Log.d(LOG_TAG,String.format("%d-%d-%d",day,month,year));
                month = month + 1 ;// As month is in the range [0-11]
                userSelectedDate = FORMAT.parse(String.format("%d-%d-%d", day, month, year));
            } catch (ParseException e) {
                throw new RuntimeException(String.format("Could not parse date %d %d %d", day, month, year));
            }
        };
        calendarView.setOnDateChangeListener(onDateChangeListener);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Log.i(LOG_TAG,"In create dialog");
        return new AlertDialog.Builder(requireContext())
                .setView(R.layout.fragment_calendar_view)
                .setNegativeButton(getString(R.string.confirm), (dialog,which) ->{
                    dateSelectedHandler.handle(userSelectedDate);} )
                .setPositiveButton(getString(R.string.cancel), (dialog,which) ->{} )
                .create();
    }
}