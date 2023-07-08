package com.example.appmusic.ui.main.home.mymusic.playlistfragment.detailPlayList

import android.view.View
import com.example.tfmmusic.App

class DetailPlayListFragment :
    BaseBindingFragment<FragmentDetailPlayListBinding?, DetailPlayListViewModel?>() {
    private val listMusic: MutableList<Music> = ArrayList<Music>()
    var musicAdapter: MusicAdapter? = null
    private var nameCurrentPlayList: String? = null
    protected val viewModel: Class<DetailPlayListViewModel>
        protected get() = DetailPlayListViewModel::class.java
    protected val layoutId: Int
        protected get() = R.layout.fragment_detail_play_list

    protected fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        if (getArguments() != null) {
            nameCurrentPlayList =
                getArguments().getString(Constant.NAME_PLAYLIST, nameCurrentPlayList)
        }
        initListener()
        initAdapter()
        initData()
    }

    private fun initListener() {
        binding.tvNamePlaylist.setText(nameCurrentPlayList)
        binding.imBack.setOnClickListener { v -> (requireActivity() as MainActivity).navController.popBackStack() }
    }

    private fun initAdapter() {
        musicAdapter = MusicAdapter()
        binding.rcListPlayList.setAdapter(musicAdapter)
        musicAdapter.setIclickMusic(object : IclickMusic() {
            fun clickItem(position: Int) {
                App.getInstance().setMusicCurrent(listMusic[position])
                (requireActivity() as MainActivity).navController.navigate(R.id.fragment_detail_music)
            }

            fun clickMenu(position: Int) {}
        })
    }

    private fun initData() {
        viewModel.getAllMusicPlayList(nameCurrentPlayList)
        viewModel.listSong.observe(getViewLifecycleOwner()) { list ->
            listMusic.clear()
            listMusic.addAll(list)
            musicAdapter.setArrayList(listMusic)
        }
    }
}