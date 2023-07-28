package com.example.appmusic.ui.main.home.musicfragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.appmusic.App
import com.example.appmusic.R
import com.example.appmusic.common.Constant
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.FragmentSongBinding
import com.example.appmusic.ui.adapter.MusicAdapter
import com.example.appmusic.ui.base.BaseBindingFragment
import com.example.appmusic.ui.main.MainActivity
import com.example.appmusic.ui.main.home.musicfragment.bottomsheetsort.BottomSheetSortFragment
import com.example.appmusic.ui.main.home.musicfragment.dialogfragment.BottomSheetListFuntionFrag
import java.util.Random

class SongFragment : BaseBindingFragment<FragmentSongBinding, SongViewModel>() {
    var musicAdapter: MusicAdapter? = null
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
        mainViewModel.listAllMusicDevice.observe(viewLifecycleOwner) { music ->
            if (music != null) {
                App.instance.listMusic.clear()
                App.instance.listMusic.addAll(music)
                musicAdapter?.arrayList = (music)
                binding.loading.visibility = View.GONE
            }
        }
    }

    private fun initListener() {
        binding.imSortSong.setOnClickListener { v ->
            BottomSheetSortFragment().apply {
                musicList = (musicList)
                show(childFragmentManager, null)
                musicAdapter?.arrayList = musicList
            }
        }
        binding.imPlay.setOnClickListener {
            randomListMusic(App.instance.listMusic)
            App.instance.listMusic = (App.instance.listMusic)
            App.instance.musicCurrent = (App.instance.listMusic[1])
            navigateFragment(R.id.fragment_detail_music)
        }
    }

    fun randomListMusic(musicList: MutableList<Music>): MutableList<Music> {
        val rand = Random()
        for (i in musicList.indices) {
            val randomNum = rand.nextInt(musicList.size - 1)
            musicList[i] = musicList[randomNum]
        }
        return musicList
    }

    private fun initAdapter() {
        musicAdapter = MusicAdapter()
        binding.rcMusic.adapter = musicAdapter
        musicAdapter?.setIclickMusic(object : MusicAdapter.IclickMusic {

            override fun clickItem(position: Int, music: Music) {
                App.instance.musicCurrent = (music)
                Bundle().apply {
                    putBoolean(Constant.RUN_NEW_MUSIC, true)
                    (requireActivity() as MainActivity).navController?.navigate(
                        R.id.fragment_detail_music,
                        this
                    )
                }
            }

            override fun clickMenu(position: Int, itemMusic: Music) {
                BottomSheetListFuntionFrag().apply {
                    music = itemMusic
                    show(childFragmentManager, null)
                }
            }
        })


    }

}