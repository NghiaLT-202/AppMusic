package com.example.appmusic.ui.main.home.singerfragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.appmusic.R
import com.example.appmusic.databinding.FragmentSingerBinding
import com.example.appmusic.ui.adapter.SingerAdapter
import com.example.appmusic.ui.base.BaseBindingFragment
import timber.log.Timber

class SingerFragment : BaseBindingFragment<FragmentSingerBinding, SingerViewModel>() {

    var singerAdapter: SingerAdapter? = null


    override fun getViewModel(): Class<SingerViewModel> {
        return SingerViewModel::class.java
    }

    override val layoutId: Int
        get() = R.layout.fragment_singer

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initData()

    }


    private fun initAdapter() {
        singerAdapter = SingerAdapter()
        binding.rcSinger.adapter = singerAdapter
//        singerAdapter?.setiBaseClickAdapter {
//
//        }
    }

    fun initData() {
        mainViewModel.getAllMusicDetail(requireContext())

        mainViewModel.listAllMusicDevice.observe(viewLifecycleOwner) {
            Timber.e("ltnghia"+it.size)
            Timber.e("ltnghia")
            singerAdapter?.lisSing=it
        }
    }

}