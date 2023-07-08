package com.example.appmusic.ui.adapter

import com.example.tfmmusic.R

class AlbumAdapter : BaseBindingAdapter<ItemAlbumBinding?>() {
    var list: MutableList<Music> = ArrayList<Music>()
    protected val layoutIdItem: Int
        protected get() = R.layout.item_album
    protected val sizeItem: Int
        protected get() = list.size

    fun setList(list: List<Music>?) {
        this.list.clear()
        this.list.addAll(list!!)
        notifyDataSetChanged()
    }

    protected fun onBindViewHolderBase(holder: BaseHolder<ItemAlbumBinding?>, position: Int) {
        if (list[holder.getAdapterPosition()].getImageSong() != null) {
            holder.binding.imMusicSong.setImageBitmap(list[holder.getAdapterPosition()].getImageSong())
        } else {
            holder.binding.imMusicSong.setImageResource(R.drawable.ic_apple_music)
        }
        holder.binding.tvNameAlbum.setText(list[position].getNameAlbum())
        holder.binding.tvNameSinger.setText(list[position].getNameSinger())
        holder.itemView.setOnClickListener { v -> iBaseClickAdapter.clickItem(holder.getAdapterPosition()) }
    }
}