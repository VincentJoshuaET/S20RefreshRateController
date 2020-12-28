package com.vjet.s20refreshratecontroller

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import com.vjet.s20refreshratecontroller.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)

        preferences.edit {
            putString(
                PEAK_REFRESH_RATE,
                Settings.System.getString(contentResolver, PEAK_REFRESH_RATE)
            )
        }

        val result = checkSelfPermission(android.Manifest.permission.WRITE_SECURE_SETTINGS)
        if (result == PackageManager.PERMISSION_GRANTED) {
            binding.switchHighRefreshRate.isEnabled = true

            val high = Settings.Secure.getString(contentResolver, REFRESH_RATE_MODE) != "0"
            binding.switchHighRefreshRate.isChecked = high
            binding.switchMaximumRefreshRate.isEnabled = high
            binding.switchAdaptiveRefreshRate.isEnabled = high

            binding.switchHighRefreshRate.setOnCheckedChangeListener { _, checked ->
                binding.switchMaximumRefreshRate.isEnabled = checked
                binding.switchAdaptiveRefreshRate.isEnabled = checked
                Settings.Secure.putString(
                    contentResolver,
                    REFRESH_RATE_MODE,
                    if (checked) "2" else "0"
                )
            }

            val peak = preferences.getString(PEAK_REFRESH_RATE, "120")
            binding.switchMaximumRefreshRate.isChecked = peak == "120"
            binding.switchMaximumRefreshRate.setOnCheckedChangeListener { _, checked ->
                val rate = if (checked) "120" else "96"
                preferences.edit {
                    putString(PEAK_REFRESH_RATE, rate)
                }
                Settings.System.putString(contentResolver, PEAK_REFRESH_RATE, rate)
            }

            val adaptive = preferences.getBoolean(AUTO_REFRESH_RATE, false)
            val intent = Intent(this, AdaptiveRefreshRateService::class.java)
            if (adaptive) {
                startForegroundService(intent)
            } else {
                stopService(intent)
            }

            binding.switchAdaptiveRefreshRate.isChecked = adaptive
            binding.switchAdaptiveRefreshRate.setOnCheckedChangeListener { _, checked ->
                preferences.edit {
                    putBoolean(AUTO_REFRESH_RATE, checked)
                }
                if (checked) {
                    startForegroundService(intent)
                } else {
                    stopService(intent)
                }
            }
        } else {
            Snackbar.make(binding.root, "WRITE_SECURE_SETTINGS permission is not granted", Snackbar.LENGTH_SHORT).show()
        }

    }
}