package com.example.appmusic.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import com.example.appmusic.App
import com.example.appmusic.R
import com.example.appmusic.common.Constant
import com.example.appmusic.common.Constant.CHANGE_MUSIC_CURRENT
import com.example.appmusic.common.MessageEvent
import com.example.appmusic.ui.main.MainActivity
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

class MusicService : Service() {
    var mediaPlayer: MediaPlayer = MediaPlayer()
    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
//        notification()
    }

    override fun onUnbind(intent: Intent): Boolean {
        return super.onUnbind(intent)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
//        notification()
        if (intent.action != null) {
            when (intent.action) {
                Constant.CHANGE_MUSIC_SERVICE, Constant.START_MEDIA_SERVICE -> {
                    Timber.e("nghialt: CHANGE_MUSIC_SERVICE")
                    startMedia()
                }

                Constant.SEEK_TO_MEDIA_SERVICE -> mediaPlayer.seekTo(
                    intent.getIntExtra(
                        "SEEK_TO",
                        0
                    )
                )

                Constant.FAVOURITE -> Timber.e("nghialt: FAVOURITE")
                Constant.STOP_MEDIA_SERVICE -> {
                    Timber.e("nghialt: STOP_MEDIA_SERVICE")
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
//                mediaPlayer = null
        }
        mediaPlayer =
            MediaPlayer.create(applicationContext, Uri.parse(App.instance.musicCurrent.musicFile))
        EventBus.getDefault().post(
            MessageEvent(
                CHANGE_MUSIC_CURRENT,
                mediaPlayer.duration,
                mediaPlayer.currentPosition
            )
        )
        mediaPlayer.setOnCompletionListener(OnCompletionListener {
            EventBus.getDefault().post(MessageEvent(Constant.COMPLETE_PLAY_MUSIC))
        })
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
        val contentView = RemoteViews(packageName, R.layout.notification_music)
        contentView.setImageViewResource(R.id.im_account, R.mipmap.ic_launcher)
        if (App.instance.musicCurrent != null) {
            contentView.setImageViewResource(R.id.backgroud, R.drawable.bg_music)
            contentView.setTextViewText(R.id.nameSong, App.instance.musicCurrent.musicName)
            contentView.setTextViewText(R.id.nameSinger, App.instance.musicCurrent.nameSinger)
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying) {
                    contentView.setImageViewResource(R.id.im_playSong, R.drawable.baseline_pause_24)
                }
                if (!App.instance.musicCurrent.checkFavorite) {
                    contentView.setImageViewResource(
                        R.id.im_favorite,
                        R.drawable.ic_baseline_favorite_border_24
                    )
                } else {
                    contentView.setImageViewResource(
                        R.id.im_favorite,
                        R.drawable.ic_baseline_favorite_24_red
                    )
                }
            }
        }


//        contentView.setOnClickPendingIntent(R.id.im_playSong, getPendingSelfIntent(getApplicationContext(), Constant.PLAY_SONG));
//
//
//        contentView.setOnClickPendingIntent(R.id.im_favorite, getPendingSelfIntent(getApplicationContext(), Constant.PLAY_SONG));
//        contentView.setOnClickPendingIntent(R.id.im_nextSong, getPendingSelfIntent(getApplicationContext(), Constant.NEXT_SONG));
//        contentView.setOnClickPendingIntent(R.id.im_backSong, getPendingSelfIntent(getApplicationContext(), Constant.BACK_SONG));
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? getNotificationChannel(Objects.requireNonNull(notificationManager)) : "");
//        notificationBuilder.setContent(contentView);
//        Notification notification = notificationBuilder.setOngoing(true)
//                .setSmallIcon(R.drawable.ic_launcher_background)
//                .setContentIntent(pendingIntent)
//                .setFullScreenIntent(pendingIntent, true)
//                .setCategory(NotificationCompat.CATEGORY_SERVICE)
//                .setContentTitle(getString(R.string.app_name))
//                .setTicker("ticker")
//                .build();
//
//        startForeground(110, notification);
//    }
//
//    protected PendingIntent getPendingSelfIntent(Context context, String action) {
//        Intent intent = new Intent(context, Broadcast.class);
//        intent.setAction(action);
//        return PendingIntent.getBroadcast(context, 0, intent, (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) ? PendingIntent.FLAG_MUTABLE : PendingIntent.FLAG_UPDATE_CURRENT);
//    }

        //    @RequiresApi(Build.VERSION_CODES.O)
//    private String getNotificationChannel(NotificationManager notificationManager) {
//        String channelId = "app_active_channel";
//        NotificationChannel channel = new NotificationChannel(channelId, "App Active", NotificationManager.IMPORTANCE_HIGH);
//        channel.setImportance(NotificationManager.IMPORTANCE_NONE);
//        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
//        notificationManager.createNotificationChannel(channel);
//        return channelId;
//    }
        @Override
        fun onDestroy() {
            super.onDestroy()
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }
}