package com.example.appmusic.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.appmusic.App
import com.example.appmusic.common.Constant
import com.example.appmusic.common.MessageEvent
import com.example.appmusic.data.model.Music
import org.greenrobot.eventbus.EventBus

class Broadcast : BroadcastReceiver() {
    private val checked = false
    override fun onReceive(context: Context, intent: Intent) {
        if (intent != null) {
            if (intent.action == null) return
            when (intent.action) {
                Constant.PLAY_SONG -> {
                    EventBus.getDefault().post(MessageEvent("nameSong"))
                    startService(Constant.STOP_MEDIA_SERVICE)
                }

                Constant.NEXT_SONG -> {
                    //                    YoutubeService.ACTIVITY_SERVICE..setImageViewResource(R.id.im_playSong, R.drawable.baseline_pause_24);
                    var currentPos =
                        getPosCurrentMusic(App.Companion.instance.musicCurrent)
                    currentPos++
                    if (currentPos > App.Companion.instance.listMusic.size - 1) {
                        currentPos = 0
                    }
                    App.Companion.instance.musicCurrent =
                        (App.Companion.instance.listMusic[currentPos])
                    startService(Constant.CHANGE_MUSIC_SERVICE)
                }

                Constant.BACK_SONG -> {
                    var currentPosback =
                        getPosCurrentMusic(App.Companion.instance.musicCurrent)
                    currentPosback--
                    if (currentPosback < 0) {
                        currentPosback = App.Companion.instance.listMusic.size - 1
                    }
                    App.Companion.instance.musicCurrent =
                        (App.Companion.instance.listMusic[currentPosback])
                    startService(Constant.CHANGE_MUSIC_SERVICE)
                }
            }
        }
    }

    private fun startService(action: String?) {
        val intentBroadCast = Intent(App.Companion.instance, MusicService::class.java)
        intentBroadCast.action = action
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            App.Companion.instance.startForegroundService(intentBroadCast)
        } else {
            App.Companion.instance.startService(intentBroadCast)
        }
    }

    private fun getPosCurrentMusic(music: Music?): Int {
        for (i in App.Companion.instance.listMusic.indices) {
            if (App.Companion.instance.listMusic[i]?.musicFile == music?.musicFile
            ) {
                return i
            }
        }
        return -1
    }
}