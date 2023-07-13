package com.example.appmusic.ui.main.home

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.LinearInterpolator
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
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.FragmentHomeBinding
import com.example.appmusic.service.MusicService
import com.example.appmusic.ui.adapter.MenuBottomAdapter
import com.example.appmusic.ui.base.BaseBindingFragment
import com.example.appmusic.ui.main.MainActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber

class HomeFragment : BaseBindingFragment<FragmentHomeBinding?, HomeViewModel>() {
    override val layoutId: Int
        get() = R.layout.fragment_home

    override fun getViewModel(): Class<HomeViewModel>? {
        return HomeViewModel::class.java
    }

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        Timber.e("nghialt: onCreatedView")
        viewpager()
        initListener()
        initData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.e("nghialt: DEstroyview")
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

    private fun initListener() {
        if (App.Companion.getInstance().getMusicCurrent() != null) {
            binding!!.tvNameSong.setText(
                App.Companion.getInstance().getMusicCurrent().getMusicName()
            )
            binding!!.tvNameSinger.setText(
                App.Companion.getInstance().getMusicCurrent().getNameSinger()
            )
            setImageSong(App.Companion.getInstance().getMusicCurrent().getMusicFile())
            val objectAnimator = ObjectAnimator.ofFloat(binding!!.imMusicSong, "rotation", 0f, 360f)
            objectAnimator.duration = 8000 // Thời gian quay tròn trong 5 giây
            objectAnimator.repeatCount = ObjectAnimator.INFINITE // Lặp vô hạn
            objectAnimator.interpolator = LinearInterpolator() // Độ mượt của quay tròn
            objectAnimator.start()
        }
        binding!!.tvNameSong.setOnClickListener {
            (requireActivity() as MainActivity).navController!!.navigate(
                R.id.fragment_detail_music
            )
        }
        binding!!.imPlaySong.setOnClickListener { startService(Constant.STOP_MEDIA_SERVICE) }
        binding!!.imNextSong.setOnClickListener {
            binding!!.imPlaySong.setImageResource(R.drawable.icons8_circled_play_72_black)
            var currentPos = getPosCurrentMusic(App.Companion.getInstance().getMusicCurrent())
            currentPos++
            if (currentPos > App.Companion.getInstance().getListMusic().size - 1) {
                currentPos = 0
            }
            App.Companion.getInstance()
                .setMusicCurrent(App.Companion.getInstance().getListMusic().get(currentPos))
            setImageSong(App.Companion.getInstance().getMusicCurrent().getMusicFile())
            binding!!.tvNameSong.setText(
                App.Companion.getInstance().getMusicCurrent().getMusicName()
            )
            binding!!.tvNameSinger.setText(
                App.Companion.getInstance().getMusicCurrent().getNameSinger()
            )
            startService(Constant.CHANGE_MUSIC_SERVICE)
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(messageEvent: MessageEvent) {
        when (messageEvent.typeEvent) {
            Constant.CHANGE_MUSIC_CURRENT -> binding!!.imPlaySong.setImageResource(R.drawable.icons8_pause_button_72_black)
            Constant.START_MEDIA -> {
                Timber.e("tunglt: START_MEDIA")
                binding!!.imPlaySong.setImageResource(R.drawable.icons8_pause_button_72_black)
            }

            Constant.STOP_MEDIA -> {
                Timber.e("nghialt: STOP_MEDIA")
                binding!!.imPlaySong.setImageResource(R.drawable.icons8_circled_play_72_black)
            }
        }
    }

    private fun initData() {
        mainViewModel!!.isStartMedia.observe(viewLifecycleOwner) { aBoolean ->
            if (aBoolean != null) {
                if (aBoolean) {
                    binding!!.imPlaySong.setImageResource(R.drawable.icons8_pause_button_72_black)
                } else {
                    binding!!.imPlaySong.setImageResource(R.drawable.icons8_circled_play_72_black)
                }
                mainViewModel!!.isStartMedia.postValue(null)
            }
        }
    }

    fun viewpager() {
        val menuBottomAdapter = MenuBottomAdapter(childFragmentManager, lifecycle)
        binding!!.viewpager2.adapter = menuBottomAdapter
        binding!!.viewpager2.offscreenPageLimit = 2
        //tắt scroll viewpager
        binding!!.viewpager2.isUserInputEnabled = false
        binding!!.btnMenu.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.fragment_myMusic -> binding!!.viewpager2.currentItem = 0
                R.id.fragment_homeMusic -> binding!!.viewpager2.currentItem = 1
            }
            true
        }
    }

    private fun setImageSong(path: String?) {
        if (App.Companion.getInstance().getMusicCurrent().getImageSong() != null) {
            try {
                MediaMetadataRetriever().use { mmr ->
                    mmr.setDataSource(path)
                    val data = mmr.embeddedPicture
                    val bitmap = BitmapFactory.decodeByteArray(data, 0, data!!.size)
                    binding!!.imMusicSong.setImageBitmap(bitmap)
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
                        binding!!.imMusicSong.setImageBitmap(resource)
                        //                    Blurry.with(requireContext()).radius(16).from(resource).into(binding.imMusicSong);
                        return true
                    }
                }).submit()
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