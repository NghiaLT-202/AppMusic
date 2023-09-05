package com.example.appmusic.ui.main.home.singerfragment

import android.os.Bundle
import android.view.View
import com.example.appmusic.R
import com.example.appmusic.databinding.FragmentSingerBinding
import com.example.appmusic.ui.adapter.SingerAdapter
import com.example.appmusic.ui.base.BaseBindingFragment

class SingerFragment : BaseBindingFragment<FragmentSingerBinding, SingerViewModel>() {

    private val singerAdapter: SingerAdapter by lazy {
        SingerAdapter().apply {
            binding.rcSinger.adapter = this
        }
    }

    override fun getViewModel(): Class<SingerViewModel> {
        return SingerViewModel::class.java
    }

    override val layoutId: Int
        get() = R.layout.fragment_singer

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initData()

    }


    fun initData() {
        mainViewModel.listAllDataMusicDevice.observe(viewLifecycleOwner) {

            singerAdapter.listSing = it
        }
    }

}