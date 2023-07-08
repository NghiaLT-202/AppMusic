package com.example.appmusic.ui.adapter

import com.bumptech.glide.Glide
import java.util.LinkedList

class VideoAdapter : BaseBindingAdapter<ItemVideoYoutubeBinding?>() {
    var videoRecentList: List<VideoRecent> = LinkedList<VideoRecent>()
    private var iVideo: IVideo? = null
    fun setiVideo(iVideo: IVideo?) {
        this.iVideo = iVideo
    }

    fun setVideoYoutube(videoRecentList: List<VideoRecent>) {
        this.videoRecentList = videoRecentList
        notifyDataSetChanged()
    }

    protected fun onBindViewHolderBase(
        holder: BaseHolder<ItemVideoYoutubeBinding?>,
        position: Int
    ) {
        val videoRecent: VideoRecent = videoRecentList[holder.getAdapterPosition()]
        Glide.with(holder.itemView.getContext()).asBitmap().load(videoRecent.getUrl())
            .into(holder.binding.imgThumbnails)
        holder.binding.txtTitle.setText(videoRecent.getTitle())
        if (videoRecent.getDuration() != null) {
            Timber.e("nghialt: " + videoRecent.getDuration())
            holder.binding.textDuration.setText(
                ConvertTime.convertDuration(
                    ConvertTime.partStringDuration(
                        videoRecentList[holder.getAdapterPosition()].getDuration()
                    )
                )
            )
        }
        holder.binding.txtChannel.setText(videoRecent.getChannelTitle())
        holder.itemView.setOnClickListener { v ->
            iVideo!!.clickTitle(
                holder.getAdapterPosition(),
                videoRecent
            )
        }
        holder.binding.txtTitle.setOnClickListener { v ->
            iVideo!!.clickTitle(
                holder.getAdapterPosition(),
                videoRecent
            )
        }
    }

    protected val layoutIdItem: Int
        protected get() = R.layout.item_video_youtube

    protected fun getSizeItem(): Int {
        return videoRecentList.size
    }

    interface IVideo {
        fun clickTitle(position: Int, videoRecent: VideoRecent?)
    }
}