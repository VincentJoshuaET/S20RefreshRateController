package com.vjet.s20refreshratecontroller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.vjet.s20refreshratecontroller.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.switchRefreshRate.isChecked = RefreshRateUtils.isMaxRefreshRate(contentResolver)
        binding.switchRefreshRate.setOnCheckedChangeListener { _, checked ->
            val rate = if (checked) RefreshRateUtils.MAX else RefreshRateUtils.HIGH
            RefreshRateUtils.setRefreshRate(contentResolver, rate)
            Snackbar.make(binding.root, "Refresh rate set to $rate Hz", Snackbar.LENGTH_SHORT).show()
        }
    }
}