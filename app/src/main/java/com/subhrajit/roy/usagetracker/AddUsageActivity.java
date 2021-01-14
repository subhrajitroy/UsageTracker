package com.subhrajit.roy.usagetracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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
        DateSelectedHandler dateSelectedHandler = (Date selected) -> {
            usageDateView.setText(FORMAT.format(selected));
        };
        CalendarViewDialogFragment.newInstance(new Date(),dateSelectedHandler)
                .show(getSupportFragmentManager(), CalendarViewDialogFragment.TAG);
    }


}