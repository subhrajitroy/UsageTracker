package com.subhrajit.roy.usagetracker;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Entity
public class Usage {
    @PrimaryKey
    @NonNull
    private UUID id;

    private int count;

    private Date date;

    private String unitType;

    private String itemUsed;

    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    public Usage(){}

    public Usage(int count, Date date, String unitType, String itemUsed) {
        this.count = count;
        this.date = date;
        this.unitType = unitType;
        this.itemUsed = itemUsed;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }


    public void setId(UUID id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getItemUsed() {
        return itemUsed;
    }

    public void setItemUsed(String itemUsed) {
        this.itemUsed = itemUsed;
    }

    public String getDateAsString(){
        return DATE_FORMAT.format(this.date);
    }
}
