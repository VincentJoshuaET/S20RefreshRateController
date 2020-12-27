package com.vjet.s20refreshratecontroller

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.preference.PreferenceManager

class AdaptiveRefreshRateService : Service() {

    private var receiver: AdaptiveRefreshRateReceiver? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        val preference = PreferenceManager.getDefaultSharedPreferences(this)
        val adaptive = preference.getBoolean(PEAK_REFRESH_RATE, false)

        if (adaptive) {
            val id = "ADAPTIVE_REFRESH_RATE_SERVICE"
            val name = "Adaptive Refresh Rate Service"
            val channel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW)
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)

            val notification = NotificationCompat.Builder(this, id).setContentTitle(name).build()

            startForeground(1, notification)

            receiver = AdaptiveRefreshRateReceiver()
            val filter = IntentFilter().apply {
                addAction(Intent.ACTION_SCREEN_OFF)
                addAction(Intent.ACTION_SCREEN_ON)
            }
            registerReceiver(receiver, filter)
        }
    }

    override fun onDestroy() {
        receiver?.let(::unregisterReceiver)
        receiver = null
    }

}