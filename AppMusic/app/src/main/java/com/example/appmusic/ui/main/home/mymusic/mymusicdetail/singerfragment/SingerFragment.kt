package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.singerfragment

import android.view.View
import com.example.tfmmusic.R

class SingerFragment : BaseBindingFragment<FragmentSingerBinding?, SingerViewModel?>() {
    var singerAdapter: SingerAdapter? = null
    val layoutId: Int
        get() = R.layout.fragment_singer
    protected val viewModel: Class<SingerViewModel>
        protected get() = SingerViewModel::class.java

    protected fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initData()
        initAdapter()
    }

    private fun initAdapter() {
        singerAdapter = SingerAdapter()
        binding.rcSinger.setAdapter(singerAdapter)
        singerAdapter.setiBaseClickAdapter { position ->
            Toast.makeText(
                requireContext(),
                "Hello",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun initData() {
        mainViewModel.listAllMusicDevice.observe(getViewLifecycleOwner()) { songs ->
            singerAdapter.setLisSing(
                songs
            )
        }
    }
}