package com.example.appmusic.ui.adapter

import android.view.View
import com.example.tfmmusic.R

class PlaylistAdapter : BaseBindingAdapter<ItemPlayListBinding?>() {
    private val listPlayList: MutableList<PlayList> = ArrayList<PlayList>()
    private var iclickMenu: IclickMenu? = null
    private var totalSong = 0
    fun getTotalSong(): Int {
        return totalSong
    }

    fun setTotalSong(totalSong: Int) {
        this.totalSong = totalSong
    }

    fun setIclickMenu(iclickMenu: IclickMenu?) {
        this.iclickMenu = iclickMenu
    }

    fun setListPlayList(listPlayList: List<PlayList>?) {
        this.listPlayList.clear()
        this.listPlayList.addAll(listPlayList!!)
        notifyDataSetChanged()
    }

    protected fun onBindViewHolderBase(holder: BaseHolder<ItemPlayListBinding?>, position: Int) {
        holder.binding.imImagePlaylist.setImageResource(R.drawable.buoc_qua_nhau)
        holder.binding.tvNamePlayList.setText(listPlayList[position].getNamePlayList())
        //        totalSong=getTotalSong();
//        holder.binding.tvTotalSong.setText(totalSong);
        holder.binding.imMore.setOnClickListener(View.OnClickListener { v ->
            val location = IntArray(2)
            holder.binding.locationShowDialog.getLocationInWindow(location)
            iclickMenu!!.clickMenu(location, v, listPlayList[position], position)
        })
        holder.itemView.setOnClickListener(View.OnClickListener { iclickMenu!!.clickItem(holder.getAdapterPosition()) })
    }

    protected fun getLayoutIdItem(): Int {
        return R.layout.item_play_list
    }

    protected fun getSizeItem(): Int {
        return listPlayList.size
    }

    interface IclickMenu {
        fun clickMenu(location: IntArray?, view: View?, playList: PlayList?, position: Int)
        fun clickItem(position: Int)
    }
}