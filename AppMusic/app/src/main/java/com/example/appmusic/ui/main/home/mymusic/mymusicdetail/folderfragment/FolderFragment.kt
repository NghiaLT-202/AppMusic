package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.folderfragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.FragmentFolderBinding
import com.example.appmusic.ui.adapter.FolderAdapter
import com.example.appmusic.ui.base.BaseBindingFragment

class FolderFragment : BaseBindingFragment<FragmentFolderBinding?, FolderViewModel>() {
    private val listFolfer: MutableList<Music?> = ArrayList()
    private var folderAdapter: FolderAdapter? = null
    override val layoutId: Int
        get() = R.layout.fragment_folder

    override fun getViewModel(): Class<FolderViewModel>? {
        return FolderViewModel::class.java
    }

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initData()
    }

    private fun initAdapter() {
        folderAdapter = FolderAdapter()
        binding!!.rcFolder.adapter = folderAdapter
        folderAdapter!!.setiBaseClickAdapter { position: Int ->
            Toast.makeText(
                requireContext(),
                R.string.hello,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun initData() {
        mainViewModel!!.listAllMusicDevice.observe(viewLifecycleOwner) { list: List<Music?>? ->
            listFolfer.clear()
            listFolfer.addAll(list!!)
            folderAdapter!!.setListFolder(listFolfer)
        }
    }
}