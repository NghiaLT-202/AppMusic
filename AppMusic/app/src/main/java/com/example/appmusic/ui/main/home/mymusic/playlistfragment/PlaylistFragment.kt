package com.example.appmusic.ui.main.home.mymusic.playlistfragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.example.appmusic.R
import com.example.appmusic.common.Constant
import com.example.appmusic.data.model.Music
import com.example.appmusic.data.model.PlayList
import com.example.appmusic.databinding.FragmentPlayListMusicBinding
import com.example.appmusic.ui.adapter.PlaylistAdapter
import com.example.appmusic.ui.adapter.PlaylistAdapter.IclickMenu
import com.example.appmusic.ui.base.BaseBindingFragment
import com.example.appmusic.ui.dialog.OptionPlayListDialog
import com.example.appmusic.ui.dialog.OptionPlayListDialog.IOptionCollectionDialog
import com.example.appmusic.ui.main.MainActivity
import com.example.appmusic.ui.main.home.mymusic.playlistfragment.dialogFragmentAddPlayList.DialogAddPlayListFragment
import com.example.appmusic.ui.main.home.mymusic.playlistfragment.dialogFragmentAddPlayList.DialogAddPlayListFragment.IDialogAdd

class PlaylistFragment : BaseBindingFragment<FragmentPlayListMusicBinding?, PlaylistViewModel>() {
    private val listPlayList: MutableList<PlayList?> = ArrayList()
    private val listMusicPlaylist: MutableList<Music?> = ArrayList()
    private var playlistAdapter: PlaylistAdapter? = null
    private var dialogAddPlayListFragment: DialogAddPlayListFragment? = null
    override val layoutId: Int
        get() = R.layout.fragment_play_list_music

    override fun getViewModel(): Class<PlaylistViewModel>? {
        return PlaylistViewModel::class.java
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initlistener()
        initAdapter()
        intitData()
    }

    private fun initAdapter() {
        playlistAdapter = PlaylistAdapter()
        binding!!.rcPlayList.adapter = playlistAdapter
        playlistAdapter!!.setIclickMenu(object : IclickMenu {
            override fun clickMenu(
                location: IntArray,
                view: View?,
                playList: PlayList?,
                position: Int
            ) {
                val optionPlayListDialog = OptionPlayListDialog()
                optionPlayListDialog.setiOptionCollectionDialog(object : IOptionCollectionDialog {
                    override fun delete() {
                        viewModel!!.deletePlayList(playList.getNamePlayList())
                        listPlayList.remove(playList)
                        if (listPlayList.size < 1) {
                            binding!!.tvAddPlayList.visibility = View.VISIBLE
                            binding!!.tvPlayRandom.visibility = View.INVISIBLE
                            binding!!.imAddPlayList.visibility = View.INVISIBLE
                        }
                        playlistAdapter!!.setListPlayList(listPlayList)
                    }

                    override fun edit() {
                        showDialogEditPlayList(position)
                    }
                })
                optionPlayListDialog.showDialog(
                    location[0].toFloat(),
                    location[1].toFloat(),
                    childFragmentManager
                )
            }

            override fun clickItem(position: Int) {
                val bundle = Bundle()
                bundle.putString(Constant.NAME_PLAYLIST, listPlayList[position].getNamePlayList())
                (requireActivity() as MainActivity).navController!!.navigate(
                    R.id.fragment_detail_playlist,
                    bundle
                )
            }
        })
    }

