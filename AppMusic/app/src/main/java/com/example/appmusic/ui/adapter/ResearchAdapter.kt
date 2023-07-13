package com.example.appmusic.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.ItemMusicBinding
import com.example.appmusic.ui.base.BaseBindingAdapter

class ResearchAdapter : BaseBindingAdapter<ItemMusicBinding>() {
    var arrayList: MutableList<Music?> = ArrayList()
    @SuppressLint("NotifyDataSetChanged")
    fun setArrayList(arrayList: List<Music?>?) {
        this.arrayList.clear()
        this.arrayList.addAll(arrayList!!)
        notifyDataSetChanged()
    }

    override fun onBindViewHolderBase(holder: BaseHolder<ItemMusicBinding>, position: Int) {
        holder.binding.tvNameSong.text = arrayList[position].getMusicName()
        holder.binding.imMore.visibility = View.INVISIBLE
        holder.binding.tvNameSinger.text = arrayList[position].getNameSinger()
        if (arrayList[holder.adapterPosition].getImageSong() != null) {
            holder.binding.imMusicSong.setImageBitmap(arrayList[holder.adapterPosition].getImageSong())
        } else {
            holder.binding.imMusicSong.setImageResource(R.drawable.ic_apple_music)
        }
        holder.binding.tvNameAlbum.text = arrayList[holder.adapterPosition].getNameAlbum()
        holder.itemView.setOnClickListener { v: View? -> iBaseClickAdapter!!.clickItem(holder.adapterPosition) }
    }

    protected override val layoutIdItem: Int
        protected get() = R.layout.item_music
    protected override val sizeItem: Int
        protected get() = arrayList.size
}