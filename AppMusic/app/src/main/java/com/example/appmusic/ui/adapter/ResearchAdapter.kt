package com.example.appmusic.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.ItemMusicBinding
import com.example.appmusic.ui.base.BaseBindingAdapter

class ResearchAdapter : BaseBindingAdapter<ItemMusicBinding>() {
    var arrayList: MutableList<Music> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    protected override fun onBindViewHolderBase(
        holder: BaseHolder<ItemMusicBinding>,
        position: Int
    ) {
        with(holder.binding) {
            tvNameSong.text = arrayList[position].musicName
            imMore.visibility = View.INVISIBLE
            tvNameSinger.text = arrayList[position].nameSinger
            if (arrayList[holder.adapterPosition].imageSong != null) {
                imMusicSong.setImageBitmap(arrayList[holder.adapterPosition].imageSong)
            } else {
                imMusicSong.setImageResource(R.drawable.ic_apple_music)
            }
            tvNameAlbum.text = arrayList[holder.adapterPosition].nameAlbum
        }

        holder.itemView.setOnClickListener { v: View? -> iBaseClickAdapter!!.clickItem(holder.adapterPosition) }
    }

    override val layoutIdItem: Int
        protected get() = R.layout.item_music
    override val sizeItem: Int
        protected get() = arrayList.size
}