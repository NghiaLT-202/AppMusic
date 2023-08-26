package com.example.appmusic.common

import android.content.Intent
import android.os.Build
import com.example.appmusic.App
import com.example.appmusic.service.MusicService


fun startService(action: String) {
    val intentBroadCast = Intent(App.instance, MusicService::class.java)
    intentBroadCast.action = action
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        App.instance.startForegroundService(intentBroadCast)
    } else {
        App.instance.startService(intentBroadCast)
    }
}
