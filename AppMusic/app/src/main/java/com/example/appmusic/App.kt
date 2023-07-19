package com.example.appmusic

import android.app.Application
import com.example.appmusic.data.model.Music
import com.example.appmusic.utils.MyDebugTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
     lateinit var musicCurrent: Music
     var listMusic: MutableList<Music> = mutableListOf()


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
        lateinit var instance: App
    }
}