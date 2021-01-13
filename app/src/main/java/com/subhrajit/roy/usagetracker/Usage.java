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

    private String comment;

    private String type;

    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    public Usage(){}

    public Usage(int count, Date date, String comment, String type) {
        this.count = count;
        this.date = date;
        this.comment = comment;
        this.type = type;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateAsString(){
        return DATE_FORMAT.format(this.date);
    }
}
