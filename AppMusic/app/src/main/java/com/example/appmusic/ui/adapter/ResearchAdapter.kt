package com.example.appmusic.ui.adapter

import android.view.View
import com.example.tfmmusic.R

class ResearchAdapter : BaseBindingAdapter<ItemMusicBinding?>() {
    var arrayList: MutableList<Music> = ArrayList<Music>()
    @SuppressLint("NotifyDataSetChanged")
    fun setArrayList(arrayList: List<Music>?) {
        this.arrayList.clear()
        this.arrayList.addAll(arrayList!!)
        notifyDataSetChanged()
    }

    protected fun onBindViewHolderBase(holder: BaseHolder<ItemMusicBinding?>, position: Int) {
        holder.binding.tvNameSong.setText(arrayList[position].getMusicName())
        holder.binding.imMore.setVisibility(View.INVISIBLE)
        holder.binding.tvNameSinger.setText(arrayList[position].getNameSinger())
        if (arrayList[holder.getAdapterPosition()].getImageSong() != null) {
            holder.binding.imMusicSong.setImageBitmap(arrayList[holder.getAdapterPosition()].getImageSong())
        } else {
            holder.binding.imMusicSong.setImageResource(R.drawable.ic_apple_music)
        }
        holder.binding.tvNameAlbum.setText(arrayList[holder.getAdapterPosition()].getNameAlbum())
        holder.itemView.setOnClickListener { v -> iBaseClickAdapter.clickItem(holder.getAdapterPosition()) }
    }

    protected fun getLayoutIdItem(): Int {
        return R.layout.item_music
    }

    protected fun getSizeItem(): Int {
        return arrayList.size
    }
}