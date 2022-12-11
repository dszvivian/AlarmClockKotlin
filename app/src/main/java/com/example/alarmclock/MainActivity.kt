package com.example.alarmclock

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isEmpty
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.alarmclock.Extension.toast
import com.example.alarmclock.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.sql.Time
import java.util.Calendar

class MainActivity : AppCompatActivity(), AlarmListAdapter.AlarmClickDeleteInterface {

    private lateinit var binding: ActivityMainBinding

    private var alarmManagers = arrayListOf<AlarmManager>()

    private lateinit var viewModal: AlarmViewModel

    private lateinit var adapter: AlarmListAdapter


    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        adapter = AlarmListAdapter(this, this)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
        ).get(AlarmViewModel::class.java)


        viewModal.allAlarm.observe(this, Observer { list ->
            list?.let {
                adapter.updateList(it)
            }
        })


        var reqCode = 110

        binding.btnSavetime.setOnClickListener {

            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

            alarmManagers.add(alarmManager)


            val sec = binding.etSetTime

            if (sec.isEmpty()) {
                this.toast("Please Enter the Time")
            }


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
            alarmManagers[alarmManagers.size - 1] =
                getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pi)
            Toast.makeText(this,
                "Alarm is set at ${cal.time.hours} : ${cal.time.minutes}",
                Toast.LENGTH_SHORT).show()


            try {
                viewModal.insert(Alarm("${cal.time.hours} : ${cal.time.minutes}"))
                this@MainActivity.toast("Added to Db")
            } catch (e: Exception) {
                this.toast(e.localizedMessage!!)
            }

        }


    }

    override fun onDeleteIconClick(alarm: Alarm) {
        viewModal.delete(alarm)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent()
        val pi = PendingIntent.getBroadcast(this,alarm.reqKey,intent,0)
        alarmManager.cancel(pi)

        adapter.notifyDataSetChanged()

    }
}