package com.example.appmusic.ui.adapter

import android.annotation.SuppressLint
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
        with(holder.binding) {
            tvNameSinger.text = lisSing[position].nameSinger
            tvTotalSong.setText(lisSing.size + R.string.music)
        }
        holder.itemView.setOnClickListener {
            iBaseClickAdapter?.clickItem(holder.adapterPosition)
        }
    }


    override val layoutIdItem: Int
         get() = R.layout.item_singer
    override val sizeItem: Int
         get() = lisSing.size
}