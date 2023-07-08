package com.example.appmusic.ui.adapter

import com.bumptech.glide.Glide

class CommentAdapter : BaseBindingAdapter<ItemCommentBinding?>() {
    private var commentList: List<ItemComment>? = ArrayList<ItemComment>()
    fun setCommentList(commentList: List<ItemComment>?) {
        this.commentList = commentList
        notifyDataSetChanged()
    }

    protected fun onBindViewHolderBase(holder: BaseHolder<ItemCommentBinding?>, position: Int) {
        Glide.with(holder.binding.imImageAccount).load(
            commentList!![holder.getAdapterPosition()].getSnippet().getTopLevelComment()
                .getSnippet().getAuthorProfileImageUrl()
        ).into(holder.binding.imImageAccount)
        holder.binding.tvContentComment.setText(
            commentList!![holder.getAdapterPosition()].getSnippet().getTopLevelComment()
                .getSnippet().getTextOriginal()
        )
        holder.binding.tvNameAccount.setText(
            commentList!![holder.getAdapterPosition()].getSnippet().getTopLevelComment()
                .getSnippet().getAuthorDisplayName()
        )
        holder.binding.tvLikeCommentCount.setText(
            commentList!![holder.getAdapterPosition()].getSnippet().getTopLevelComment()
                .getSnippet().getLikeCount() + ""
        )
        holder.binding.tvTimeComment.setText(
            ConvertTime.convertTime(
                commentList!![holder.getAdapterPosition()].getSnippet().getTopLevelComment()
                    .getSnippet().getUpdatedAt()
            )
        )
    }

    protected fun getLayoutIdItem(): Int {
        return R.layout.item_comment
    }

    protected fun getSizeItem(): Int {
        return if (commentList != null) commentList!!.size else 0
    }
}