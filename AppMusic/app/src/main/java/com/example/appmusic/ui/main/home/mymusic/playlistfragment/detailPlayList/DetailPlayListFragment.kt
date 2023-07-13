package com.example.appmusic.ui.main.home.mymusic.playlistfragment.detailPlayList

import android.os.Bundle
import android.view.View
import com.example.appmusic.App
import com.example.appmusic.R
import com.example.appmusic.common.Constant
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.FragmentDetailPlayListBinding
import com.example.appmusic.ui.adapter.MusicAdapter
import com.example.appmusic.ui.base.BaseBindingFragment
import com.example.appmusic.ui.main.MainActivity

class DetailPlayListFragment :
    BaseBindingFragment<FragmentDetailPlayListBinding?, DetailPlayListViewModel>() {
    private val listMusic: MutableList<Music?> = ArrayList()
    var musicAdapter: MusicAdapter? = null
    private var nameCurrentPlayList: String? = null
    override fun getViewModel(): Class<DetailPlayListViewModel>? {
        return DetailPlayListViewModel::class.java
    }

    protected override val layoutId: Int
        protected get() = R.layout.fragment_detail_play_list

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        if (arguments != null) {
            nameCurrentPlayList = arguments!!.getString(Constant.NAME_PLAYLIST, nameCurrentPlayList)
        }
        initListener()
        initAdapter()
        initData()
    }

    private fun initListener() {
        binding!!.tvNamePlaylist.text = nameCurrentPlayList
        binding!!.imBack.setOnClickListener { v: View? -> (requireActivity() as MainActivity).navController!!.popBackStack() }
    }

    private fun initAdapter() {
        musicAdapter = MusicAdapter()
        binding!!.rcListPlayList.adapter = musicAdapter
        musicAdapter!!.setIclickMusic(object : MusicAdapter.IclickMusic {
            override fun clickItem(position: Int) {
                App.Companion.getInstance().setMusicCurrent(listMusic[position])
                (requireActivity() as MainActivity).navController!!.navigate(R.id.fragment_detail_music)
            }

            override fun clickMenu(position: Int) {}
        })
    }

    private fun initData() {
        viewModel!!.getAllMusicPlayList(nameCurrentPlayList)
        viewModel!!.listSong.observe(viewLifecycleOwner) { list: List<Music?>? ->
            listMusic.clear()
            listMusic.addAll(list!!)
            musicAdapter!!.setArrayList(listMusic)
        }
    }
}