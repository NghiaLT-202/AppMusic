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
import com.example.appmusic.ui.main.home.musicfragment.dialogfragment.BottomSheetListFuntionFrag
import java.util.Random

class MusicFragment : BaseBindingFragment<FragmentSongBinding, MusicViewModel>() {
    private val musicList: MutableList<Music> = mutableListOf()
    var musicAdapter: MusicAdapter? = null


    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                mainViewModel.getAllMusicDetail(requireContext())
            }
        }

    override fun getViewModel(): Class<MusicViewModel> {
        return MusicViewModel::class.java
    }

    override val layoutId: Int
        get() = R.layout.fragment_song


    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (musicList.size == 0) {
                mainViewModel.getAllMusicDetail(requireContext())
            }
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
                musicList.clear()
                musicList.addAll(music)
                musicAdapter?.arrayList = (music)
                binding.loading.visibility = View.GONE
            }
        }
    }

    private fun initListener() {
//        binding.imSortSong.setOnClickListener { v ->
//            val bottomSheetSortFragment = BottomSheetSortFragment()
//            bottomSheetSortFragment.setMusicList(musicList)
//            bottomSheetSortFragment.show(childFragmentManager, null)
//            musicAdapter.setArrayList(bottomSheetSortFragment.getMusicList())
//        }
//        binding.imPlay.setOnClickListener {
//            randomListMusic(musicList)
//            App.getInstance().setListMusic(musicList)
//            App.getInstance().setMusicCurrent(musicList[1])
//            (requireActivity() as MainActivity).navController.navigate(
//                R.id.fragment_detail_music,
//                null
//            )
//        }

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
        binding.rcMusic.adapter = musicAdapter
        musicAdapter?.setIclickMusic(object : MusicAdapter.IclickMusic {
            override fun clickItem(position: Int) {
                App.instance.musicCurrent = (musicList[position])
                val bundle = Bundle()
                bundle.putBoolean(Constant.RUN_NEW_MUSIC, true)
                (requireActivity() as MainActivity).navController?.navigate(
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

}