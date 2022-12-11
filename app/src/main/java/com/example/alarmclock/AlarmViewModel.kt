package com.example.alarmclock

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmViewModel(application: Application): AndroidViewModel(application) {

    val allAlarm: LiveData<List<Alarm>>
    val dao:AlarmDao

    init {
        dao = AlarmDatabase.getDatabase(application).alarmDao()
        allAlarm = dao.getAllAlarms()
    }

    fun insert(alarm:Alarm) = viewModelScope.launch(Dispatchers.IO){
        dao.insertAlarm(alarm)
    }

    fun delete(alarm:Alarm) = viewModelScope.launch(Dispatchers.IO){
        dao.deleteAlarm(alarm)
    }





}