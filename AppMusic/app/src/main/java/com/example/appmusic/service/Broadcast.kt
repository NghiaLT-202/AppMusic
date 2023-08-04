package com.example.appmusic.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.appmusic.App
import com.example.appmusic.R
import com.example.appmusic.common.Constant
import com.example.appmusic.common.MessageEvent
import com.example.appmusic.data.model.Music
import org.greenrobot.eventbus.EventBus

class Broadcast : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == null) return
        when (intent.action) {
            Constant.PLAY_SONG -> {
                EventBus.getDefault().post(MessageEvent(context.getString(R.string.namesong)))
                startService(Constant.STOP_MEDIA_SERVICE)
            }

            Constant.NEXT_SONG -> {
                var currentPos = getPosCurrentMusic(App.instance.musicCurrent)
                currentPos++
                if (currentPos > App.instance.listMusic.size - 1) {
                    currentPos = 0
                }
                App.instance.musicCurrent = App.instance.listMusic[currentPos]
                startService(Constant.CHANGE_MUSIC_SERVICE)
            }

            Constant.BACK_SONG -> {
                var currentPosback = getPosCurrentMusic(App.instance.musicCurrent)
                currentPosback--
                if (currentPosback < 0) {
                    currentPosback = App.instance.listMusic.size - 1
                }
                App.instance.musicCurrent = App.instance.listMusic[currentPosback]
                startService(Constant.CHANGE_MUSIC_SERVICE)
            }
        }
    }

    private fun startService(action: String) {
        val intentBroadCast = Intent(App.instance, MusicService::class.java)
        intentBroadCast.action = action
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            App.instance.startForegroundService(intentBroadCast)
        } else {
            App.instance.startService(intentBroadCast)
        }
    }

    private fun getPosCurrentMusic(music: Music): Int {
        val musicFile=music.musicFile
        for (i in App.instance.listMusic.indices) {
            if (App.instance.listMusic[i].musicFile == musicFile) {
                return i
            }
        }
        return -1
    }
}