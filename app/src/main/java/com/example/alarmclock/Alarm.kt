package com.example.alarmclock

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Alarm(
    @ColumnInfo var time: String? = null
){
    @PrimaryKey(autoGenerate = true) var reqKey: Int = 0
}