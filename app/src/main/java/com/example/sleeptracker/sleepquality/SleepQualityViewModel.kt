package com.example.sleeptracker.sleepquality

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sleeptracker.database.SleepDatabaseDao
import com.example.sleeptracker.database.SleepNightEntity
import kotlinx.coroutines.launch

class SleepQualityViewModel(val nightId : Long, val dataSource : SleepDatabaseDao) : ViewModel(){
    init {

    }

    suspend fun update(night : SleepNightEntity){
        dataSource.update(night)
    }

    private val _navigateToSleepTracker = MutableLiveData<Boolean>()
    val navigateToSleepTracker : LiveData<Boolean>
        get() = _navigateToSleepTracker



    fun onRatingSelected(rating : Int){
        viewModelScope.launch {
            val tonight = dataSource.get(nightId) ?: return@launch
            tonight.sleepQuality = rating
            update(tonight)
            _navigateToSleepTracker.value = true

        }
    }


    fun onNavigationCompleted(){
        _navigateToSleepTracker.value = false
    }


}