package com.subhrajit.roy.usagetracker;

import android.icu.util.Calendar;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import java.util.Date;

import static com.subhrajit.roy.usagetracker.DateUtil.FORMAT;

public class AddUsageActivity extends AppCompatActivity {

    private TextView usageDateView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_usage);
        usageDateView = findViewById(R.id.usage_date);
        usageDateView.setText(FORMAT.format(new Date()));
    }

    public void onDateChange(View view){
        CalendarViewFragment.newInstance(new Date())
                .show(getSupportFragmentManager(),CalendarViewFragment.TAG);
    }
}