package com.example.appmusic.ui.main.home.albumfragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.appmusic.R
import com.example.appmusic.databinding.FragmentAlbumBinding
import com.example.appmusic.ui.adapter.AlbumAdapter
import com.example.appmusic.ui.base.BaseBindingAdapter
import com.example.appmusic.ui.base.BaseBindingFragment
import timber.log.Timber

class AlbumFragment : BaseBindingFragment<FragmentAlbumBinding, AlbumViewModel>() {
    private var albumAdapter: AlbumAdapter? = null


    override fun getViewModel(): Class<AlbumViewModel> {
        return AlbumViewModel::class.java
    }

    override val layoutId: Int
        get() = R.layout.fragment_album

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initData()
    }

    private fun initAdapter() {
        AlbumAdapter().apply {
            binding.rcAlbum.adapter = this
            this.setiBaseClickAdapter(object : BaseBindingAdapter.IBaseClickAdapter {
                override fun clickItem(position: Int) {
                    Toast.makeText(requireContext(), R.string.hello, Toast.LENGTH_SHORT).show()
                }
            })
        }

    }

    private fun initData() {
        mainViewModel.listAllMusicDevice.observe(viewLifecycleOwner) { songs ->
            if (songs != null) {
                albumAdapter?.list = (songs)
            }
        }
    }


}