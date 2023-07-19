package com.example.appmusic.ui.main.home.musicfragment.musicdetail

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.PorterDuff
import android.media.MediaMetadataRetriever
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.example.appmusic.App
import com.example.appmusic.R
import com.example.appmusic.common.Constant
import com.example.appmusic.common.Constant.SEEK_TO_MEDIA_SERVICE
import com.example.appmusic.common.MessageEvent
import com.example.appmusic.data.model.ItemRecent
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.FragmentDetailSongBinding
import com.example.appmusic.service.MusicService
import com.example.appmusic.ui.base.BaseBindingFragment
import com.example.appmusic.ui.main.MainActivity
import com.example.appmusic.utils.TimeUtils
import jp.wasabeef.blurry.Blurry
import timber.log.Timber

class MusicDetailFragment :
    BaseBindingFragment<FragmentDetailSongBinding, MusicDetailViewModel>() {
    private val listFavourite: MutableList<Music> = mutableListOf()
    private val listRecent: MutableList<ItemRecent> = mutableListOf<ItemRecent>()
    private val runnable = Runnable {
        if (isAdded) {
            binding.sbTimeSong.progress = binding.sbTimeSong.progress + 1000
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(getString(R.string.KillAPP), true)
        outState.putInt(getString(R.string.maxProgress), binding.sbTimeSong.max)
        outState.putInt(getString(R.string.progress), binding.sbTimeSong.progress)
        outState.putString(getString(R.string.textViewEndTime), binding.tvEndTime.text.toString())
        outState.putString(
            getString(R.string.textViewStartTime),
            binding.tvStartTime.text.toString()
        )
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
            requireArguments().getBoolean(Constant.RUN_NEW_MUSIC, false).apply {
                if (this) {
                    if (!isKillApp) {
                        startService(Constant.START_MEDIA_SERVICE)
                        mainViewModel.isStartMedia.postValue(true)
                    }
                }
            }

        }
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        )
        binding.sbTimeSong.progressDrawable.setColorFilter(
            resources.getColor(R.color.white),
            PorterDuff.Mode.SRC_ATOP
        )
        initSeekBarChangeListener()
        with(binding) {
            if (App.instance.musicCurrent.musicName != null) {
                tvNameSong.text = (App.instance.musicCurrent.musicName)
                tvNameSinger.text = (App.instance.musicCurrent.nameSinger)
                App.instance.musicCurrent.musicFile?.let { setImageSong(it) }

            }
            if (savedInstanceState != null) {
                isKillApp = savedInstanceState.getBoolean(getString(R.string.KillAPP), false)
                sbTimeSong.max = savedInstanceState.getInt(getString(R.string.maxProgress))
                sbTimeSong.progress = savedInstanceState.getInt(getString(R.string.progress))
                tvStartTime.text =
                    savedInstanceState.getString(getString(R.string.textViewStartTime))
                tvEndTime.text =
                    savedInstanceState.getString(getString(R.string.textViewEndTime))
                imPlaySong.setImageResource(R.drawable.ic_baseline_pause_circle_filled_60)
            }
        }
        initListener()
        initData()
    }

    private fun initData() {

    }

    private fun initListener() {

        binding.imFastForward.setOnClickListener {
            var currentPos = getPosCurrentMusic(App.instance.musicCurrent)
            currentPos++
            if (currentPos > App.instance.listMusic.size - 1) {
                currentPos = 0
            }
            binding.sbTimeSong.progress = 0
            App.instance.musicCurrent = (App.instance.listMusic[currentPos])
            binding.imPlaySong.setImageResource(R.drawable.ic_baseline_play_circle_filled_60)
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
                with(binding){
                   tvNameSong.text = (App.instance.musicCurrent.musicName)
                   tvNameSinger.text = (App.instance.musicCurrent.nameSinger)
                    App.instance.musicCurrent.musicFile?.let { setImageSong(it) }
                    binding.tvEndTime.text =
                        (com.example.appmusic.utils.TimeUtils.getTimeDurationMusic(messageEvent.intValue1))
                    sbTimeSong.max = messageEvent.intValue1
                    imPlaySong.setImageResource(R.drawable.ic_baseline_pause_circle_24)
                    tvStartTime.text =
                        (com.example.appmusic.utils.TimeUtils.getTimeDurationMusic(messageEvent.intValue2))
                    sbTimeSong.progress = 0
                }

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
                App.instance.musicCurrent = (App.instance.listMusic[currentPos])
                startService(Constant.CHANGE_MUSIC_SERVICE)
            }

            Constant.START_MEDIA -> {
                Timber.e("nghialt: START_MEDIA")
                binding.imPlaySong.setImageResource(R.drawable.ic_baseline_pause_circle_24)
                binding.sbTimeSong.progress = binding.sbTimeSong.progress + 1
                mainViewModel.isStartMedia.postValue(true)
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
                mainViewModel.isStartMedia.postValue(false)
            }
        }
    }

    private fun startService(action: String) {
        val intent = Intent(requireActivity(), MusicService::class.java)
        intent.action = action
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Timber.e("ltnghia startService")
            requireActivity().startForegroundService(intent)
        } else {
            requireActivity().startService(intent)
        }
    }

    private fun initSeekBarChangeListener() {
        binding.sbTimeSong.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                binding.tvStartTime.text = (TimeUtils.getTimeDurationMusic(i))
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 1000)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                val intent = Intent(requireActivity(), MusicService::class.java)
                intent.action = SEEK_TO_MEDIA_SERVICE
                binding.sbTimeSong.progress = binding.sbTimeSong.progress
                intent.putExtra("SEEK_TO", binding.sbTimeSong.progress)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    requireActivity().startForegroundService(intent)
                } else {
                    requireActivity().startService(intent)
                }
            }
        })
    }
}



