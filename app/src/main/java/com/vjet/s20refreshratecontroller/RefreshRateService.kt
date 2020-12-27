package com.vjet.s20refreshratecontroller

import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

@RequiresApi(Build.VERSION_CODES.N)
class RefreshRateService : TileService() {

    private fun updateTile() {
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_SECURE_SETTINGS)
        if (permission == PackageManager.PERMISSION_GRANTED) {
            val enabled = Settings.Secure.getString(contentResolver, REFRESH_RATE_MODE) == "0"
            qsTile.state = if (enabled) Tile.STATE_INACTIVE else Tile.STATE_ACTIVE
            qsTile.label = if (enabled) "Standard" else "High"
            qsTile.updateTile()
        }
    }

    override fun onTileAdded() {
        updateTile()
    }

    override fun onStartListening() {
        updateTile()
    }

    override fun onClick() {
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_SECURE_SETTINGS)
        if (permission == PackageManager.PERMISSION_GRANTED) {
            val mode = if (qsTile.state == Tile.STATE_ACTIVE) "2" else "0"
            Settings.Secure.putString(contentResolver, REFRESH_RATE_MODE, mode)
            qsTile.state = if (mode == "0") Tile.STATE_INACTIVE else Tile.STATE_ACTIVE
            qsTile.label = if (mode == "0") "Standard" else "High"
            qsTile.updateTile()
        }
    }
}