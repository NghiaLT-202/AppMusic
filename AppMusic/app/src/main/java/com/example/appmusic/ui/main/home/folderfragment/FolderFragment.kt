package com.example.appmusic.ui.main.home.folderfragment

import android.os.Bundle
import android.view.View
import com.example.appmusic.R
import com.example.appmusic.databinding.FragmentFolderBinding
import com.example.appmusic.ui.adapter.FolderAdapter
import com.example.appmusic.ui.base.BaseBindingFragment

class FolderFragment : BaseBindingFragment<FragmentFolderBinding, FolderViewModel>() {
    private val folderAdapter: FolderAdapter by lazy {
        FolderAdapter().apply {
            binding.rcFolder.adapter = this

        }
    }


    override fun getViewModel(): Class<FolderViewModel> {
        return FolderViewModel::class.java
    }

    override val layoutId: Int
        get() = R.layout.fragment_folder

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initData()
    }



    private fun initData() {
        mainViewModel.listAllDataMusicDevice.observe(viewLifecycleOwner) { list ->
            folderAdapter.listFolder = (list)
        }
    }

}