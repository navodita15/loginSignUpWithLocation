package com.example.loginsignupapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class HomePageActivity : AppCompatActivity() {

    private val NOTIFICATION_REMINDER_NIGHT: Int = 2
    private lateinit var pickTimeButton: Button
    private lateinit var bundle: Bundle
    var interval : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_home_page)
        bundle = intent.extras!!

        pickTimeButton = findViewById(R.id.pick_time_button)

        pickTimeButton.setOnClickListener {
            // Get Current Time

            // Get Current Time
            val c: Calendar = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)
            var calendar: Calendar = Calendar.getInstance()

            // Launch Time Picker Dialog
            val timePickerDialog = TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                        timePicker.getHour(), timePicker.getMinute(), 0);
                    if (calendar.timeInMillis - System.currentTimeMillis()  > 0)
                    scheduleNotification(calendar.timeInMillis)
                else
                Toast.makeText(applicationContext,"Selected time is less than the current time",Toast.LENGTH_SHORT).show()},
                hour,
                minute,
                false
            )
            timePickerDialog.show()


        }
    }

    private fun scheduleNotification(timeInMillis: Long) {
        val notifyIntent = Intent(this, NotificationBroadcastReceiver::class.java)
        notifyIntent.putExtras(bundle)
        Log.d("Profile", "scheduleNotification: coming here bundle == ${bundle["firstName"]}")
        Log.d("Profile", "scheduleNotification: system == ${System.currentTimeMillis()}")
        Log.d("Profile", "scheduleNotification: interval == ${timeInMillis}")
        Log.d("Profile", "scheduleNotification: diff == ${interval + System.currentTimeMillis()}")
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            NOTIFICATION_REMINDER_NIGHT,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
        Toast.makeText(this, "Alarm set in ... seconds",Toast.LENGTH_LONG).show();
    }
}