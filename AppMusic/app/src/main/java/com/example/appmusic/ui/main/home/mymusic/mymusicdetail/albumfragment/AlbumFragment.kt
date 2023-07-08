package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.albumfragment

import android.view.View
import com.example.tfmmusic.R

class AlbumFragment : BaseBindingFragment<FragmentAlbumBinding?, AlbumViewModel?>() {
    private val listAlbum: MutableList<Music> = ArrayList<Music>()
    private var albumAdapter: AlbumAdapter? = null
    val layoutId: Int
        get() = R.layout.fragment_album
    protected val viewModel: Class<AlbumViewModel>
        protected get() = AlbumViewModel::class.java

    protected fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initData()
    }

    private fun initAdapter() {
        albumAdapter = AlbumAdapter()
        binding.rcAlbum.setAdapter(albumAdapter)
        albumAdapter.setiBaseClickAdapter(object : IBaseClickAdapter() {
            fun clickItem(position: Int) {
                Toast.makeText(requireContext(), R.string.hello, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initData() {
        mainViewModel.listAllMusicDevice.observe(getViewLifecycleOwner()) { songs ->
            if (songs != null) {
                listAlbum.clear()
                listAlbum.addAll(songs)
                albumAdapter.setList(songs)
            }
        }
    }
}