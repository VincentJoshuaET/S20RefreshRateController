package com.vjet.s20refreshratecontroller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi

class BootReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                context?.startForegroundService(
                    Intent(
                        context,
                        AdaptiveRefreshRateService::class.java
                    )
                )
            }
        }
    }

}