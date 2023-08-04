package com.example.appmusic.ui.main.home.musicfragment.dialogfragment.dialog

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.data.model.PlayList
import com.example.appmusic.databinding.BottomSheetAddPlaylistBinding
import com.example.appmusic.ui.adapter.ListPlayListAdapter
import com.example.appmusic.ui.base.BaseBindingAdapter.IBaseClickAdapter
import com.example.appmusic.ui.base.BaseBottomSheetDialogFragment
import com.example.appmusic.ui.main.MainActivity
import timber.log.Timber

class BottomSheetAddPlayListFrag :
    BaseBottomSheetDialogFragment<BottomSheetAddPlaylistBinding, BottomSheetAddPlayListVM>() {
    private var listPlayListAdapter: ListPlayListAdapter? = null
    private val listPlayList: MutableList<PlayList> = mutableListOf()
    private val listMusicPlayList: MutableList<Music> = mutableListOf()
    private var positionPlayList = 0
    var musicCurent: Music? = null
    override fun getViewModel(): Class<BottomSheetAddPlayListVM> {
        return BottomSheetAddPlayListVM::class.java
    }

    override val layoutId: Int
        get() = R.layout.bottom_sheet_add_playlist

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        Timber.e("ltnghia")

        initAdapter()
        initListener()
        initData()
    }

    private fun initAdapter() {
        listPlayListAdapter = ListPlayListAdapter()
        binding.rcListCollection.adapter = listPlayListAdapter
        listPlayListAdapter!!.setiBaseClickAdapter(object : IBaseClickAdapter {
            override fun clickItem(position: Int) {
                positionPlayList = position
                viewModel.getAllMusicPlayList(listPlayList[position].namePlayList)
            }
        })
    }

    private fun initListener() {

        binding.framelayout.setOnClickListener { dismiss() }
        binding.tvConllectionNew.setOnClickListener {
            (requireActivity() as MainActivity).navController?.navigate(
                R.id.fragment_list_music
            )
        }
    }

    private fun initData() {
        mainViewModel.getAllPlayList()
        mainViewModel.listPlaylist.observe(viewLifecycleOwner) { playLists ->
            listPlayList.clear()
            listPlayList.addAll(playLists)
            listPlayListAdapter!!.listPlay = (listPlayList)
        }
        viewModel.listMusicPlaylist.observe(viewLifecycleOwner) { music: MutableList<Music> ->
            listMusicPlayList.clear()
            listMusicPlayList.addAll(music)
            var check = false
            for (i in listMusicPlayList.indices) {
                if (musicCurent!!.musicName == listMusicPlayList[i].musicName) {
                    check = true
                    break
                }
            }
            if (!check) {
                musicCurent!!.namePlayList = listPlayList[positionPlayList].namePlayList
                viewModel.inSertMusicofPlayList(musicCurent!!)
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "No add", Toast.LENGTH_SHORT).show()
            }
            dismiss()
        }
    }
}