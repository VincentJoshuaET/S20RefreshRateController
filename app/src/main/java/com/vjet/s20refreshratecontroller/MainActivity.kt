package com.vjet.s20refreshratecontroller

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.vjet.s20refreshratecontroller.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result = checkSelfPermission(android.Manifest.permission.WRITE_SECURE_SETTINGS)
        if (result == PackageManager.PERMISSION_GRANTED) {
            binding.switchHighRefreshRate.isEnabled = true

            val high = Settings.Secure.getString(contentResolver, REFRESH_RATE_MODE) != "0"
            binding.switchHighRefreshRate.isChecked = high
            binding.switchMaximumRefreshRate.isEnabled = high

            binding.switchHighRefreshRate.setOnCheckedChangeListener { _, checked ->
                binding.switchMaximumRefreshRate.isEnabled = checked
                Settings.Secure.putString(contentResolver, REFRESH_RATE_MODE, if (checked) "2" else "0")
            }

            val peak = Settings.System.getString(contentResolver, PEAK_REFRESH_RATE)
            binding.switchMaximumRefreshRate.isChecked = peak == "120"
            binding.switchMaximumRefreshRate.setOnCheckedChangeListener { _, checked ->
                val rate = if (checked) "120" else "96"
                Settings.System.putString(contentResolver, PEAK_REFRESH_RATE, rate)
            }
        } else {
            Snackbar.make(binding.root, "WRITE_SECURE_SETTINGS permission is not granted", Snackbar.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val PEAK_REFRESH_RATE = "peak_refresh_rate"
        private const val REFRESH_RATE_MODE = "refresh_rate_mode"
    }
}