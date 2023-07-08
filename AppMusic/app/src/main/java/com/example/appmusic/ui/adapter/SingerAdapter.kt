package com.example.appmusic.ui.adapter

import com.example.tfmmusic.R

class SingerAdapter : BaseBindingAdapter<ItemSingerBinding?>() {
    private val lisSing: ArrayList<Music> = ArrayList<Music>()
    @SuppressLint("NotifyDataSetChanged")
    fun setLisSing(lisSing: List<Music>?) {
        this.lisSing.clear()
        this.lisSing.addAll(lisSing!!)
        notifyDataSetChanged()
    }

    protected fun onBindViewHolderBase(holder: BaseHolder<ItemSingerBinding?>, position: Int) {
        holder.binding.tvNameSinger.setText(lisSing[position].getNameSinger())
        holder.binding.tvTotalSong.setText(lisSing.size + R.string.music)
        holder.itemView.setOnClickListener { view -> iBaseClickAdapter.clickItem(holder.getAdapterPosition()) }
    }

    protected fun getLayoutIdItem(): Int {
        return R.layout.item_singer
    }

    protected fun getSizeItem(): Int {
        return lisSing.size
    }
}