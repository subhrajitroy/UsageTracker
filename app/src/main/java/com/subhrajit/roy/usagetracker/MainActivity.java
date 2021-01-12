package com.subhrajit.roy.usagetracker;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG  = MainActivity.class.getSimpleName();
    private static final String APP_TAG  = "app";
    public static final String SUCCESS = "Done";
    public static final String ERROR = "error";
    private TextView countView;
    private int currentCount = 0;
    private TextView usageTextView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countView = findViewById(R.id.show_count);
        usageTextView = findViewById(R.id.usage_text);
        setUsageForCurrentMonth();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private int setUsageForCurrentMonth() {

        CompletableFuture.supplyAsync(() -> {
            UsageDAO usageDAO = getUsageDAO();
            List<Usage> all = usageDAO.getAll();
            return all;
        }).thenApply(all -> {
                Integer usage = getUsageForCurrentMonth(all);
                setUsageOnView(usage);
                return all;
        }).thenAccept(all -> {
            all.forEach(u -> setUsageDetail(u));
        });


        return 0;
    }

    private void setUsageDetail(Usage usage) {
        this.runOnUiThread(() ->{
            if(usageTextView != null){
                usageTextView.append("\n");
                usageTextView.append(String.format("1 Pack bought on %s",usage.getDate().toString()));
            }
        });
    }

    private void setUsageOnView(Integer usage) {
        this.runOnUiThread(() ->{
            if(countView != null){
                countView.setText(Integer.toString(usage));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private int getUsageForCurrentMonth(List<Usage> all) {
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);

        int sumForCurrentMonth = all.stream().filter(u -> {
            Date usageDate = u.getDate();
            Calendar usageDateCalendar = Calendar.getInstance();
            usageDateCalendar.setTime(usageDate);
            return currentMonth == usageDateCalendar.get(Calendar.MONTH);
        }).reduce(0,(sum,usage) -> sum + usage.getCount(),Integer::sum);
        return sumForCurrentMonth;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void add(View view){
        Usage usage = new Usage(1, new Date(),"A pack of 20 cigarettes","");
        Log.i(APP_TAG,"calling save usage ");
        saveUsage(usage);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void reset(View view){
        CompletableFuture.runAsync(() -> {
            UsageDAO usageDAO = getUsageDAO();
            usageDAO.deleteAll();
            Integer count = usageDAO.getCount();
            setUsageOnView(count);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void saveUsage(Usage usage) {
        UsageDAO usageDAO = getUsageDAO();
        CompletableFuture.supplyAsync(() -> {
            usageDAO.save(usage);
            return SUCCESS;
        }).thenApply((res) -> {
            setUsageForCurrentMonth();
            return SUCCESS;
        }).exceptionally(ex -> {
            Log.e(APP_TAG, ex.getMessage(), ex);
            return ERROR;
        });
    }


    private UsageDAO getUsageDAO() {
        AppDatabase db = AppDatabase.instance(getApplicationContext());
        return db.getDAO();
    }


}