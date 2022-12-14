package com.example.alarmclock

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.alarmclock.databinding.ActivityMainBinding
import java.sql.Time
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var alarmManagers = arrayListOf<AlarmManager>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var reqCode = 0


        binding.btnSavetime.setOnClickListener {

            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

            alarmManagers.add(alarmManager)




            val sec = binding.etSetTime


            var cal = Calendar.getInstance()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cal.set(
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DATE),
                    sec.hour,
                    sec.minute,
                    0
                )

            }


            val i = Intent(applicationContext, MyBroadcast::class.java)
            var pi = PendingIntent.getBroadcast(applicationContext, reqCode++, i, 0)
            alarmManagers[alarmManagers.size-1] = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pi)
            Toast.makeText(this, "Alarm Is set at ${cal.time.hours} : ${cal.time.minutes}", Toast.LENGTH_SHORT).show()


        }


    }
}