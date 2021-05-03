package com.example.sleeptracker.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SleepDatabaseDao {

    @Insert
    fun insert(night : SleepNightEntity)

    @Update
    fun update(night : SleepNightEntity)

    @Delete
    fun delete(night: SleepNightEntity)

    @Query("SELECT * FROM daily_sleep_quality_table where nightId = :key")
    fun get(key : Long) : SleepNightEntity?

    @Query("SELECT * FROM daily_sleep_quality_table where nightId = :key")
    fun getNightwithId(key : Long) : LiveData<SleepNightEntity>

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC")
    fun getAllNights() : LiveData<List<SleepNightEntity>>

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
    fun getCurrentNight() : SleepNightEntity?
}