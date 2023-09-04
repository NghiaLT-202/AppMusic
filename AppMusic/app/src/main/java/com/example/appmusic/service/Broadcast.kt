package com.example.appmusic.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.appmusic.App
import com.example.appmusic.R
import com.example.appmusic.common.Constant
import com.example.appmusic.common.MessageEvent
import com.example.appmusic.common.startService
import com.example.appmusic.data.model.DataMusic
import org.greenrobot.eventbus.EventBus
import java.security.cert.Extension

class Broadcast : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == null) return

        val currentPos = getPosCurrentMusic(App.instance.musicCurrent)
        val totalMusic = App.instance.listDataMusic.size

        when (intent.action) {
            Constant.PLAY_SONG -> {
                EventBus.getDefault().post(MessageEvent(context.getString(R.string.namesong)))
                startService(Constant.STOP_MEDIA_SERVICE)
            }

            Constant.NEXT_SONG -> {
                val nextPos = (currentPos + 1) % totalMusic
                App.instance.musicCurrent = App.instance.listDataMusic[nextPos]
                startService(Constant.CHANGE_MUSIC_SERVICE)
            }

            Constant.BACK_SONG -> {
                val backPos = if (currentPos > 0) currentPos - 1 else totalMusic - 1
                App.instance.musicCurrent = App.instance.listDataMusic[backPos]
                startService(Constant.CHANGE_MUSIC_SERVICE)
            }
        }
    }


    private fun getPosCurrentMusic(dataMusic: DataMusic): Int {
        val musicFile=dataMusic.musicFile
        for (i in App.instance.listDataMusic.indices) {
            if (App.instance.listDataMusic[i].musicFile == musicFile) {
                return i
            }
        }
        return -1
    }
}