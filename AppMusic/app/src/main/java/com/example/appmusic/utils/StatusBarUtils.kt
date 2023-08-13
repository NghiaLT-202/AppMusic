package com.example.appmusic.utils

import android.app.Activity
import android.view.WindowManager

object StatusBarUtils {
    fun makeStatusBarLight(activity: Activity) {
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        )
    }
}