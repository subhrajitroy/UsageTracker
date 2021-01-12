package com.subhrajit.roy.usagetracker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UsageDAO {

    @Query("select * from usage")
    List<Usage> getAll();


    @Query("select count(*) from usage")
    Integer getCount();


    @Insert
    void save(Usage usage);

    @Query("delete from usage")
    void deleteAll();
}
