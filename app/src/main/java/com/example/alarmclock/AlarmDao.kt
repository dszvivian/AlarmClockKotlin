package com.example.alarmclock

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AlarmDao {

    @Query("select * from Alarm")
    fun getAllAlarms() : LiveData<List<Alarm>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlarm(newAlarm: Alarm)

    @Delete
    suspend fun deleteAlarm(alarm:Alarm)

}