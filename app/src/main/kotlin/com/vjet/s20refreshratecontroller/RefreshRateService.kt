package com.vjet.s20refreshratecontroller

import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.N)
class RefreshRateService : TileService() {

    private fun updateTile() {
        val rate = RefreshRateUtils.getRefreshRate(contentResolver)
        qsTile.state = if (rate == RefreshRateUtils.MAX) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        qsTile.label = "$rate Hz"
        qsTile.updateTile()
    }

    override fun onTileAdded() = updateTile()

    override fun onStartListening() = updateTile()

    override fun onClick() {
        super.onClick()
        val max = qsTile.state == Tile.STATE_ACTIVE
        val rate = if (max) RefreshRateUtils.HIGH else RefreshRateUtils.MAX
        RefreshRateUtils.setRefreshRate(contentResolver, rate)
        qsTile.state = if (max) Tile.STATE_INACTIVE else Tile.STATE_ACTIVE
        qsTile.label = "$rate Hz"
        qsTile.updateTile()
    }
}