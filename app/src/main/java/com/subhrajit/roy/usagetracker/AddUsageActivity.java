package com.subhrajit.roy.usagetracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.subhrajit.roy.usagetracker.DateUtil.FORMAT;

public class AddUsageActivity extends AppCompatActivity {

    public static final String SUCCESS = "Done";
    public static final String ERROR = "error";
    private static final String LOG_TAG  = AddUsageActivity.class.getSimpleName();
    private TextView usageDateView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_usage);
        Toolbar myToolbar = findViewById(R.id.add_usage_toolbar);
        setSupportActionBar(myToolbar);
        usageDateView = findViewById(R.id.usage_date);
        usageDateView.setText(FORMAT.format(new Date()));
        Spinner spinner = (Spinner) findViewById(R.id.usage_unit_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.usage_types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void onDateChange(View view){
        DateSelectedHandler dateSelectedHandler = (Date selected) -> {
            usageDateView.setText(FORMAT.format(selected));
        };
        CalendarViewDialogFragment.newInstance(new Date(),dateSelectedHandler)
                .show(getSupportFragmentManager(), CalendarViewDialogFragment.TAG);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onUsageAdd(View view) throws ParseException {
        TextView itemUsed = findViewById(R.id.usage_type);
        TextView usageDate = findViewById(R.id.usage_date);
        TextView unitCount = findViewById(R.id.usage_unit_count);
        Spinner unitType = (Spinner) findViewById(R.id.usage_unit_type);

        Date date = FORMAT.parse(usageDate.getText().toString());
        Object selectedItem = unitType.getSelectedItem();
        Usage usage = new Usage(Integer.parseInt(String.valueOf(unitCount.getText())), date, selectedItem.toString(),
                itemUsed.getText().toString());
        saveUsage(usage);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void saveUsage(Usage usage) {
        UsageDAO usageDAO = getUsageDAO();
        CompletableFuture.supplyAsync(() -> {
            usageDAO.save(usage);
            List<Usage> all = usageDAO.getAll();
            Log.i(LOG_TAG,String.format("saved %d usages", all.size()));
            return SUCCESS;
        }).thenApply((res)->{
            this.runOnUiThread(()-> {
                Toast toast = Toast.makeText(getApplicationContext(),res, Toast.LENGTH_SHORT);
                toast.show();
            });
            return SUCCESS;
        }).thenApply((res) -> {
            GoBackToMain();
            return SUCCESS;
        }).exceptionally(ex -> {
            Log.e(LOG_TAG, ex.getMessage(), ex);
            return ERROR;
        });
    }

    private void GoBackToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private UsageDAO getUsageDAO() {
        AppDatabase db = AppDatabase.instance(getApplicationContext());
        return db.getDAO();
    }

    public void onCancel(View view) {
        GoBackToMain();
    }
}