package com.example.appmusic.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.appmusic.App
import com.example.appmusic.R
import com.example.appmusic.common.Constant
import com.example.appmusic.common.Constant.CHANGE_MUSIC_CURRENT
import com.example.appmusic.common.MessageEvent
import com.example.appmusic.ui.main.MainActivity
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

open class MusicService : Service() {
    private var mediaPlayer: MediaPlayer = MediaPlayer()
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        notification()
    }


    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        notification()
        intent.action?.let {
            when (it) {
                Constant.CHANGE_MUSIC_SERVICE, Constant.START_MEDIA_SERVICE -> {
                    startMedia()
                }
                Constant.SEEK_TO_MEDIA_SERVICE -> mediaPlayer.seekTo(
                    intent.getIntExtra(
                        getString(R.string.seek_to), 0
                    )
                )
                Constant.FAVOURITE -> Timber.e("")
                Constant.STOP_MEDIA_SERVICE -> {
                    if (mediaPlayer.isPlaying) {
                        mediaPlayer.pause()
                        EventBus.getDefault().post(MessageEvent(Constant.STOP_MEDIA, false))
                    } else {
                        mediaPlayer.start()
                        EventBus.getDefault().post(MessageEvent(Constant.START_MEDIA, true))
                    }
                }
            }
        }
        return START_NOT_STICKY
    }

    private fun startMedia() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
        mediaPlayer =
            MediaPlayer.create(applicationContext, Uri.parse(App.instance.musicCurrent.uriMusic))
        EventBus.getDefault().post(
            MessageEvent(
                CHANGE_MUSIC_CURRENT,
                mediaPlayer.duration,
                mediaPlayer.currentPosition
            )
        )
        mediaPlayer.setOnCompletionListener {
            EventBus.getDefault().post(MessageEvent(Constant.COMPLETE_PLAY_MUSIC))
        }
        mediaPlayer.start()
    }

    private fun notification() {
        val notificationIntent = Intent(applicationContext, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_IMMUTABLE else PendingIntent.FLAG_UPDATE_CURRENT
        )
        val contentView = RemoteViews(packageName, R.layout.notification_music).apply {
            setImageViewResource(R.id.im_account, R.mipmap.ic_launcher)
            setImageViewResource(R.id.backgroud, R.drawable.bg_music)
            setTextViewText(R.id.nameSong, App.instance.musicCurrent.musicName)
            setTextViewText(R.id.nameSinger, App.instance.musicCurrent.nameSinger)
            if (mediaPlayer.isPlaying) {
               setImageViewResource(R.id.im_playSong, R.drawable.baseline_pause_24)
            }
            if (!App.instance.musicCurrent.checkFavorite) {
               setImageViewResource(
                    R.id.im_favorite,
                    R.drawable.ic_baseline_favorite_border_24
                )
            } else {
               setImageViewResource(
                    R.id.im_favorite,
                    R.drawable.ic_baseline_favorite_24_red
                )
            }


            setOnClickPendingIntent(
                R.id.im_playSong, getPendingSelfIntent(
                    applicationContext, Constant.PLAY_SONG
                )
            )


           setOnClickPendingIntent(
                R.id.im_favorite, getPendingSelfIntent(
                    applicationContext, Constant.PLAY_SONG
                )
            )
            setOnClickPendingIntent(
                R.id.im_nextSong, getPendingSelfIntent(
                    applicationContext, Constant.NEXT_SONG
                )
            )
           setOnClickPendingIntent(
                R.id.im_backSong, getPendingSelfIntent(
                    applicationContext, Constant.BACK_SONG
                )
            )
        }


        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getNotificationChannel(notificationManager)
        } else {
            ""
        }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
        notificationBuilder.setContent(contentView)
        val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
            .setFullScreenIntent(pendingIntent, true)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setContentTitle(getString(R.string.app_name))
            .setTicker(getString(R.string.ticker))
            .build()

        startForeground(110, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getNotificationChannel(notificationManager: NotificationManager): String {
        val channelId = getString(R.string.app_active_channel)
        NotificationChannel(channelId, getString(R.string.app_active), NotificationManager.IMPORTANCE_HIGH).apply {
            importance = NotificationManager.IMPORTANCE_NONE
            lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            notificationManager.createNotificationChannel(this)
        }

        return channelId
    }

    private fun getPendingSelfIntent(context: Context, action: String): PendingIntent? {
        val intent = Intent(context, Broadcast::class.java)
        intent.action = action
        return PendingIntent.getBroadcast(
            context,
            0,
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_MUTABLE else PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
    }

}