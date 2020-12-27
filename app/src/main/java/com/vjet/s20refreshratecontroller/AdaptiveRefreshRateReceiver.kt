package com.vjet.s20refreshratecontroller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings

class AdaptiveRefreshRateReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val resolver = context?.contentResolver ?: return
        val rate = when (intent?.action) {
            Intent.ACTION_SCREEN_ON -> "120"
            Intent.ACTION_SCREEN_OFF -> "48"
            else -> return
        }
        Settings.System.putString(resolver, PEAK_REFRESH_RATE, rate)
    }

}