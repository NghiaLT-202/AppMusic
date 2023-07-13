package com.example.appmusic.ui.adapter

import android.view.View
import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.ItemAlbumBinding
import com.example.appmusic.ui.base.BaseBindingAdapter

class AlbumAdapter : BaseBindingAdapter<ItemAlbumBinding>() {
    var list: MutableList<Music?> = ArrayList()
    protected override val layoutIdItem: Int
        protected get() = R.layout.item_album
    protected override val sizeItem: Int
        protected get() = list.size

    fun setList(list: List<Music?>?) {
        this.list.clear()
        this.list.addAll(list!!)
        notifyDataSetChanged()
    }

    override fun onBindViewHolderBase(holder: BaseHolder<ItemAlbumBinding>, position: Int) {
        if (list[holder.adapterPosition].getImageSong() != null) {
            holder.binding.imMusicSong.setImageBitmap(list[holder.adapterPosition].getImageSong())
        } else {
            holder.binding.imMusicSong.setImageResource(R.drawable.ic_apple_music)
        }
        holder.binding.tvNameAlbum.text = list[position].getNameAlbum()
        holder.binding.tvNameSinger.text = list[position].getNameSinger()
        holder.itemView.setOnClickListener { v: View? -> iBaseClickAdapter!!.clickItem(holder.adapterPosition) }
    }
}