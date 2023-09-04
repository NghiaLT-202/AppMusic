package com.example.appmusic.ui.main.home.playlistfragment.detailPlayList

import android.os.Bundle
import android.view.View
import com.example.appmusic.App
import com.example.appmusic.R
import com.example.appmusic.common.Constant
import com.example.appmusic.data.model.DataMusic
import com.example.appmusic.databinding.FragmentDetailPlayListBinding
import com.example.appmusic.ui.adapter.MusicAdapter
import com.example.appmusic.ui.base.BaseBindingFragment
import com.example.appmusic.ui.main.MainActivity

class DetailPlayListFragment :
    BaseBindingFragment<FragmentDetailPlayListBinding, DetailPlayListViewModel>() {
    private val musicAdapter: MusicAdapter by lazy {
        MusicAdapter().apply {
            clickItem = { _, item ->
                App.instance.musicCurrent = item
                Bundle().apply {
                    putBoolean(Constant.RUN_NEW_MUSIC, true)
                    (requireActivity() as MainActivity).navController.navigate(
                        R.id.fragment_detail_music,
                        this
                    )
                }
            }
            binding.rcListPlayList.adapter = this
        }
    }

    override fun getViewModel(): Class<DetailPlayListViewModel> {
        return DetailPlayListViewModel::class.java
    }

    override val layoutId: Int
        get() = R.layout.fragment_detail_play_list

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initListener()
        initData()
    }

    private fun initListener() {
        binding.imBack.setOnClickListener { (requireActivity() as MainActivity).navController.popBackStack() }
    }

    private fun initData() {
        arguments?.getString(Constant.NAME_PLAYLIST, null)?.let { nameCurrentPlayList ->
            viewModel.getAllMusicPlayList(nameCurrentPlayList)
            binding.tvNamePlaylist.text = nameCurrentPlayList
        }

        viewModel.listSong.observe(viewLifecycleOwner) { listDataMusic ->
            musicAdapter.submitList(listDataMusic)
        }
    }
}