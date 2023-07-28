package com.example.appmusic.utils

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowManager
import com.example.appmusic.App

object  StatusBarUtils {
     fun  makeStatusBarLight(activity: Activity) {
         activity.window.setFlags(
             WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
             WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
         )
    }
}