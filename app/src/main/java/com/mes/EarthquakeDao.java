package com.mes;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.RawQuery;
import android.arch.persistence.room.Update;

import java.util.List;

import retrofit2.http.Query;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao // data access object
public interface EarthquakeDao
{
    @Insert(onConflict = REPLACE)
    void save(Earthquake earthquake);

    @RawQuery("SELECT * FROM earthquake WHERE id = :earthquakeId")
    LiveData<Earthquake> load(String earthquakeId);

    @Insert
    Long insertTask(Earthquake earthquake);

    @Query("SELECT * FROM earthquake")
    LiveData<List<Earthquake>> fetchAllTasks();


    @Query("SELECT * FROM earthquake WHERE id =:earthquakeId")
    LiveData<Earthquake> getTask(int earthquakeId);


    @Update
    void updateTask(Earthquake earthquake);


    @Delete
    void deleteTask(Earthquake earthquake);
}