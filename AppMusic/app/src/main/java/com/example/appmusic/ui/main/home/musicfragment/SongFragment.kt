package com.example.appmusic.ui.main.home.musicfragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.appmusic.App
import com.example.appmusic.R
import com.example.appmusic.common.Constant
import com.example.appmusic.common.MessageEvent
import com.example.appmusic.data.model.DataMusic
import com.example.appmusic.databinding.FragmentSongBinding
import com.example.appmusic.ui.adapter.MusicAdapter
import com.example.appmusic.ui.base.BaseBindingFragment
import com.example.appmusic.ui.main.MainActivity
import com.example.appmusic.ui.main.home.musicfragment.bottomsheetsort.BottomSheetSortFragment
import com.example.appmusic.ui.main.home.musicfragment.dialogfragment.BottomSheetOptionsFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.Random

class SongFragment : BaseBindingFragment<FragmentSongBinding, SongViewModel>() {
    private val musicAdapter: MusicAdapter by lazy { MusicAdapter() }
    private val songList: MutableList<DataMusic> = mutableListOf()

    private val requestPermissionLauncher: ActivityResultLauncher<String> =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    mainViewModel.getAllMusicDetail(requireContext())
                }
            }

    override fun getViewModel(): Class<SongViewModel> {
        return SongViewModel::class.java
    }

    override val layoutId: Int
        get() = R.layout.fragment_song


    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
        ) {
            mainViewModel.getAllMusicDetail(requireContext())
        } else {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        initAdapter()
        initListener()
        initData()
    }

    private fun initData() {

        mainViewModel.listAllDataMusicDevice.observe(viewLifecycleOwner) { music ->
            if (music != null) {
                songList.clear()
                songList.addAll(music)
                musicAdapter.listDataMusic = (music)
                App.instance.listDataMusic = songList

                binding.loading.visibility = View.GONE
            }
        }
    }

    private fun initListener() {
        binding.imSortSong.setOnClickListener { v ->
            val bottomSheetSortFragment = BottomSheetSortFragment()
            bottomSheetSortFragment.dataMusicList = (songList)
            bottomSheetSortFragment.show(childFragmentManager, null)
            musicAdapter.listDataMusic = songList

        }
        binding.imPlay.setOnClickListener {
            randomListMusic(songList)
            App.instance.musicCurrent = (songList[1])
            Bundle().apply {
                putBoolean(Constant.RUN_NEW_MUSIC, true)
                navigateFragmentAndBundle(R.id.fragment_detail_music,this)

            }
        }
    }

    private fun randomListMusic(dataMusicList: MutableList<DataMusic>): MutableList<DataMusic> {
        val rand = Random()
        for (i in dataMusicList.indices) {
            val randomNum = rand.nextInt(dataMusicList.size - 1)
            dataMusicList[i] = dataMusicList[randomNum]
        }
        return dataMusicList
    }

    private fun initAdapter() {
        binding.rcMusic.adapter = musicAdapter
        musicAdapter.clickItem = { _, dataMusic ->
            App.instance.musicCurrent = (dataMusic)
            Bundle().apply {
                putBoolean(Constant.RUN_NEW_MUSIC, true)
                (requireActivity() as MainActivity).navController.navigate(
                        R.id.fragment_detail_music,
                        this
                )
            }
        }
        musicAdapter.clickMenu = { position, _ ->

            val bottomSheetFragment = BottomSheetOptionsFragment()

            bottomSheetFragment.dataMusic = (songList[position])
            bottomSheetFragment.show(childFragmentManager, null)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(messageEvent: MessageEvent) {
        if (messageEvent.stringValue == Constant.SORT_NAME) {
            songList.sortWith { o1, o2 -> o1.musicName.compareTo(o2.musicName) }

        }
        if (messageEvent.stringValue == Constant.SORT_TIME) {
            songList.sortWith { o1, o2 -> o1.date.compareTo(o2.date) }

        }
        musicAdapter.listDataMusic = (songList)
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