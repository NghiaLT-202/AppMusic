package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.singerfragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.FragmentSingerBinding
import com.example.appmusic.ui.adapter.SingerAdapter
import com.example.appmusic.ui.base.BaseBindingFragment

class SingerFragment : BaseBindingFragment<FragmentSingerBinding?, SingerViewModel>() {
    var singerAdapter: SingerAdapter? = null
    override val layoutId: Int
        get() = R.layout.fragment_singer

    override fun getViewModel(): Class<SingerViewModel>? {
        return SingerViewModel::class.java
    }

    override fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initData()
        initAdapter()
    }

    private fun initAdapter() {
        singerAdapter = SingerAdapter()
        binding!!.rcSinger.adapter = singerAdapter
        singerAdapter!!.setiBaseClickAdapter { position: Int ->
            Toast.makeText(
                requireContext(),
                "Hello",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun initData() {
        mainViewModel!!.listAllMusicDevice.observe(viewLifecycleOwner) { songs: List<Music?>? ->
            singerAdapter!!.setLisSing(
                songs
            )
        }
    }
}