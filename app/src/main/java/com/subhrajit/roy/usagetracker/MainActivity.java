package com.subhrajit.roy.usagetracker;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG  = MainActivity.class.getSimpleName();
    private static final String APP_TAG  = "app";
    public static final String SUCCESS = "Done";
    public static final String ERROR = "error";
    private TextView countView;
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
            setUsageDetail(all);
        });


        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUsageDetail(List<Usage> usageList) {

        Map<String, Long> collect = usageList.stream().collect(Collectors.groupingBy(usage -> usage.getDateAsString(), Collectors.counting()));
        StringBuilder textContentBuilder = new StringBuilder();

        Stream<String> sortedKeySet = collect.keySet().stream().sorted();

        sortedKeySet.forEach(k -> {
            Long count = collect.get(k);
            String content = String.format("%d Pack(s) bought on %s",count,k);
            textContentBuilder.append("\n").append(content);
        });

        this.runOnUiThread(() ->{
            if(usageTextView != null){
                usageTextView.setText(textContentBuilder.toString());
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
//        Usage usage = new Usage(1, new Date(),"A pack of 20 cigarettes","");
//        Log.i(APP_TAG,"calling save usage ");
//        saveUsage(usage);
        Intent intent = new Intent(this, AddUsageActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void reset(View view){
        ConfirmationDialogHandler handler = (dialog,which) -> {
            Log.d(APP_TAG,String.format("User selected %d",which));
            if(Math.abs(which) == 2){
                deleteDataOnConfirmation();
            }
        };
        new ConfirmationDialogFragment(handler).show(
                getSupportFragmentManager(),ConfirmationDialogFragment.TAG);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void deleteDataOnConfirmation() {
        CompletableFuture.runAsync(() -> {
            UsageDAO usageDAO = getUsageDAO();
            usageDAO.deleteAll();
            Integer count = usageDAO.getCount();
            setUsageOnView(count);
            setUsageDetail(new ArrayList<>());
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