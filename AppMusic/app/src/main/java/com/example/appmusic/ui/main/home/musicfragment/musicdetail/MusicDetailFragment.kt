package com.example.appmusic.ui.main.home.musicfragment.musicdetail

import android.content.Context
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
import com.example.appmusic.common.makeStatusBarLight
import com.example.appmusic.common.startService
import com.example.appmusic.data.model.DataItemRecent
import com.example.appmusic.data.model.DataMusic
import com.example.appmusic.databinding.FragmentDetailSongBinding
import com.example.appmusic.service.MusicService
import com.example.appmusic.ui.base.BaseBindingFragment
import com.example.appmusic.ui.main.MainActivity
import com.example.appmusic.ui.main.home.musicfragment.dialogfragment.dialog.BottomSheetAddPlayListFrag
import com.example.appmusic.utils.TimeUtils
import jp.wasabeef.blurry.Blurry
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MusicDetailFragment : BaseBindingFragment<FragmentDetailSongBinding, MusicDetailViewModel>() {
    private val runnable = Runnable {
        if (isAdded) {
            binding.sbTimeSong.progress = binding.sbTimeSong.progress + 1000
        }
    }
    private val listFavourite: MutableList<DataMusic> = mutableListOf()
    private val listRecent: MutableList<DataItemRecent> = mutableListOf()


    private var isKillApp = false
    var handler = Handler(Looper.getMainLooper())
    private var checked = false


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

    override val layoutId: Int
        get() = R.layout.fragment_detail_song

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
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
        makeStatusBarLight(requireActivity())
        binding.sbTimeSong.progressDrawable.setColorFilter(
            resources.getColor(R.color.white),
            PorterDuff.Mode.SRC_ATOP
        )
        initSeekBarChangeListener()
        with(binding) {
            with(App.instance.musicCurrent) {
                tvNameSong.text = musicName
                tvNameSinger.text = nameSinger
                setImageSong(musicFile)
            }

            savedInstanceState?.let {
                isKillApp = it.getBoolean(getString(R.string.KillAPP), false)
                sbTimeSong.max = it.getInt(getString(R.string.maxProgress))
                sbTimeSong.progress = it.getInt(getString(R.string.progress))
                tvStartTime.text = it.getString(getString(R.string.textViewStartTime))
                tvEndTime.text = it.getString(getString(R.string.textViewEndTime))
            }
        }
        initListener()
        observerData()
    }

    private fun observerData() {
        mainViewModel.getAllRecentMusic()
        mainViewModel.listRecentLiveData.observe(viewLifecycleOwner) { reccentlies ->
            if (reccentlies != null) {
                listRecent.clear()
                listRecent.addAll(reccentlies)
                val dataMusicCurrent: DataMusic = App.instance.musicCurrent
                insertRecent(dataMusicCurrent, DataItemRecent().apply {
                    musicFile = dataMusicCurrent.musicFile
                    musicName = dataMusicCurrent.musicName
                    nameSinger = dataMusicCurrent.nameSinger
                    nameAlbum = dataMusicCurrent.nameAlbum
                    namePlayList = dataMusicCurrent.namePlayList
                    imageSong = (dataMusicCurrent.imageSong)
                }
                )
                mainViewModel.listRecentLiveData.postValue(null)
            }
        }
        viewModel.getAllFavourite(true)


        viewModel.listFavourite.observe(viewLifecycleOwner) { list ->
            if (list != null) {
                listFavourite.clear()
                listFavourite.addAll(list)
            }
            checkFavourite()
        }

    }

    private fun getPosCurrentMusic(music: DataMusic): Int {
        return App.instance.listDataMusic.indexOfFirst { it.musicFile == music.musicFile }
    }

    private fun initListener() {
        binding.imFavorite.setOnClickListener {
            binding.imFavorite.setImageResource(R.drawable.ic_baseline_favorite_24_red)
            if (!checked) {
                checked = true
                val musicFile = App.instance.musicCurrent.musicFile
                val checkFavourite = listFavourite.any { it.musicFile == musicFile }

                if (!checkFavourite) {
                    App.instance.musicCurrent.checkFavorite = true
                    viewModel.insertFavorite(App.instance.musicCurrent)
                }
            } else {
                checked = false
                binding.imFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                App.instance.musicCurrent.checkFavorite = checked
                viewModel.deleteFavourite(App.instance.musicCurrent.musicFile)
            }
            startService(Constant.FAVOURITE)
        }
        binding.imListPlay.setOnClickListener {
            val bottomSheetAddPlayListFrag = BottomSheetAddPlayListFrag()
            bottomSheetAddPlayListFrag.dataMusicCurrent=(App.instance.musicCurrent)
            bottomSheetAddPlayListFrag.show(childFragmentManager, null)
        }
        binding.imFastForward.setOnClickListener {
            binding.imPlaySong.setImageResource(R.drawable.ic_baseline_play_circle_filled_60)
            var currentPos = getPosCurrentMusic(App.instance.musicCurrent)
            currentPos++
            if (currentPos > App.instance.listDataMusic.size - 1) {
                currentPos = 0
            }
            App.instance.musicCurrent = (App.instance.listDataMusic[currentPos])
            binding.sbTimeSong.progress = 0
            setImageSong(App.instance.musicCurrent.musicFile)
            startService(Constant.CHANGE_MUSIC_SERVICE)
        }
        binding.imPlaySong.setOnClickListener { startService(Constant.STOP_MEDIA_SERVICE) }
        binding.imRewind.setOnClickListener {
            with(App.instance.musicCurrent) {
                var currentPos = getPosCurrentMusic(this)
                currentPos--
                if (currentPos < 0) {
                    currentPos = App.instance.listDataMusic.size - 1
                }
                startService(Constant.STOP_MEDIA_SERVICE)
                App.instance.musicCurrent = (App.instance.listDataMusic[currentPos])
                binding.imPlaySong.setImageResource(R.drawable.ic_baseline_play_circle_filled_60)
                setImageSong(this.musicFile)
            }

            startService(Constant.START_MEDIA_SERVICE)
        }
        binding.imDown.setOnClickListener {
            (requireActivity() as MainActivity).navController.popBackStack()

        }
        binding.imPlayAgain.setOnClickListener {
            if (!App.isLoop) {
                App.isLoop = true
                binding.imPlayAgain.setColorFilter(R.color.black)
            } else {
                binding.imPlayAgain.colorFilter = null
                App.isLoop = false
            }
        }


    }


    private fun setImageSong(pathMusic: String) {
        if (App.instance.musicCurrent.imageSong != null) {
            try {
                MediaMetadataRetriever().use { mmr ->
                    mmr.setDataSource(pathMusic)
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(messageEvent: MessageEvent) {
        when (messageEvent.typeEvent) {
            Constant.CHANGE_MUSIC_CURRENT -> {
                with(binding) {
                    tvNameSong.text = (App.instance.musicCurrent.musicName)
                    tvNameSinger.text = (App.instance.musicCurrent.nameSinger)
                    App.instance.musicCurrent.musicFile.let { setImageSong(it) }
                    binding.tvEndTime.text =
                        (TimeUtils.getTimeDurationMusic(messageEvent.intValue1))
                    sbTimeSong.max = messageEvent.intValue1
                    imPlaySong.setImageResource(R.drawable.ic_baseline_pause_circle_24)
                    tvStartTime.text =
                        (TimeUtils.getTimeDurationMusic(messageEvent.intValue2))
                    sbTimeSong.progress = 0
                    checkFavourite()

                }

            }

            Constant.COMPLETE_PLAY_MUSIC -> {
                binding.imPlaySong.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                var currentPos: Int
                currentPos = getPosCurrentMusic(App.instance.musicCurrent)
                if (!App.isLoop) {
                    currentPos++
                    if (currentPos > App.instance.listDataMusic.size - 1) {
                        currentPos = 0
                    }
                }
                App.instance.musicCurrent = (App.instance.listDataMusic[currentPos])
                startService(Constant.CHANGE_MUSIC_SERVICE)

            }

            Constant.START_MEDIA -> {
                binding.imPlaySong.setImageResource(R.drawable.ic_baseline_pause_circle_24)
                binding.sbTimeSong.progress = binding.sbTimeSong.progress + 1
                mainViewModel.isStartMedia.postValue(true)

            }

            Constant.FAVORITE_MEDIA -> {}
            Constant.STOP_MEDIA -> {
                checkFavourite()

                if (messageEvent.isBooleanValue) {
                    App.instance.musicCurrent.musicFile.let { setImageSong(it) }
                    binding.imPlaySong.setImageResource(R.drawable.ic_baseline_pause_circle_filled_60)
                } else {
                    binding.imPlaySong.setImageResource(R.drawable.ic_baseline_play_circle_filled_60)
                }
                handler.removeCallbacks(runnable)
                mainViewModel.isStartMedia.postValue(false)
            }
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
                intent.putExtra(Constant.SEEK_TO, binding.sbTimeSong.progress)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    requireActivity().startForegroundService(intent)
                } else {
                    requireActivity().startService(intent)
                }
            }
        })
    }

    private fun checkFavourite() {
        val isExit = listFavourite.any { it.musicFile == App.instance.musicCurrent.musicFile }
        val imageResource = if (isExit) {
            R.drawable.ic_baseline_favorite_24_red
        } else {
            R.drawable.ic_baseline_favorite_border_24
        }
        binding.imFavorite.setImageResource(imageResource)
    }

    private fun insertRecent(dataMusic: DataMusic, dataItemRecent: DataItemRecent) {

        val checkExistedRecent = listRecent.any { it.musicFile == dataMusic.musicFile }

        if (!checkExistedRecent) {
            mainViewModel.insertRecentMusic(dataItemRecent)
            listRecent.add(dataItemRecent)
        }
    }

}



