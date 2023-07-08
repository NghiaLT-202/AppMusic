package com.example.appmusic

import android.app.Application

@HiltAndroidApp
class App : Application() {
    var listMusic: List<Music> = ArrayList<Music>()
    var musicCurrent: Music? = null

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        initLog()
    }

    private fun initLog() {
        if (BuildConfig.DEBUG) {
            Timber.plant(MyDebugTree())
        }
    }

    companion object {
        var isLoop = false
        var instance: App
            private set
    }
}