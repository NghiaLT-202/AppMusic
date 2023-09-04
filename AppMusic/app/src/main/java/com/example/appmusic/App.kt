package com.example.appmusic

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.appmusic.data.model.DataMusic
import com.example.appmusic.utils.MyDebugTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    lateinit var musicCurrent: DataMusic
    var listDataMusic: MutableList<DataMusic> = mutableListOf()


    init {
        instance = this
        context= this
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
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: App
        @SuppressLint("StaticFieldLeak")
        lateinit var context :Context
    }
}