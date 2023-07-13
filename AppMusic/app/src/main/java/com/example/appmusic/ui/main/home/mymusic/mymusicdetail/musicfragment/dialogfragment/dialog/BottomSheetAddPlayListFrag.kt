package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.musicfragment.dialogfragment.dialog

import android.os.Bundleimport

android.view.Viewimport android.widget.Toastimport com.example.appmusic.Rimport com.example.appmusic.data .model.Musicimport com.example.appmusic.data .model.PlayListimport com.example.appmusic.databinding.BottomSheetAddPlaylistBindingimport com.example.appmusic.ui.adapter.ListPlayListAdapterimport com.example.appmusic.ui.base.BaseBottomSheetDialogFragmentimport com.example.appmusic.ui.main.MainActivityimport dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class BottomSheetAddPlayListFrag :
    BaseBottomSheetDialogFragment<BottomSheetAddPlaylistBinding?, BottomSheetAddPlayListVM>() {
    private var listPlayListAdapter: ListPlayListAdapter? = null
    private val listPlayList: MutableList<PlayList?> = ArrayList()
    private val listMusicPlayList: MutableList<Music?> = ArrayList()
    private var positionPlayList = 0
    private var musicCurent: Music? = null
    fun setMusicCurent(musicCurent: Music?) {
        this.musicCurent = musicCurent
    }

    override fun getViewModel(): Class<BottomSheetAddPlayListVM>? {
        return BottomSheetAddPlayListVM::class.java
    }

    override val layoutId: Int
        get() = R.layout.bottom_sheet_add_playlist

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
            viewModel!!.getAllMusicPlayList(listPlayList[position].getNamePlayList())
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
            listPlayListAdapter!!.setListPlay(listPlayList)
        }
        viewModel!!.listMusicPlaylist.observe(viewLifecycleOwner) { music: List<Music?>? ->
            listMusicPlayList.clear()
            listMusicPlayList.addAll(music!!)
            var check = false
            for (i in listMusicPlayList.indices) {
                if (musicCurent.getMusicName() == listMusicPlayList[i].getMusicName()) {
                    check = true
                    break
                }
            }
            if (!check) {
                musicCurent.setNamePlayList(listPlayList[positionPlayList].getNamePlayList())
                viewModel!!.inSertMusicofPlayList(musicCurent)
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "No add", Toast.LENGTH_SHORT).show()
            }
            dismiss()
        }
    }
}