package com.example.sleeptracker.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.sleeptracker.database.SleepDatabaseDao
import com.example.sleeptracker.database.SleepNightEntity
import kotlinx.coroutines.*

class SleepTrackerViewModel(val dataSource : SleepDatabaseDao, application: Application) : AndroidViewModel(application) {

    private val tonight = MutableLiveData<SleepNightEntity>()
    val nights = dataSource.getAllNights()

    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        viewModelScope.launch {
            tonight.value = getTonightFromDataBase()
        }
    }

    private suspend fun getTonightFromDataBase(): SleepNightEntity? {
        var night = dataSource.getCurrentNight()
        if(night?.startTimeMilli != night?.endTimeMilli){
            night = null
        }
        return night
    }

    private suspend fun insert(night : SleepNightEntity){
        dataSource.insert(night)
    }
    private suspend fun update(night: SleepNightEntity){
        dataSource.update(night)
    }


    fun onSleepTracking(){
        viewModelScope.launch {
            val newNight = SleepNightEntity()
            insert((newNight))
            tonight.value = getTonightFromDataBase()
        }
    }

    fun onStopTracking(){
        viewModelScope.launch {
            val oldNight = tonight.value?:return@launch

            oldNight.endTimeMilli = System.currentTimeMillis()
            update(oldNight)

        }
    }








}