package com.example.sleeptracker.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SleepDatabaseDao {

    @Insert
    suspend fun insert(night : SleepNightEntity)

    @Update
    suspend fun update(night : SleepNightEntity)

    @Delete
    suspend fun delete(night: SleepNightEntity)

    @Query("SELECT * FROM daily_sleep_quality_table where nightId = :key")
    suspend fun get(key : Long) : SleepNightEntity?

    @Query("SELECT * from daily_sleep_quality_table WHERE nightId = :key")
    fun getNightWithId(key: Long): LiveData<SleepNightEntity>

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC")
    fun getAllNights() : LiveData<List<SleepNightEntity>>

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
    suspend fun getCurrentNight() : SleepNightEntity?
}