package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.musicfragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.appmusic.App
import com.example.appmusic.R
import com.example.appmusic.common.Constant
import com.example.appmusic.common.MessageEvent
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.FragmentSongBinding
import com.example.appmusic.ui.adapter.MusicAdapter
import com.example.appmusic.ui.base.BaseBindingFragment
import com.example.appmusic.ui.main.MainActivity
import com.example.appmusic.ui.main.home.mymusic.mymusicdetail.musicfragment.bottomsheetsort.BottomSheetSortFragment
import com.example.appmusic.ui.main.home.mymusic.mymusicdetail.musicfragment.dialogfragment.BottomSheetListFuntionFrag
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.Collections
import java.util.Random

class MusicFragment : BaseBindingFragment<FragmentSongBinding?, MusicViewModel>() {
    private val musicList: MutableList<Music?> = ArrayList()
    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                mainViewModel.getAllMusicDetail(requireContext())
            }
        }
    var musicAdapter: MusicAdapter? = null
    override val layoutId: Int
        get() = R.layout.fragment_song

    override fun getViewModel(): Class<MusicViewModel>? {
        return MusicViewModel::class.java
    }

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (musicList.size == 0) {
                mainViewModel!!.getAllMusicDetail(context)
            }
        } else {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        initAdapter()
        initListener()
        initData()
    }

    private fun initListener() {
        binding!!.imSortSong.setOnClickListener { v: View? ->
            val bottomSheetSortFragment = BottomSheetSortFragment()
            bottomSheetSortFragment.musicList = musicList
            bottomSheetSortFragment.show(childFragmentManager, null)
            musicAdapter!!.setArrayList(bottomSheetSortFragment.musicList)
        }
        binding!!.imPlay.setOnClickListener {
            randomListMusic(musicList)
            App.Companion.getInstance().setListMusic(musicList)
            App.Companion.getInstance().setMusicCurrent(musicList[1])
            (requireActivity() as MainActivity).navController!!.navigate(
                R.id.fragment_detail_music,
                null
            )
        }
    }

    fun randomListMusic(musicList: MutableList<Music?>): List<Music?> {
        val rand = Random()
        for (i in musicList.indices) {
            val randomNum = rand.nextInt(musicList.size - 1)
            musicList[i] = musicList[randomNum]
        }
        return musicList
    }

    private fun initAdapter() {
        musicAdapter = MusicAdapter()
        binding!!.rcMusic.adapter = musicAdapter
        musicAdapter!!.setIclickMusic(object : MusicAdapter.IclickMusic {
            override fun clickItem(position: Int) {
                App.Companion.getInstance().setMusicCurrent(musicList[position])
                val bundle = Bundle()
                bundle.putBoolean(Constant.RUN_NEW_MUSIC, true)
                (requireActivity() as MainActivity).navController!!.navigate(
                    R.id.fragment_detail_music,
                    bundle
                )
            }

            override fun clickMenu(position: Int) {
                val bottomSheetFragment = BottomSheetListFuntionFrag()
                bottomSheetFragment.music = musicList[position]
                bottomSheetFragment.show(childFragmentManager, null)
            }
        })
    }

    private fun initData() {
        mainViewModel!!.listAllMusicDevice.observe(viewLifecycleOwner) { music: List<Music?>? ->
            if (music != null) {
                musicList.clear()
                musicList.addAll(music)
                musicAdapter!!.setArrayList(music)
                binding!!.loading.visibility = View.GONE
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(messageEvent: MessageEvent) {
        if (messageEvent.stringValue === Constant.SORT_NAME) {
            Collections.sort(musicList) { o1: Music?, o2: Music? ->
                o1.getMusicName()
                    .compareTo(o2.getMusicName())
            }
        }
        if (messageEvent.stringValue === Constant.SORT_TIME) {
            Collections.sort(musicList) { o1: Music?, o2: Music? ->
                o1.getDate()
                    .compareTo(o2.getDate())
            }
        }
        musicAdapter!!.setArrayList(musicList)
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }
}