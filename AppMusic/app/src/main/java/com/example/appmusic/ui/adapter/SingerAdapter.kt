package com.example.appmusic.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.ItemSingerBinding
import com.example.appmusic.ui.base.BaseBindingAdapter

class SingerAdapter : BaseBindingAdapter<ItemSingerBinding>() {
     var lisSing: MutableList<Music> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolderBase(holder: BaseHolder<ItemSingerBinding>, position: Int) {
        holder.binding.tvNameSinger.text = lisSing[position]?.nameSinger
        holder.binding.tvTotalSong.setText(lisSing.size + R.string.music)
        holder.itemView.setOnClickListener { view: View? -> iBaseClickAdapter!!.clickItem(holder.adapterPosition) }
    }


    override fun getLayoutIdItem(): Int {
        return R.layout.item_singer
    }

    override fun getSizeItem(): Int {
        return lisSing.size
    }
}