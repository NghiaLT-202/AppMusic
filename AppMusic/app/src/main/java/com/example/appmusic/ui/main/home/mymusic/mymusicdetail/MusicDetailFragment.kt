package com.example.appmusic.ui.main.home.mymusic.mymusicdetail

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
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
import androidx.lifecycle.ViewModelProvider.get
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.appmusic.App
import com.example.appmusic.R
import com.example.appmusic.common.Constant
import com.example.appmusic.common.MessageEvent
import com.example.appmusic.data.model.ItemRecent
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.FragmentDetailSongBinding
import com.example.appmusic.service.MusicService
import com.example.appmusic.ui.base.BaseBindingFragment
import com.example.appmusic.ui.main.home.mymusic.mymusicdetail.musicfragment.dialogfragment.dialog.BottomSheetAddPlayListFrag
import com.example.appmusic.utils.TimeUtils
import jp.wasabeef.blurry.Blurry
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber

class MusicDetailFragment :
    BaseBindingFragment<FragmentDetailSongBinding?, MusicDetailViewModel>() {
    private val listFavourite: MutableList<Music?> = ArrayList()
    private val listRecent: MutableList<ItemRecent?> = ArrayList()
    private val runnable = Runnable {
        if (isAdded) {
            binding!!.sbTimeSong.progress = binding!!.sbTimeSong.progress + 1000
        }
    }
    var isKillApp = false
    var handler = Handler(Looper.getMainLooper())
    private var checked = false
    override val layoutId: Int
        get() = R.layout.fragment_detail_song

    override fun getViewModel(): Class<MusicDetailViewModel>? {
        return MusicDetailViewModel::class.java
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(getString(R.string.KillAPP), true)
        outState.putInt(getString(R.string.maxProgress), binding!!.sbTimeSong.max)
        outState.putInt(getString(R.string.progress), binding!!.sbTimeSong.progress)
        outState.putString(getString(R.string.textViewEndTime), binding!!.tvEndTime.text.toString())
        outState.putString(
            getString(R.string.textViewStartTime),
            binding!!.tvStartTime.text.toString()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.e("nghialt: onDestroyView")
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    }

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        if (arguments != null) {
            val isRunNew = arguments!!.getBoolean(Constant.RUN_NEW_MUSIC, false)
            if (isRunNew) {
                if (!isKillApp) {
                    startService(Constant.START_MEDIA_SERVICE)
                    mainViewModel!!.isStartMedia.postValue(true)
                }
            }
        }
        Timber.e("nghialt: onCreatedView")
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        )
        binding!!.sbTimeSong.progressDrawable.setColorFilter(
            resources.getColor(R.color.white),
            PorterDuff.Mode.SRC_ATOP
        )
        initSeekBarChangeListener()
        if (App.Companion.getInstance().getMusicCurrent().getMusicName() != null) {
            binding!!.tvNameSong.setText(
                App.Companion.getInstance().getMusicCurrent().getMusicName()
            )
            binding!!.tvNameSinger.setText(
                App.Companion.getInstance().getMusicCurrent().getNameSinger()
            )
            setImageSong(App.Companion.getInstance().getMusicCurrent().getMusicFile())
        }
        if (savedInstanceState != null) {
            isKillApp = savedInstanceState.getBoolean(getString(R.string.KillAPP), false)
            binding!!.sbTimeSong.max = savedInstanceState.getInt(getString(R.string.maxProgress))
            binding!!.sbTimeSong.progress = savedInstanceState.getInt(getString(R.string.progress))
            binding!!.tvStartTime.text =
                savedInstanceState.getString(getString(R.string.textViewStartTime))
            binding!!.tvEndTime.text =
                savedInstanceState.getString(getString(R.string.textViewEndTime))
            binding!!.imPlaySong.setImageResource(R.drawable.ic_baseline_pause_circle_filled_60)
        }
        initListener()
        initData()
    }

    fun insertReccently(music: Music?, itemRecent: ItemRecent?) {
        var checkExistedRecent = false
        for (reccent in listRecent) {
            if (reccent.getMusicFile() == music.getMusicFile()) {
                checkExistedRecent = true
                break
            }
        }
        if (!checkExistedRecent) {
            mainViewModel!!.insertReccentMusic(itemRecent)
            listRecent.add(itemRecent)
        }
    }

    private fun initData() {
        mainViewModel.getAllReccentMusic()
        observerData()
    }

    private fun observerData() {
        mainViewModel!!.listRecentLiveData.observe(viewLifecycleOwner) { reccentlies: List<ItemRecent?>? ->
            if (reccentlies != null) {
                listRecent.clear()
                listRecent.addAll(reccentlies)
                val musicCurrent: Music = App.Companion.getInstance().getMusicCurrent()
                val itemRecent = ItemRecent(
                    musicCurrent.musicFile,
                    musicCurrent.musicName,
                    musicCurrent.nameSinger,
                    musicCurrent.nameAlbum,
                    musicCurrent.namePlayList
                )
                itemRecent.imageSong = musicCurrent.imageSong
                insertReccently(musicCurrent, itemRecent)
                mainViewModel!!.listRecentLiveData.postValue(null)
            }
        }
        viewModel!!.getAllFavourite(true)
        viewModel!!.listFavourite.observe(viewLifecycleOwner) { list: List<Music?>? ->
            if (list != null) {
                listFavourite.clear()
                listFavourite.addAll(list)
            }
            checkFavourite()
        }
        mainViewModel!!.listAllMusicDevice.observe(viewLifecycleOwner) { songs: List<Music?>? ->
            if (songs != null) {
                App.Companion.getInstance().getListMusic().clear()
                App.Companion.getInstance().getListMusic().addAll(songs)
            }
        }
    }

    private fun getPosCurrentMusic(music: Music?): Int {
        for (i in App.Companion.getInstance().getListMusic().indices) {
            if (App.Companion.getInstance().getListMusic().get(i)
                    .getMusicFile() == music.getMusicFile()
            ) {
                return i
            }
        }
        return -1
    }

    private fun initListener() {
        binding!!.imFavorite.setOnClickListener { v: View? ->
            binding!!.imFavorite.setImageResource(R.drawable.ic_baseline_favorite_24_red)
            if (!checked) {
                checked = true
                var checkFavourite = false
                for (i in listFavourite.indices) {
                    if (listFavourite[i].getMusicFile() == App.Companion.getInstance()
                            .getMusicCurrent().getMusicFile()
                    ) {
                        checkFavourite = true
                        break
                    }
                }
                if (!checkFavourite) {
                    App.Companion.getInstance().getMusicCurrent().setCheckFavorite(true)
                    viewModel!!.insertFavorite(App.Companion.getInstance().getMusicCurrent())
                }
            } else {
                checked = false
                binding!!.imFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                App.Companion.getInstance().getMusicCurrent().setCheckFavorite(checked)
                viewModel!!.deleteFavourite(
                    App.Companion.getInstance().getMusicCurrent().getMusicFile()
                )
            }
            startService(Constant.FAVOURITE)
        }
        binding!!.imPlaySong.setOnClickListener { view: View? -> startService(Constant.STOP_MEDIA_SERVICE) }
        binding!!.imListPlay.setOnClickListener {
            val bottomSheetAddPlayListFrag = BottomSheetAddPlayListFrag()
            bottomSheetAddPlayListFrag.setMusicCurent(App.Companion.getInstance().getMusicCurrent())
            bottomSheetAddPlayListFrag.show(childFragmentManager, null)
        }
        binding!!.imFastForward.setOnClickListener { view: View? ->
            binding!!.imPlaySong.setImageResource(R.drawable.ic_baseline_play_circle_filled_60)
            var currentPos = getPosCurrentMusic(App.Companion.getInstance().getMusicCurrent())
            currentPos++
            if (currentPos > App.Companion.getInstance().getListMusic().size - 1) {
                currentPos = 0
            }
            App.Companion.getInstance()
                .setMusicCurrent(App.Companion.getInstance().getListMusic().get(currentPos))
            binding!!.sbTimeSong.progress = 0
            setImageSong(App.Companion.getInstance().getMusicCurrent().getMusicFile())
            startService(Constant.CHANGE_MUSIC_SERVICE)
        }
        binding!!.imRewind.setOnClickListener { view: View? ->
            var currentPos = getPosCurrentMusic(App.Companion.getInstance().getMusicCurrent())
            currentPos--
            if (currentPos < 0) {
                currentPos = App.Companion.getInstance().getListMusic().size - 1
            }
            startService(Constant.STOP_MEDIA_SERVICE)
            App.Companion.getInstance()
                .setMusicCurrent(App.Companion.getInstance().getListMusic().get(currentPos))
            binding!!.imPlaySong.setImageResource(R.drawable.ic_baseline_play_circle_filled_60)
            setImageSong(App.Companion.getInstance().getMusicCurrent().getMusicFile())
            startService(Constant.START_MEDIA_SERVICE)
        }
        binding!!.imPlayAgain.setOnClickListener { v: View? ->
            if (!App.Companion.isLoop) {
                App.Companion.isLoop = true
                binding!!.imPlayAgain.setColorFilter(R.color.black)
            } else {
                binding!!.imPlayAgain.colorFilter = null
                App.Companion.isLoop = false
            }
        }
        binding!!.imDown.setOnClickListener { view: View? -> requireActivity().supportFragmentManager.popBackStack() }
    }

    private fun setImageSong(path: String?) {
        if (App.Companion.getInstance().getMusicCurrent().getImageSong() != null) {
            try {
                MediaMetadataRetriever().use { mmr ->
                    mmr.setDataSource(path)
                    val data = mmr.embeddedPicture
                    val bitmap = BitmapFactory.decodeByteArray(data, 0, data!!.size)
                    binding!!.imSong.setImageBitmap(bitmap)
                    Blurry.with(requireContext()).radius(16).from(bitmap).into(binding!!.imSong)
                    binding!!.imSongTop.setImageBitmap(bitmap)
                }
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        } else {
            Glide.with(requireContext()).asBitmap().load(R.drawable.bg_music)
                .listener(object : RequestListener<Bitmap?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Bitmap?>,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Bitmap?,
                        model: Any,
                        target: Target<Bitmap?>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding!!.imSong.setImageBitmap(resource)
                        Blurry.with(requireContext()).radius(16).from(resource)
                            .into(binding!!.imSong)
                        return true
                    }
                }).submit()
            binding!!.imSongTop.setImageResource(R.drawable.bg_music)
        }
    }

    private fun checkFavourite() {
        var isExit = false
        if (listFavourite.size > 0) {
            for (i in listFavourite.indices) {
                if (listFavourite[i].getMusicFile() == App.Companion.getInstance().getMusicCurrent()
                        .getMusicFile()
                ) {
                    isExit = true
                    break
                }
            }
        }
        if (isExit) {
            binding!!.imFavorite.setImageResource(R.drawable.ic_baseline_favorite_24_red)
        } else {
            binding!!.imFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    private fun initSeekBarChangeListener() {
        binding!!.sbTimeSong.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                binding!!.tvStartTime.text = TimeUtils.getTimeDurationMusic(i)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 1000)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                val intent = Intent(requireActivity(), MusicService::class.java)
                intent.action = Constant.SEEK_TO_MEDIA_SERVICE
                binding!!.sbTimeSong.progress = binding!!.sbTimeSong.progress
                intent.putExtra("SEEK_TO", binding!!.sbTimeSong.progress)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    requireActivity().startForegroundService(intent)
                } else {
                    requireActivity().startService(intent)
                }
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        Timber.e("nghialt: onDetach")
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(messageEvent: MessageEvent) {
        when (messageEvent.typeEvent) {
            Constant.CHANGE_MUSIC_CURRENT -> {
                Timber.e("nghialt: CHANGE_MUSIC_CURRENT")
                binding!!.tvNameSong.setText(
                    App.Companion.getInstance().getMusicCurrent().getMusicName()
                )
                binding!!.tvNameSinger.setText(
                    App.Companion.getInstance().getMusicCurrent().getNameSinger()
                )
                setImageSong(App.Companion.getInstance().getMusicCurrent().getMusicFile())
                binding!!.tvEndTime.text =
                    TimeUtils.getTimeDurationMusic(messageEvent.intValue1)
                binding!!.sbTimeSong.max = messageEvent.intValue1
                binding!!.imPlaySong.setImageResource(R.drawable.ic_baseline_pause_circle_24)
                binding!!.tvStartTime.text =
                    TimeUtils.getTimeDurationMusic(messageEvent.intValue2)
                binding!!.sbTimeSong.progress = 0
            }

            Constant.COMPLETE_PLAY_MUSIC -> {
                Timber.e("nghialt: COMPLETE_PLAY_MUSIC")
                binding!!.imPlaySong.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                var currentPos: Int
                currentPos = getPosCurrentMusic(App.Companion.getInstance().getMusicCurrent())
                if (!App.Companion.isLoop) {
                    currentPos++
                    if (currentPos > App.Companion.getInstance().getListMusic().size - 1) {
                        currentPos = 0
                    }
                }
                App.Companion.getInstance()
                    .setMusicCurrent(App.Companion.getInstance().getListMusic().get(currentPos))
                startService(Constant.CHANGE_MUSIC_SERVICE)
            }

            Constant.START_MEDIA -> {
                Timber.e("nghialt: START_MEDIA")
                binding!!.imPlaySong.setImageResource(R.drawable.ic_baseline_pause_circle_24)
                binding!!.sbTimeSong.progress = binding!!.sbTimeSong.progress + 1
                mainViewModel!!.isStartMedia.postValue(true)
            }

            Constant.FAVORITE_MEDIA -> {}
            Constant.STOP_MEDIA -> {
                if (messageEvent.isBooleanValue) {
                    setImageSong(App.Companion.getInstance().getMusicCurrent().getMusicFile())
                    binding!!.imPlaySong.setImageResource(R.drawable.ic_baseline_pause_circle_filled_60)
                } else {
                    binding!!.imPlaySong.setImageResource(R.drawable.ic_baseline_play_circle_filled_60)
                }
                handler.removeCallbacks(runnable)
                mainViewModel!!.isStartMedia.postValue(false)
            }
        }
    }

    private fun startService(action: String?) {
        val intent = Intent(requireActivity(), MusicService::class.java)
        intent.action = action
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requireActivity().startForegroundService(intent)
        } else {
            requireActivity().startService(intent)
        }
    }
}