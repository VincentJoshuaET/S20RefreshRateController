package com.vjet.s20refreshratecontroller

import android.content.ContentResolver
import android.provider.Settings

object RefreshRateUtils {
    private const val MIN_REFRESH_RATE = "min_refresh_rate"
    private const val PEAK_REFRESH_RATE = "peak_refresh_rate"

    const val HIGH = "96.0"
    const val MAX = "120.0"

    fun isMaxRefreshRate(resolver: ContentResolver) =
        Settings.System.getString(resolver, PEAK_REFRESH_RATE) == MAX

    fun getRefreshRate(resolver: ContentResolver): String? =
        Settings.System.getString(resolver, PEAK_REFRESH_RATE)

    fun setRefreshRate(resolver: ContentResolver, rate: String) {
        Settings.System.putString(resolver, MIN_REFRESH_RATE, rate)
        Settings.System.putString(resolver, PEAK_REFRESH_RATE, rate)
    }
}