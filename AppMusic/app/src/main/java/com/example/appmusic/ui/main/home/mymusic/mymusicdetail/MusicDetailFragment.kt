package com.example.appmusic.ui.main.home.mymusic.mymusicdetail

import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.view.View
import com.example.tfmmusic.common.Constant.SEEK_TO_MEDIA_SERVICE

class MusicDetailFragment :
    BaseBindingFragment<FragmentDetailSongBinding?, MusicDetailViewModel?>() {
    private val listFavourite: MutableList<Music> = ArrayList<Music>()
    private val listRecent: MutableList<ItemRecent> = ArrayList<ItemRecent>()
    private val runnable = Runnable {
        if (isAdded()) {
            binding.sbTimeSong.setProgress(binding.sbTimeSong.getProgress() + 1000)
        }
    }
    var isKillApp = false
    var handler: Handler = Handler(Looper.getMainLooper())
    private var checked = false
    val layoutId: Int
        get() = R.layout.fragment_detail_song
    protected val viewModel: Class<MusicDetailViewModel>
        protected get() = MusicDetailViewModel::class.java

    fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.e("nghialt: onSaveInstanceState")
        outState.putBoolean(getString(R.string.KillAPP), true)
        outState.putInt(getString(R.string.maxProgress), binding.sbTimeSong.getMax())
        outState.putInt(getString(R.string.progress), binding.sbTimeSong.getProgress())
        outState.putString(
            getString(R.string.textViewEndTime),
            binding.tvEndTime.getText().toString()
        )
        outState.putString(
            getString(R.string.textViewStartTime),
            binding.tvStartTime.getText().toString()
        )
        //        outState.putInt("image_play_song",binding.imPlaySong.getImageAlpha());
    }

    fun onDestroyView() {
        super.onDestroyView()
        requireActivity().getWindow()
            .clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    }

    protected fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initSeekBarChangeListener()
        if (savedInstanceState != null) {
            binding.sbTimeSong.getProgressDrawable()
                .setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
            isKillApp = savedInstanceState.getBoolean(getString(R.string.KillAPP), false)
            binding.sbTimeSong.setMax(savedInstanceState.getInt(getString(R.string.maxProgress)))
            binding.sbTimeSong.setProgress(savedInstanceState.getInt(getString(R.string.progress)))
            binding.tvStartTime.setText(savedInstanceState.getString(getString(R.string.textViewStartTime)))
            binding.tvEndTime.setText(savedInstanceState.getString(getString(R.string.textViewEndTime)))
            binding.imPlaySong.setImageResource(R.drawable.ic_baseline_pause_circle_filled_60)
        } else {
            if (getArguments() != null) {
                val isRunNew: Boolean = getArguments().getBoolean(Constant.RUN_NEW_MUSIC, false)
                if (isRunNew) {
                    if (!isKillApp) {
                        startService(Constant.START_MEDIA_SERVICE)
                        mainViewModel.isStartMedia.postValue(true)
                    }
                }
            }
        }
        requireActivity().getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        )
        if (App.getInstance().getMusicCurrent().getMusicName() != null) {
            binding.tvNameSong.setText(App.getInstance().getMusicCurrent().getMusicName())
            binding.tvNameSinger.setText(App.getInstance().getMusicCurrent().getNameSinger())
            setImageSong(App.getInstance().getMusicCurrent().getMusicFile())
        }
        initListener()
        initData()
    }

    fun insertReccently(music: Music, itemRecent: ItemRecent) {
        var checkExistedRecent = false
        for (reccent in listRecent) {
            if (reccent.getMusicFile().equals(music.getMusicFile())) {
                checkExistedRecent = true
                break
            }
        }
        if (!checkExistedRecent) {
            mainViewModel.insertReccentMusic(itemRecent)
            listRecent.add(itemRecent)
        }
    }

    private fun initData() {
        mainViewModel.getAllReccentMusic()
        observerData()
    }

    private fun observerData() {
        mainViewModel.listRecentLiveData.observe(getViewLifecycleOwner()) { reccentlies ->
            if (reccentlies != null) {
                listRecent.clear()
                listRecent.addAll(reccentlies)
                val musicCurrent: Music = App.getInstance().getMusicCurrent()
                val itemRecent = ItemRecent(
                    musicCurrent.getMusicFile(),
                    musicCurrent.getMusicName(),
                    musicCurrent.getNameSinger(),
                    musicCurrent.getNameAlbum(),
                    musicCurrent.getNamePlayList()
                )
                itemRecent.setImageSong(musicCurrent.getImageSong())
                insertReccently(musicCurrent, itemRecent)
                mainViewModel.listRecentLiveData.postValue(null)
            }
        }
        viewModel.getAllFavourite(true)
        viewModel.listFavourite.observe(getViewLifecycleOwner()) { list ->
            if (list != null) {
                listFavourite.clear()
                listFavourite.addAll(list)
            }
            checkFavourite()
        }
        mainViewModel.listAllMusicDevice.observe(getViewLifecycleOwner()) { songs ->
            if (songs != null) {
                App.getInstance().getListMusic().clear()
                App.getInstance().getListMusic().addAll(songs)
            }
        }
    }

    private fun getPosCurrentMusic(music: Music): Int {
        for (i in 0 until App.getInstance().getListMusic().size()) {
            if (App.getInstance().getListMusic().get(i).getMusicFile()
                    .equals(music.getMusicFile())
            ) {
                return i
            }
        }
        return -1
    }

    private fun initListener() {
        binding.imFavorite.setOnClickListener { v ->
            binding.imFavorite.setImageResource(R.drawable.ic_baseline_favorite_24_red)
            if (!checked) {
                checked = true
                var checkFavourite = false
                for (i in listFavourite.indices) {
                    if (listFavourite[i].getMusicFile()
                            .equals(App.getInstance().getMusicCurrent().getMusicFile())
                    ) {
                        checkFavourite = true
                        break
                    }
                }
                if (!checkFavourite) {
                    App.getInstance().getMusicCurrent().setCheckFavorite(true)
                    viewModel.insertFavorite(App.getInstance().getMusicCurrent())
                }
            } else {
                checked = false
                binding.imFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                App.getInstance().getMusicCurrent().setCheckFavorite(checked)
                viewModel.deleteFavourite(App.getInstance().getMusicCurrent().getMusicFile())
            }
            startService(Constant.FAVOURITE)
        }
        binding.imPlaySong.setOnClickListener { view -> startService(Constant.STOP_MEDIA_SERVICE) }
        binding.imListPlay.setOnClickListener(View.OnClickListener {
            val bottomSheetAddPlayListFrag = BottomSheetAddPlayListFrag()
            bottomSheetAddPlayListFrag.setMusicCurent(App.getInstance().getMusicCurrent())
            bottomSheetAddPlayListFrag.show(getChildFragmentManager(), null)
        })
        binding.imFastForward.setOnClickListener { view ->
            binding.imPlaySong.setImageResource(R.drawable.ic_baseline_play_circle_filled_60)
            var currentPos = getPosCurrentMusic(App.getInstance().getMusicCurrent())
            currentPos++
            if (currentPos > App.getInstance().getListMusic().size() - 1) {
                currentPos = 0
            }
            App.getInstance().setMusicCurrent(App.getInstance().getListMusic().get(currentPos))
            Timber.e("nghialt: sbTimeSong")
            binding.sbTimeSong.setProgress(0)
            setImageSong(App.getInstance().getMusicCurrent().getMusicFile())
            startService(Constant.START_MEDIA_SERVICE)
        }
        binding.imRewind.setOnClickListener { view ->
            var currentPos = getPosCurrentMusic(App.getInstance().getMusicCurrent())
            currentPos--
            if (currentPos < 0) {
                currentPos = App.getInstance().getListMusic().size() - 1
            }
            App.getInstance().setMusicCurrent(App.getInstance().getListMusic().get(currentPos))
            binding.imPlaySong.setImageResource(R.drawable.ic_baseline_play_circle_filled_60)
            setImageSong(App.getInstance().getMusicCurrent().getMusicFile())
            startService(Constant.START_MEDIA_SERVICE)
        }
        binding.imPlayAgain.setOnClickListener { v ->
            if (!App.isLoop) {
                App.isLoop = true
                binding.imPlayAgain.setColorFilter(R.color.black)
            } else {
                binding.imPlayAgain.setColorFilter(null)
                App.isLoop = false
            }
        }
        binding.imDown.setOnClickListener { view ->
            requireActivity().getSupportFragmentManager().popBackStack()
        }
    }

    private fun setImageSong(path: String) {
        if (App.getInstance().getMusicCurrent().getImageSong() != null) {
            try {
                MediaMetadataRetriever().use { mmr ->
                    mmr.setDataSource(path)
                    val data: ByteArray = mmr.getEmbeddedPicture()
                    val bitmap: Bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
                    binding.imSong.setImageBitmap(bitmap)
                    Blurry.with(requireContext()).radius(16).from(bitmap).into(binding.imSong)
                    binding.imSongTop.setImageBitmap(bitmap)
                }
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        } else {
            Glide.with(requireContext()).asBitmap().load(R.drawable.bg_music)
                .listener(object : RequestListener<Bitmap?>() {
                    fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Bitmap?>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    fun onResourceReady(
                        resource: Bitmap?,
                        model: Any?,
                        target: Target<Bitmap?>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.imSong.setImageBitmap(resource)
                        Blurry.with(requireContext()).radius(16).from(resource).into(binding.imSong)
                        return true
                    }
                }).submit()
            binding.imSongTop.setImageResource(R.drawable.bg_music)
        }
    }

    private fun checkFavourite() {
        var isExit = false
        if (listFavourite.size > 0) {
            for (i in listFavourite.indices) {
                if (listFavourite[i].getMusicFile()
                        .equals(App.getInstance().getMusicCurrent().getMusicFile())
                ) {
                    isExit = true
                    break
                }
            }
        }
        if (isExit) {
            binding.imFavorite.setImageResource(R.drawable.ic_baseline_favorite_24_red)
        } else {
            binding.imFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    private fun initSeekBarChangeListener() {
        binding.sbTimeSong.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                binding.tvStartTime.setText(TimeUtils.getTimeDurationMusic(i))
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 1000)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                val intent = Intent(requireActivity(), YoutubeService::class.java)
                intent.setAction(SEEK_TO_MEDIA_SERVICE)
                Timber.e("nghialt: sbTimeSong")
                binding.sbTimeSong.setProgress(binding.sbTimeSong.getProgress())
                intent.putExtra("SEEK_TO", binding.sbTimeSong.getProgress())
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    requireActivity().startForegroundService(intent)
                } else {
                    requireActivity().startService(intent)
                }
            }
        })
    }

    fun onAttach(context: Context) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }

    fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }

    fun onDestroy() {
        super.onDestroy()
        Timber.e("nghialt: onDestroy")
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(messageEvent: MessageEvent) {
        when (messageEvent.getTypeEvent()) {
            Constant.CHANGE_MUSIC_CURRENT -> {
                binding.tvNameSong.setText(App.getInstance().getMusicCurrent().getMusicName())
                binding.tvNameSinger.setText(App.getInstance().getMusicCurrent().getNameSinger())
                setImageSong(App.getInstance().getMusicCurrent().getMusicFile())
                binding.tvEndTime.setText(TimeUtils.getTimeDurationMusic(messageEvent.getIntValue1()))
                binding.sbTimeSong.setMax(messageEvent.getIntValue1())
                binding.imPlaySong.setImageResource(R.drawable.ic_baseline_pause_circle_24)
                binding.tvStartTime.setText(TimeUtils.getTimeDurationMusic(messageEvent.getIntValue2()))
                binding.sbTimeSong.setProgress(0)
            }

            Constant.COMPLETE_PLAY_MUSIC -> {
                Timber.e("nghialt: COMPLETE_PLAY_MUSIC")
                binding.imPlaySong.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                var currentPos: Int
                currentPos = getPosCurrentMusic(App.getInstance().getMusicCurrent())
                if (!App.isLoop) {
                    currentPos++
                    if (currentPos > App.getInstance().getListMusic().size() - 1) {
                        currentPos = 0
                    }
                }
                App.getInstance().setMusicCurrent(App.getInstance().getListMusic().get(currentPos))
                startService(Constant.CHANGE_MUSIC_SERVICE)
            }

            Constant.START_MEDIA -> {
                binding.imPlaySong.setImageResource(R.drawable.ic_baseline_pause_circle_24)
                binding.sbTimeSong.setProgress(binding.sbTimeSong.getProgress() + 1)
                mainViewModel.isStartMedia.postValue(true)
            }

            Constant.FAVORITE_MEDIA -> {}
            Constant.STOP_MEDIA -> {
                if (messageEvent.isBooleanValue()) {
                    setImageSong(App.getInstance().getMusicCurrent().getMusicFile())
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
        val intent = Intent(requireActivity(), YoutubeService::class.java)
        intent.setAction(action)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requireActivity().startForegroundService(intent)
        } else {
            requireActivity().startService(intent)
        }
    }
}