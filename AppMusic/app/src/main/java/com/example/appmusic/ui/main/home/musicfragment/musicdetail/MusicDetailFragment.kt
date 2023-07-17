package com.example.appmusic.ui.main.home.musicfragment.musicdetail

import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import com.example.appmusic.App
import com.example.appmusic.R
import com.example.appmusic.common.Constant
import com.example.appmusic.common.MessageEvent
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.FragmentDetailSongBinding
import com.example.appmusic.service.MusicService
import com.example.appmusic.ui.base.BaseBindingFragment
import com.example.appmusic.ui.main.MainActivity
import jp.wasabeef.blurry.Blurry
import timber.log.Timber

class MusicDetailFragment :
    BaseBindingFragment<FragmentDetailSongBinding, MusicDetailViewModel>() {
    private val runnable = Runnable {
        if (isAdded) {
            binding.sbTimeSong.progress = binding.sbTimeSong.progress + 1000
        }
    }

    override fun getViewModel(): Class<MusicDetailViewModel> {
        return MusicDetailViewModel::class.java
    }

    var isKillApp = false
    var handler = Handler(Looper.getMainLooper())


    override val layoutId: Int
        get() = R.layout.fragment_detail_song


    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    }

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        if (arguments != null) {
            val isRunNew = requireArguments().getBoolean(Constant.RUN_NEW_MUSIC, false)
            if (isRunNew) {
                if (!isKillApp) {
                    startService(Constant.START_MEDIA_SERVICE)
//                    mainViewModel.isStartMedia.postValue(true)
                }
            }
        }
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        )

        if (App.instance.musicCurrent.musicName != null) {
            binding.tvNameSong.text = (App.instance.musicCurrent.musicName)
            binding.tvNameSinger.text = (App.instance.musicCurrent.nameSinger)
            App.instance.musicCurrent.musicFile?.let { setImageSong(it) }

        }

        initListener()


        initData()

    }

    private fun initData() {


    }

    private fun initListener() {
        binding.imFastForward.setOnClickListener {
            binding.imPlaySong.setImageResource(R.drawable.ic_baseline_play_circle_filled_60)
            var currentPos = getPosCurrentMusic(App.instance.musicCurrent)
            currentPos++
            if (currentPos > App.instance.listMusic.size - 1) {
                currentPos = 0
            }
            App.instance.musicCurrent = (App.instance.listMusic[currentPos])
            binding.sbTimeSong.progress = 0
            App.instance.musicCurrent.musicFile?.let { setImageSong(it) }
            startService(Constant.CHANGE_MUSIC_SERVICE)
        }
        binding.imPlaySong.setOnClickListener { startService(Constant.STOP_MEDIA_SERVICE) }
        binding.imRewind.setOnClickListener {
            var currentPos = getPosCurrentMusic(App.instance.musicCurrent)
            currentPos--
            if (currentPos < 0) {
                currentPos = App.instance.listMusic.size - 1
            }
            startService(Constant.STOP_MEDIA_SERVICE)
            App.instance.musicCurrent = (App.instance.listMusic[currentPos])
            binding.imPlaySong.setImageResource(R.drawable.ic_baseline_play_circle_filled_60)
            App.instance.musicCurrent.musicFile?.let { it1 -> setImageSong(it1) }
            startService(Constant.START_MEDIA_SERVICE)
        }
        binding.imDown.setOnClickListener {
            (requireActivity() as MainActivity).navController?.popBackStack()

        }


    }

    private fun getPosCurrentMusic(music: Music): Int {
        for (i in 0 until App.instance.listMusic.size) {
            if (App.instance.listMusic[i].musicFile
                    .equals(music.musicFile)
            ) {
                return i
            }
        }
        return -1
    }

    private fun setImageSong(path: String) {
        if (App.instance.musicCurrent.imageSong != null) {
            try {
                MediaMetadataRetriever().use { mmr ->
                    mmr.setDataSource(path)
                    val data = mmr.embeddedPicture
                    val bitmap =
                        BitmapFactory.decodeByteArray(data, 0, data!!.size)
                    binding.imSong.setImageBitmap(bitmap)
                    Blurry.with(requireContext()).radius(16).from(bitmap).into(binding.imSong)
                    binding.imSongTop.setImageBitmap(bitmap)
                }
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        } else {
            binding.imSongTop.setImageResource(R.drawable.bg_music)
        }
    }

    fun onEvent(messageEvent: MessageEvent) {
        when (messageEvent.typeEvent) {
            Constant.CHANGE_MUSIC_CURRENT -> {
                Timber.e("nghialt: CHANGE_MUSIC_CURRENT")
                binding.tvNameSong.text=(App.instance.musicCurrent.musicName)
                binding.tvNameSinger.text=(App.instance.musicCurrent.nameSinger)
                App.instance.musicCurrent.musicFile?.let { setImageSong(it) }
                binding.tvEndTime.text=(com.example.appmusic.utils.TimeUtils.getTimeDurationMusic(messageEvent.intValue1))
                binding.sbTimeSong.max = messageEvent.intValue1
                binding.imPlaySong.setImageResource(R.drawable.ic_baseline_pause_circle_24)
                binding.tvStartTime.text=(com.example.appmusic.utils.TimeUtils.getTimeDurationMusic(messageEvent.intValue2))
                binding.sbTimeSong.progress = 0
            }

            Constant.COMPLETE_PLAY_MUSIC -> {
                Timber.e("nghialt: COMPLETE_PLAY_MUSIC")
                binding.imPlaySong.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                var currentPos: Int
                currentPos = getPosCurrentMusic(App.instance.musicCurrent)
                if (!App.isLoop) {
                    currentPos++
                    if (currentPos > App.instance.listMusic.size - 1) {
                        currentPos = 0
                    }
                }
                App.instance.musicCurrent=(App.instance.listMusic[currentPos])
                startService(Constant.CHANGE_MUSIC_SERVICE)
            }

            Constant.START_MEDIA -> {
                Timber.e("nghialt: START_MEDIA")
                binding.imPlaySong.setImageResource(R.drawable.ic_baseline_pause_circle_24)
                binding.sbTimeSong.progress = binding.sbTimeSong.progress + 1
//                mainViewModel.isStartMedia.postValue(true)
            }

            Constant.FAVORITE_MEDIA -> {}
            Constant.STOP_MEDIA -> {
                if (messageEvent.isBooleanValue) {
                    App.instance.musicCurrent.musicFile?.let { setImageSong(it) }
                    binding.imPlaySong.setImageResource(R.drawable.ic_baseline_pause_circle_filled_60)
                } else {
                    binding.imPlaySong.setImageResource(R.drawable.ic_baseline_play_circle_filled_60)
                }
                handler.removeCallbacks(runnable)
//                mainViewModel.isStartMedia.postValue(false)
            }
        }
    }

    private fun startService(action: String) {
        val intent = Intent(requireActivity(), MusicService::class.java)
        intent.action = action
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requireActivity().startForegroundService(intent)
        } else {
            requireActivity().startService(intent)
        }
    }
}



