package com.example.appmusic.ui.adapter

import com.bumptech.glide.Glide

class VideoRecentAdapter : BaseBindingAdapter<ItemVideoYoutubeBinding?>() {
    private val videoRecentList: MutableList<VideoRecent> = ArrayList<VideoRecent>()
    private var iVideo: IVideo? = null
    fun setVideoRecentList(videoRecentList: List<VideoRecent>?) {
        this.videoRecentList.clear()
        this.videoRecentList.addAll(videoRecentList!!)
        notifyDataSetChanged()
    }

    fun setiVideo(iVideo: IVideo?) {
        this.iVideo = iVideo
    }

    protected fun onBindViewHolderBase(
        holder: BaseHolder<ItemVideoYoutubeBinding?>,
        position: Int
    ) {
        val videoRecent: VideoRecent = videoRecentList[holder.getAdapterPosition()]
        holder.binding.txtTitle.setText(videoRecent.getTitle())
        holder.binding.txtChannel.setText(videoRecent.getChannelTitle())
        holder.itemView.setOnClickListener { v -> iVideo!!.clickTitle(holder.getAdapterPosition()) }
        holder.binding.txtTitle.setOnClickListener { v -> iVideo!!.clickTitle(holder.getAdapterPosition()) }
        Glide.with(holder.itemView.getContext()).asBitmap().load(videoRecent.getUrl())
            .into(holder.binding.imgThumbnails)
        if (videoRecent.getDuration() != null) {
            holder.binding.textDuration.setText(
                ConvertTime.convertDuration(
                    ConvertTime.partStringDuration(
                        videoRecent.getDuration()
                    )
                )
            )
        }
        holder.itemView.setOnClickListener { v ->
            if (videoRecentList.size != 0) {
                iVideo!!.clickTitle(position)
            }
        }
    }

    protected fun getLayoutIdItem(): Int {
        return R.layout.item_video_youtube
    }

    protected fun getSizeItem(): Int {
        return videoRecentList.size
    }

    interface IVideo {
        fun clickTitle(position: Int)
    }
}