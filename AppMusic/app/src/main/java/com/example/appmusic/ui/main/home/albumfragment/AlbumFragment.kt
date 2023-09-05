package com.example.appmusic.ui.main.home.albumfragment

import android.os.Bundle
import android.view.View
import com.example.appmusic.R
import com.example.appmusic.databinding.FragmentAlbumBinding
import com.example.appmusic.ui.adapter.AlbumAdapter
import com.example.appmusic.ui.base.BaseBindingFragment

class AlbumFragment : BaseBindingFragment<FragmentAlbumBinding, AlbumViewModel>() {
    private val albumAdapter: AlbumAdapter by lazy { AlbumAdapter().apply {
        binding.rcAlbum.adapter = this

    } }


    override fun getViewModel(): Class<AlbumViewModel> {
        return AlbumViewModel::class.java
    }

    override val layoutId: Int
        get() = R.layout.fragment_album

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initData()
    }



    private fun initData() {

        mainViewModel.listAllDataMusicDevice.observe(viewLifecycleOwner) { songs ->
            if (songs != null) {
                albumAdapter.list = (songs)
            }
        }
    }


}