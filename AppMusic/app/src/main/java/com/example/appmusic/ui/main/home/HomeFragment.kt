package com.example.appmusic.ui.main.home

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide

class HomeFragment : BaseBindingFragment<FragmentHomeBinding?, HomeViewModel?>() {
    val layoutId: Int
        get() = R.layout.fragment_home
    protected val viewModel: Class<HomeViewModel>
        protected get() = HomeViewModel::class.java

    protected fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        viewpager()
        initListener()
        initData()
    }

    fun onAttach(context: Context) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }

    fun onDetach() {
        super.onDetach()
        Timber.e("nghialt: onDetach")
        EventBus.getDefault().unregister(this)
    }

    private fun initListener() {
        if (App.getInstance().getMusicCurrent() != null) {
            binding.tvNameSong.setText(App.getInstance().getMusicCurrent().getMusicName())
            binding.tvNameSinger.setText(App.getInstance().getMusicCurrent().getNameSinger())
            setImageSong(App.getInstance().getMusicCurrent().getMusicFile())
            val objectAnimator: ObjectAnimator =
                ObjectAnimator.ofFloat(binding.imMusicSong, "rotation", 0f, 360f)
            objectAnimator.setDuration(8000) // Thời gian quay tròn trong 5 giây
            objectAnimator.setRepeatCount(ObjectAnimator.INFINITE) // Lặp vô hạn
            objectAnimator.setInterpolator(LinearInterpolator()) // Độ mượt của quay tròn
            objectAnimator.start()
        }
        binding.tvNameSong.setOnClickListener(View.OnClickListener {
            (requireActivity() as MainActivity).navController.navigate(
                R.id.fragment_detail_music
            )
        })
        binding.imPlaySong.setOnClickListener(View.OnClickListener { startService(Constant.STOP_MEDIA_SERVICE) })
        binding.imNextSong.setOnClickListener(View.OnClickListener {
            binding.imPlaySong.setImageResource(R.drawable.icons8_circled_play_72_black)
            var currentPos = getPosCurrentMusic(App.getInstance().getMusicCurrent())
            currentPos++
            if (currentPos > App.getInstance().getListMusic().size() - 1) {
                currentPos = 0
            }
            App.getInstance().setMusicCurrent(App.getInstance().getListMusic().get(currentPos))
            setImageSong(App.getInstance().getMusicCurrent().getMusicFile())
            binding.tvNameSong.setText(App.getInstance().getMusicCurrent().getMusicName())
            binding.tvNameSinger.setText(App.getInstance().getMusicCurrent().getNameSinger())
            startService(Constant.CHANGE_MUSIC_SERVICE)
        })
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(messageEvent: MessageEvent) {
        when (messageEvent.getTypeEvent()) {
            Constant.CHANGE_MUSIC_CURRENT -> binding.imPlaySong.setImageResource(R.drawable.icons8_pause_button_72_black)
            Constant.START_MEDIA -> binding.imPlaySong.setImageResource(R.drawable.icons8_pause_button_72_black)
            Constant.STOP_MEDIA -> binding.imPlaySong.setImageResource(R.drawable.icons8_circled_play_72_black)
        }
    }

    private fun initData() {
        mainViewModel.isStartMedia.observe(getViewLifecycleOwner(), Observer<Boolean?> { aBoolean ->
            if (aBoolean != null) {
                if (aBoolean) {
                    binding.imPlaySong.setImageResource(R.drawable.icons8_pause_button_72_black)
                } else {
                    binding.imPlaySong.setImageResource(R.drawable.icons8_circled_play_72_black)
                }
                mainViewModel.isStartMedia.postValue(null)
            }
        })
    }

    fun viewpager() {
        val menuBottomAdapter = MenuBottomAdapter(getChildFragmentManager(), getLifecycle())
        binding.viewpager2.setAdapter(menuBottomAdapter)
        binding.viewpager2.setOffscreenPageLimit(2)
        //tắt scroll viewpager
        binding.viewpager2.setUserInputEnabled(false)
        binding.btnMenu.setOnItemSelectedListener { item ->
            when (item.getItemId()) {
                R.id.fragment_myMusic -> binding.viewpager2.setCurrentItem(0)
                R.id.fragment_homeMusic -> binding.viewpager2.setCurrentItem(1)
            }
            true
        }
    }

    private fun setImageSong(path: String) {
        if (App.getInstance().getMusicCurrent().getImageSong() != null) {
            try {
                MediaMetadataRetriever().use { mmr ->
                    mmr.setDataSource(path)
                    val data: ByteArray = mmr.getEmbeddedPicture()
                    val bitmap: Bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
                    binding.imMusicSong.setImageBitmap(bitmap)
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
                        binding.imMusicSong.setImageBitmap(resource)
                        //                    Blurry.with(requireContext()).radius(16).from(resource).into(binding.imMusicSong);
                        return true
                    }
                }).submit()
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