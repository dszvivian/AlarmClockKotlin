package com.example.alarmclock

import android.app.Activity
import android.widget.Toast

object Extension {

    fun Activity.toast(text:String){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show()
    }

}