    private fun intitData() {
        viewModel.getAllPlayList()
        viewModel!!.listPlayList.observe(viewLifecycleOwner) { playLists: List<PlayList?>? ->
            if (playLists != null) {
                listPlayList.clear()
                listPlayList.addAll(playLists)
                for (i in listPlayList.indices) {
                    viewModel!!.getAllMusicPlayList(listPlayList[i].getNamePlayList())
                }
                playlistAdapter!!.setListPlayList(listPlayList)
                if (listPlayList.size > 0) {
                    binding!!.tvAddPlayList.visibility = View.INVISIBLE
                    binding!!.tvPlayRandom.visibility = View.VISIBLE
                    binding!!.imAddPlayList.visibility = View.VISIBLE
                }
            }
        }
        viewModel!!.listMusicPlaylist.observe(viewLifecycleOwner) { music: List<Music?>? ->
            listMusicPlaylist.clear()
            listMusicPlaylist.addAll(music!!)
            playlistAdapter.setTotalSong(listMusicPlaylist.size)
        }
    }

    private fun initlistener() {
        binding!!.imBack.setOnClickListener { v: View? -> (requireActivity() as MainActivity).navController!!.popBackStack() }
        binding!!.tvAddPlayList.setOnClickListener { v: View? -> showDialogAddPlayList() }
        binding!!.imAddPlayList.setOnClickListener { v: View? -> showDialogAddPlayList() }
    }

    fun showDialogEditPlayList(position: Int) {
        dialogAddPlayListFragment = DialogAddPlayListFragment()
        dialogAddPlayListFragment!!.setText(getString(R.string.edit))
        dialogAddPlayListFragment!!.setTextEdit(listPlayList[position].getNamePlayList())
        dialogAddPlayListFragment!!.isCancelable = true
        dialogAddPlayListFragment!!.setiDialogAdd(object : IDialogAdd {
            override fun cancle(position: Int) {
                dialogAddPlayListFragment!!.dismiss()
            }

            override fun ok(inputName: String) {
                if (!TextUtils.isEmpty(inputName)) {
                    if (!checkNamePlayList(inputName)) {
                        viewModel!!.updateNamePlayList(
                            inputName,
                            listPlayList[position].getIdPlayList()
                        )
                        listPlayList[position].setNamePlayList(inputName)
                        if (listPlayList.size > 0) {
                            binding!!.tvAddPlayList.visibility = View.INVISIBLE
                        }
                        playlistAdapter!!.setListPlayList(listPlayList)
                        dialogAddPlayListFragment!!.dismiss()
                    } else {
                        toast(getString(R.string.listNameAlreadyExists))
                    }
                } else {
                    toast(getString(R.string.pleaseEnterListName))
                }
            }
        })
        dialogAddPlayListFragment!!.show(parentFragmentManager, null)
    }

    fun showDialogAddPlayList() {
        dialogAddPlayListFragment = DialogAddPlayListFragment()
        dialogAddPlayListFragment!!.isCancelable = true
        dialogAddPlayListFragment!!.setiDialogAdd(object : IDialogAdd {
            override fun cancle(position: Int) {
                dialogAddPlayListFragment!!.dismiss()
            }

            override fun ok(inputName: String) {
                if (!TextUtils.isEmpty(inputName)) {
                    val playList: PlayList
                    playList = PlayList(inputName, null, 0)
                    if (!checkNamePlayList(inputName)) {
                        viewModel!!.insertPlayList(playList)
                        dialogAddPlayListFragment!!.dismiss()
                        listPlayList.add(playList)
                        listPlayList.size
                        binding!!.tvAddPlayList.visibility = View.INVISIBLE
                        binding!!.tvPlayRandom.visibility = View.VISIBLE
                        binding!!.imAddPlayList.visibility = View.VISIBLE
                        playlistAdapter!!.setListPlayList(listPlayList)
                    } else {
                        toast(getString(R.string.listNameAlreadyExists))
                    }
                } else {
                    toast(getString(R.string.pleaseEnterListName))
                }
            }
        })
        dialogAddPlayListFragment!!.show(parentFragmentManager, null)
    }

    fun checkNamePlayList(inputName: String): Boolean {
        for (i in listPlayList.indices) {
            if (inputName == listPlayList[i].getNamePlayList()) {
                return true
            }
        }
        return false
    }
}