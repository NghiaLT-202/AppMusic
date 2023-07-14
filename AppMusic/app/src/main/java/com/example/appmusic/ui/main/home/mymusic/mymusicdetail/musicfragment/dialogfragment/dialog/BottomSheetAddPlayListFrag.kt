package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.musicfragment.dialogfragment.dialog

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.data.model.PlayList
import com.example.appmusic.databinding.BottomSheetAddPlaylistBinding
import com.example.appmusic.ui.adapter.ListPlayListAdapter
import com.example.appmusic.ui.base.BaseBottomSheetDialogFragment
import com.example.appmusic.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BottomSheetAddPlayListFrag :
    BaseBottomSheetDialogFragment<BottomSheetAddPlaylistBinding?, BottomSheetAddPlayListVM>() {
    private var listPlayListAdapter: ListPlayListAdapter? = null
    private val listPlayList: MutableList<PlayList?> = mutableListOf()
    private val listMusicPlayList: MutableList<Music?> = mutableListOf()
    private var positionPlayList = 0
    private var musicCurent: Music? = null
    fun setMusicCurent(musicCurent: Music?) {
        this.musicCurent = musicCurent
    }

    override fun getViewModel(): Class<BottomSheetAddPlayListVM>? {
        return BottomSheetAddPlayListVM::class.java
    }

    override fun getLayoutId(): Int {
        return R.layout.bottom_sheet_add_playlist
    }


    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initListener()
        initData()
    }

    private fun initAdapter() {
        listPlayListAdapter = ListPlayListAdapter()
        binding!!.rcListCollection.adapter = listPlayListAdapter
        listPlayListAdapter!!.setiBaseClickAdapter { position: Int ->
            positionPlayList = position
            listPlayList[position]?.namePlayList?.let { viewModel!!.getAllMusicPlayList(it) }
        }
    }

    private fun initListener() {
        binding!!.framelayout.setOnClickListener { v: View? -> dismiss() }
        binding!!.tvConllectionNew.setOnClickListener { v: View? ->
            (requireActivity() as MainActivity).navController!!.navigate(
                R.id.fragment_list_music
            )
        }
    }

    private fun initData() {
        viewModel.getAllPlayList()
        viewModel!!.listPlaylist.observe(viewLifecycleOwner) { playLists: List<PlayList?>? ->
            listPlayList.clear()
            listPlayList.addAll(playLists!!)
            listPlayListAdapter?.listPlay = (listPlayList)
        }
        viewModel!!.listMusicPlaylist.observe(viewLifecycleOwner) { music: List<Music?>? ->
            listMusicPlayList.clear()
            listMusicPlayList.addAll(music!!)
            var check = false
            for (i in listMusicPlayList.indices) {
                if (musicCurent?.musicName == listMusicPlayList[i]?.musicName) {
                    check = true
                    break
                }
            }
            if (!check) {
                musicCurent?.namePlayList = listPlayList[positionPlayList]?.namePlayList
                viewModel!!.inSertMusicofPlayList(musicCurent)
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "No add", Toast.LENGTH_SHORT).show()
            }
            dismiss()
        }
    }
}