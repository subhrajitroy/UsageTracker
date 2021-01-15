package com.subhrajit.roy.usagetracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG  = MainActivity.class.getSimpleName();
    private static final String APP_TAG  = "app";

    private TextView countView;
    private ListView usageListView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countView = findViewById(R.id.show_count);
        usageListView = findViewById(R.id.usage_text);
        setUsageForCurrentMonth();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG,"In start");
        setUsageForCurrentMonth();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG,"In resume");
        setUsageForCurrentMonth();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUsageForCurrentMonth() {

        CompletableFuture.supplyAsync(() -> {
            UsageDAO usageDAO = getUsageDAO();
            return usageDAO.getAll();
        }).thenApply(all -> {
                Integer usage = getUsageForCurrentMonth(all);
                setUsageOnView(usage);
                return all;
        }).thenAccept(this::setUsageDetail);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUsageDetail(List<Usage> usageList) {

        Map<Date, List<Usage>> collect = usageList.stream().collect(Collectors.groupingBy(Usage::getDate));

        ArrayList<String> displayList = new ArrayList<>();

        final  List<Date> usageSortedByDate = collect.keySet().stream().sorted().collect(Collectors.toList());

        for(int i =0; i< usageSortedByDate.size();i++){
            Date date = usageSortedByDate.get(i);

            displayList.add(DateUtil.FORMAT.format(date));
            List<Usage> usagesOnGivenDate = collect.get(date);

            HashMap<String, Integer> usageCountMap = new HashMap<>();

            usagesOnGivenDate.forEach( u -> {
                String itemUsed = u.getItemUsed();
                if(usageCountMap.containsKey(itemUsed.toLowerCase())){
                    Integer count = usageCountMap.get(itemUsed);
                    usageCountMap.put(itemUsed,count + u.getCount());
                }else{
                    usageCountMap.put(itemUsed.toLowerCase(),u.getCount());
                }
            });

            usageCountMap.keySet().forEach(k -> {
                displayList.add(format("%d %s",usageCountMap.get(k),k));
            });

        }

        this.runOnUiThread(() ->{
            if(usageListView != null){
                ListAdapter listAdapter = new ArrayAdapter<>(getApplicationContext()
                        , R.layout.activity_list_view, displayList);
                usageListView.setAdapter(listAdapter);
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
            Log.d(APP_TAG, format("User selected %d",which));
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


    private UsageDAO getUsageDAO() {
        AppDatabase db = AppDatabase.instance(getApplicationContext());
        return db.getDAO();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppDatabase.instance(getApplicationContext()).close();
    }
}