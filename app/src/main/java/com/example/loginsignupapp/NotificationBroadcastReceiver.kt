package com.example.loginsignupapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log


class NotificationBroadcastReceiver : BroadcastReceiver(){

    override fun onReceive(context: Context?, intent: Intent?) {

        Log.d("Profile", "onReceive: coming here")
        val intent1 = Intent(context, NotificationService::class.java)
        val bundle : Bundle = intent?.extras!!
        Log.d("Profile", "onReceive: coming here bundle == ${bundle["firstName"]}")
        intent1.putExtras(bundle)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context!!.startForegroundService(intent1)
        } else {
            context!!.startService(intent1)
        }
    }

}