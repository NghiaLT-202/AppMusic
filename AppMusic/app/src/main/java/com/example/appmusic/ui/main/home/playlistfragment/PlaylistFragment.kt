package com.example.appmusic.ui.main.home.playlistfragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.example.appmusic.R
import com.example.appmusic.common.Constant
import com.example.appmusic.data.model.Music
import com.example.appmusic.data.model.PlayList
import com.example.appmusic.databinding.FragmentPlayListMusicBinding
import com.example.appmusic.ui.adapter.PlaylistAdapter
import com.example.appmusic.ui.base.BaseBindingFragment
import com.example.appmusic.ui.dialog.OptionPlayListDialog
import com.example.appmusic.ui.main.MainActivity
import com.example.appmusic.ui.main.home.playlistfragment.dialogFragmentAddPlayList.DialogAddPlayListFragment

class PlaylistFragment : BaseBindingFragment<FragmentPlayListMusicBinding, PlaylistViewModel>() {
    private val listPlayList: MutableList<PlayList> = mutableListOf()
    private val listMusicPlaylist: MutableList<Music> = mutableListOf()
    private var playlistAdapter: PlaylistAdapter? = null
    private var dialogAddPlayListFragment: DialogAddPlayListFragment? = null
    override val layoutId: Int
        get() = R.layout.fragment_play_list_music

    override fun getViewModel(): Class<PlaylistViewModel> {
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
        binding.rcPlayList.adapter = playlistAdapter
        playlistAdapter?.setIclickMenu(object : PlaylistAdapter.IclickMenu {


            override fun clickMenu(
                location: IntArray,
                view: View,
                playList: PlayList,
                position: Int
            ) {
                val optionPlayListDialog = OptionPlayListDialog()
                optionPlayListDialog.setiOptionCollectionDialog(object :
                    OptionPlayListDialog.IOptionCollectionDialog {
                    override fun delete() {
                        playList.namePlayList.let { viewModel.deletePlayList(it) }
                        listPlayList.remove(playList)
                        if (listPlayList.size < 1) {
                            binding.tvAddPlayList.visibility = View.VISIBLE
                            binding.tvPlayRandom.visibility = View.INVISIBLE
                            binding.imAddPlayList.visibility = View.INVISIBLE
                        }
                        playlistAdapter?.listPlayList=(listPlayList)
                    }

                    override fun edit() {
                        showDialogEditPlayList(position)
                    }
                })
                optionPlayListDialog.showDialog(location[0].toFloat(),
                    location[1].toFloat(), childFragmentManager)            }

            override fun clickItem(position: Int) {
                val bundle = Bundle()
                bundle.putString(Constant.NAME_PLAYLIST, listPlayList[position].namePlayList)
                (requireActivity() as MainActivity).navController?.navigate(
                    R.id.fragment_detail_playlist,
                    bundle
                )
            }
        })
    }

    private fun intitData() {
        mainViewModel.getAllPlayList()
        mainViewModel.listPlaylist.observe(viewLifecycleOwner) { playLists ->
            if (playLists != null) {
                listPlayList.clear()
                listPlayList.addAll(playLists)
                for (i in listPlayList.indices) {
                    viewModel.getAllMusicPlayList(listPlayList[i].namePlayList)
                }
                playlistAdapter?.listPlayList=(listPlayList)
                if (listPlayList.size > 0) {
                    binding.tvAddPlayList.visibility = View.INVISIBLE
                    binding.tvPlayRandom.visibility = View.VISIBLE
                    binding.imAddPlayList.visibility = View.VISIBLE
                }
            }
        }
        viewModel.listMusicPlaylist.observe(viewLifecycleOwner) { music ->
            listMusicPlaylist.clear()
            listMusicPlaylist.addAll(music)
            playlistAdapter?.totalSong=listMusicPlaylist.size
        }
    }

    private fun initlistener() {
        binding.imBack.setOnClickListener { v -> (requireActivity() as MainActivity).navController?.popBackStack() }
        binding.tvAddPlayList.setOnClickListener { v -> showDialogAddPlayList() }
        binding.imAddPlayList.setOnClickListener { v -> showDialogAddPlayList() }
    }

    fun showDialogEditPlayList(position: Int) {
        dialogAddPlayListFragment = DialogAddPlayListFragment()
        dialogAddPlayListFragment?.text=(getString(R.string.edit))
        dialogAddPlayListFragment?.textEdit=(listPlayList[position].namePlayList)
        dialogAddPlayListFragment?.setCancelable(true)
        dialogAddPlayListFragment?.setiDialogAdd(object : DialogAddPlayListFragment.IDialogAdd {
            override fun cancle(position: Int) {
                dialogAddPlayListFragment?.dismiss()
            }


            override fun ok(inputName: String) {
                if (!TextUtils.isEmpty(inputName)) {
                    if (!checkNamePlayList(inputName)) {
                        viewModel.updateNamePlayList(
                            inputName,
                            listPlayList[position].idPlayList
                        )
                        listPlayList[position].namePlayList=(inputName)
                        if (listPlayList.size > 0) {
                            binding.tvAddPlayList.visibility = View.INVISIBLE
                        }
                        playlistAdapter?.listPlayList=(listPlayList)
                        dialogAddPlayListFragment?.dismiss()
                    } else {
                        toast(getString(R.string.listNameAlreadyExists))
                    }
                } else {
                    toast(getString(R.string.pleaseEnterListName))
                }
            }

        })
        dialogAddPlayListFragment?.show(parentFragmentManager, null)
    }

    fun showDialogAddPlayList() {
        dialogAddPlayListFragment = DialogAddPlayListFragment()
        dialogAddPlayListFragment?.setCancelable(true)
        dialogAddPlayListFragment?.setiDialogAdd(object : DialogAddPlayListFragment.IDialogAdd {
            override fun cancle(position: Int) {
                dialogAddPlayListFragment?.dismiss()
            }

            override fun ok(inputName: String) {
                if (!TextUtils.isEmpty(inputName)) {
                    PlayList().apply {
                        namePlayList= inputName
                        pathPlayList= ""
                        totalSong= 0
                        if (!checkNamePlayList(inputName)) {
                            viewModel.insertPlayList(this)
                            mainViewModel.listPlaylist.value?.add(this)
                            mainViewModel.listPlaylist.postValue(mainViewModel.listPlaylist.value)
                            dialogAddPlayListFragment?.dismiss()
                            listPlayList.add(this)
                            listPlayList.size
                            binding.tvAddPlayList.visibility = View.INVISIBLE
                            binding.tvPlayRandom.visibility = View.VISIBLE
                            binding.imAddPlayList.visibility = View.VISIBLE
                            playlistAdapter?.listPlayList=(listPlayList)
                        } else {
                            toast(getString(R.string.listNameAlreadyExists))
                        }
                    }

                } else {
                    toast(getString(R.string.pleaseEnterListName))
                }
            }
        })
        dialogAddPlayListFragment?.show(parentFragmentManager, null)
    }


    fun checkNamePlayList(inputName: String): Boolean {
        for (i in listPlayList.indices) {
            if (inputName == listPlayList[i].namePlayList) {
                return true
            }
        }
        return false
    }
}