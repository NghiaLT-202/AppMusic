package com.example.appmusic.ui.adapter

import android.annotation.SuppressLint
import com.example.appmusic.R
import com.example.appmusic.data.model.DataMusic
import com.example.appmusic.databinding.ItemSingerBinding
import com.example.appmusic.ui.base.BaseBindingAdapter

class SingerAdapter : BaseBindingAdapter<ItemSingerBinding>() {
    var listSing: MutableList<DataMusic> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onBindViewHolderBase(holder: BaseHolder<ItemSingerBinding>, position: Int) {
        listSing[position].apply {
            with(holder.binding) {
                tvNameSinger.text = nameSinger
                tvTotalSong.setText(listSing.size + R.string.music)
                holder.itemView.setOnClickListener {
                    clickItem(holder.adapterPosition,this@apply)
                }
            }
        }


    }


    override val layoutIdItem: Int
         get() = R.layout.item_singer
    override val sizeItem: Int
         get() = listSing.size
}