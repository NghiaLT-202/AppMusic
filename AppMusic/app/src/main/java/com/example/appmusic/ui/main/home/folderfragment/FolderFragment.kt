package com.example.appmusic.ui.main.home.folderfragment

import android.os.Bundle
import android.view.View
import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.FragmentFolderBinding
import com.example.appmusic.ui.adapter.FolderAdapter
import com.example.appmusic.ui.base.BaseBindingFragment

class FolderFragment : BaseBindingFragment<FragmentFolderBinding, FolderViewModel>() {
    private var folderAdapter: FolderAdapter? = null


    override fun getViewModel(): Class<FolderViewModel> {
        return FolderViewModel::class.java
    }

    override val layoutId: Int
        get() = R.layout.fragment_folder

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initData()
    }

    private fun initAdapter() {
        folderAdapter = FolderAdapter()
        binding.rcFolder.adapter = folderAdapter
    }

    private fun initData() {
        mainViewModel.listAllMusicDevice.observe(viewLifecycleOwner) { list ->
            folderAdapter?.listFolder = (list)
        }
    }

}