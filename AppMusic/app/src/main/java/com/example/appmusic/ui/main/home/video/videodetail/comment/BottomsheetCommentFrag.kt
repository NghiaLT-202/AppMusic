package com.example.appmusic.ui.main.home.video.videodetail.comment

import android.view.View
import com.example.tfmmusic.R

class BottomsheetCommentFrag :
    BaseBindingFragment<BottomsheetCommentFragBinding?, BottomsheetCommentVM?>() {
    var commentAdapter: CommentAdapter? = null
    private val items: Items? = null
    protected val viewModel: Class<BottomsheetCommentVM>
        protected get() = BottomsheetCommentVM::class.java
    protected val layoutId: Int
        protected get() = R.layout.bottomsheet_comment_frag

    protected fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initData()
        initListener()
    }

    private fun initListener() {
        binding.imClose.setOnClickListener { v -> (requireActivity() as MainActivity).navController.popBackStack() }
    }

    private fun initAdapter() {
        commentAdapter = CommentAdapter()
        binding.rcListComment.setAdapter(commentAdapter)
        val dividerItemDecoration =
            DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL)
        binding.rcListComment.addItemDecoration(dividerItemDecoration)
    }

    private fun initData() {

//        if (mainViewModel.liveEvent.getValue() != null) {
//            items = mainViewModel.liveEvent.getValue().getItems();
//            videoID = items.getId().getVideoId();
//        }
        mainViewModel.commentMutableLiveData.observe(getViewLifecycleOwner()) { comment ->
            if (comment != null && comment.getItems() != null) {
                commentAdapter.setCommentList(comment.getItems())
            }
        }
    }
}