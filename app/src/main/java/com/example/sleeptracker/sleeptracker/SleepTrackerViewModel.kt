package com.example.sleeptracker.sleeptracker

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.sleeptracker.database.SleepDatabaseDao
import com.example.sleeptracker.database.SleepNightEntity
import kotlinx.coroutines.*

class SleepTrackerViewModel(val dataSource : SleepDatabaseDao, application: Application) : AndroidViewModel(application) {

    private val tonight = MutableLiveData<SleepNightEntity>()
    val nights = dataSource.getAllNights()


    private val _navigateToSleepQuality = MutableLiveData<SleepNightEntity>()
    val navigateToSleepQuality : LiveData<SleepNightEntity>
        get() = _navigateToSleepQuality



    init {
        initializeTonight()


    }

    val startButtonVisible = Transformations.map(tonight){
        it == null
    }
    val stopButtonVisible = Transformations.map(tonight){
        it != null
    }

    private fun initializeTonight() {
        viewModelScope.launch {
            tonight.value = getTonightFromDataBase()
        }
    }

    private suspend fun getTonightFromDataBase(): SleepNightEntity? {
        var night = dataSource.getCurrentNight()
        if(night?.endTimeMilli != night?.startTimeMilli){
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

    private suspend fun clearAll(){
        dataSource.clear()
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
            _navigateToSleepQuality.value = oldNight
            Log.i("SleepTrackerViewModel",nights.value?.size.toString())
        }

    }

    fun onClear(){
        viewModelScope.launch {
            clearAll()
            tonight.value = null
        }
    }


    fun onNavigateToSleepQualityComplete(){
        _navigateToSleepQuality.value = null
    }








}