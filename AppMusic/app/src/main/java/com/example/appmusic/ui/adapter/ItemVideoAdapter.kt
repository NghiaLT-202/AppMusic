package com.example.appmusic.ui.adapter

import com.bumptech.glide.Glide

class ItemVideoAdapter : BaseBindingAdapter<ItemVideoBinding?>() {
    private var videoRecentList: List<VideoRecent> = ArrayList<VideoRecent>()
    private var icLickItemSuggestionVideo: ICLickItemSuggestionVideo? = null
    fun setIcLickItemSuggestionVideo(icLickItemSuggestionVideo: ICLickItemSuggestionVideo?) {
        this.icLickItemSuggestionVideo = icLickItemSuggestionVideo
    }

    fun setVideoYoutube(videoRecentList: List<VideoRecent>) {
        this.videoRecentList = videoRecentList
        notifyDataSetChanged()
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected fun onBindViewHolderBase(holder: BaseHolder<ItemVideoBinding?>, position: Int) {
        Glide.with(holder.itemView.getContext())
            .load(videoRecentList[holder.getAdapterPosition()].getUrl())
            .into(holder.binding.imgThumbnails)
        holder.itemView.setOnClickListener { v ->
            icLickItemSuggestionVideo!!.clickItem(
                position,
                videoRecentList[holder.getAdapterPosition()]
            )
        }
        holder.binding.tvTitleVideo.setText(videoRecentList[holder.getAdapterPosition()].getTitle())
        holder.binding.tvNameChanel.setText(videoRecentList[holder.getAdapterPosition()].getChannelTitle())
    }

    protected fun getLayoutIdItem(): Int {
        return R.layout.item_video
    }

    protected fun getSizeItem(): Int {
        return videoRecentList.size
    }

    interface ICLickItemSuggestionVideo {
        fun clickItem(position: Int, videoRecent: VideoRecent?)
    }
}