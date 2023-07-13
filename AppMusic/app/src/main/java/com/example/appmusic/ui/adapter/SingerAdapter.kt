package com.example.appmusic.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import com.example.appmusic.R
import com.example.appmusic.data.model.Music
import com.example.appmusic.databinding.ItemSingerBinding
import com.example.appmusic.ui.base.BaseBindingAdapter

class SingerAdapter : BaseBindingAdapter<ItemSingerBinding>() {
    private val lisSing = ArrayList<Music?>()
    @SuppressLint("NotifyDataSetChanged")
    fun setLisSing(lisSing: List<Music?>?) {
        this.lisSing.clear()
        this.lisSing.addAll(lisSing!!)
        notifyDataSetChanged()
    }

    override fun onBindViewHolderBase(holder: BaseHolder<ItemSingerBinding>, position: Int) {
        holder.binding.tvNameSinger.text = lisSing[position].getNameSinger()
        holder.binding.tvTotalSong.setText(lisSing.size + R.string.music)
        holder.itemView.setOnClickListener { view: View? -> iBaseClickAdapter!!.clickItem(holder.adapterPosition) }
    }

    protected override val layoutIdItem: Int
        protected get() = R.layout.item_singer
    protected override val sizeItem: Int
        protected get() = lisSing.size
}