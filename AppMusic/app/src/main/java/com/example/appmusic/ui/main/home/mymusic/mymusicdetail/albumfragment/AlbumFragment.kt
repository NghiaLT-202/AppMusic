package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.albumfragment

import android.os.Bundleimport

android.view.Viewimport android.widget.Toastimport com.example.appmusic.Rimport com.example.appmusic.data .model.Musicimport com.example.appmusic.databinding.FragmentAlbumBindingimport com.example.appmusic.ui.adapter.AlbumAdapterimport com.example.appmusic.ui.base.BaseBindingFragment
class AlbumFragment : BaseBindingFragment<FragmentAlbumBinding?, AlbumViewModel>() {
    private val listAlbum: MutableList<Music?> = ArrayList()
    private var albumAdapter: AlbumAdapter? = null
    override val layoutId: Int
        get() = R.layout.fragment_album

    override fun getViewModel(): Class<AlbumViewModel>? {
        return AlbumViewModel::class.java
    }

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initData()
    }

    private fun initAdapter() {
        albumAdapter = AlbumAdapter()
        binding!!.rcAlbum.adapter = albumAdapter
        albumAdapter!!.setiBaseClickAdapter {
            Toast.makeText(
                requireContext(),
                R.string.hello,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun initData() {
        mainViewModel!!.listAllMusicDevice.observe(viewLifecycleOwner) { songs: List<Music?>? ->
            if (songs != null) {
                listAlbum.clear()
                listAlbum.addAll(songs)
                albumAdapter!!.setList(songs)
            }
        }
    }
}