package com.example.appmusic.common

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.WindowManager
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
fun makeStatusBarLight(activity: Activity) {
    activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
    )
}
