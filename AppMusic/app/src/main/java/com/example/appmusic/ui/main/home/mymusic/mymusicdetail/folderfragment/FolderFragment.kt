package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.folderfragment

import android.view.View
import com.example.tfmmusic.R

class FolderFragment : BaseBindingFragment<FragmentFolderBinding?, FolderViewModel?>() {
    private val listFolfer: MutableList<Music> = ArrayList<Music>()
    private var folderAdapter: FolderAdapter? = null
    val layoutId: Int
        get() = R.layout.fragment_folder
    protected val viewModel: Class<FolderViewModel>
        protected get() = FolderViewModel::class.java

    protected fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initData()
    }

    private fun initAdapter() {
        folderAdapter = FolderAdapter()
        binding.rcFolder.setAdapter(folderAdapter)
        folderAdapter.setiBaseClickAdapter { position ->
            Toast.makeText(
                requireContext(),
                R.string.hello,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun initData() {
        mainViewModel.listAllMusicDevice.observe(getViewLifecycleOwner()) { list ->
            listFolfer.clear()
            listFolfer.addAll(list)
            folderAdapter.setListFolder(listFolfer)
        }
    }
}