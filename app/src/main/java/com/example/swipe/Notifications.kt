package com.example.swipe

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class Notifications : BroadcastReceiver() {
    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("MyLogMAct", "Notify Received" )

        val i =  Intent(context, ActivityPage2::class.java)
        val index = intent!!.getLongExtra("BtnClickIndex", 0)
        Log.d("MyLogMAct", "Notify Received ${index}")
        i.putExtra("BtnClickIndex", index)
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT)
        val builder = NotificationCompat.Builder(context!!, "pawuk")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Вещи!")
            .setContentText("Вещи сами себя не проверят!")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(index.toInt(), builder.build())



    }



}