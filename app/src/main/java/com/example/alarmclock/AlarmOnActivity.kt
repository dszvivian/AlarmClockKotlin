package com.example.alarmclock

import android.media.MediaPlayer
import android.media.RingtoneManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alarmclock.databinding.ActivityAlarmonBinding
import com.example.alarmclock.databinding.ActivityMainBinding

class AlarmOnActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlarmonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAlarmonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val mp = MediaPlayer.create(applicationContext, uri)
        mp.start()

        binding.btnStopAlarm.setOnClickListener {

            mp.stop()

        }


    }
}