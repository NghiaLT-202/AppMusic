package com.example.appmusic.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.ItemMusicBinding
import com.example.appmusic.ui.base.BaseBindingAdapter

class ResearchAdapter : BaseBindingAdapter<ItemMusicBinding>() {
    var arrayList: MutableList<Music?> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolderBase(holder: BaseHolder<ItemMusicBinding>, position: Int) {
        holder.binding.tvNameSong.text = arrayList[position]?.musicName
        holder.binding.imMore.visibility = View.INVISIBLE
        holder.binding.tvNameSinger.text = arrayList[position]?.nameSinger
        if (arrayList[holder.adapterPosition]?.imageSong != null) {
            holder.binding.imMusicSong.setImageBitmap(arrayList[holder.adapterPosition]?.imageSong)
        } else {
            holder.binding.imMusicSong.setImageResource(R.drawable.ic_apple_music)
        }
        holder.binding.tvNameAlbum.text = arrayList[holder.adapterPosition]?.nameAlbum
        holder.itemView.setOnClickListener { v: View? -> iBaseClickAdapter!!.clickItem(holder.adapterPosition) }
    }


    override fun getLayoutIdItem(): Int {
        return R.layout.item_music
    }

    override fun getSizeItem(): Int {
        return arrayList.size
    }
}