package com.example.appmusic.ui.main.home.video.videodetail

import android.view.View
import androidx.fragment.app.FragmentTransaction

class VideoDetailFragment :
    BaseBindingFragment<FragmentDetailMusicBinding?, VideoDetailViewModel?>() {
    var bottomsheetCommentFrag: BottomsheetCommentFrag? = null
    private var youTubePlayer: YouTubePlayer? = null
    private var videoRecent: VideoRecent? = null
    private val listener: AbstractYouTubePlayerListener = object : AbstractYouTubePlayerListener() {
        fun onReady(yt: YouTubePlayer) {
            youTubePlayer = yt
            viewModel.getVideoHome(videoRecent.getTitle())
            youTubePlayer.loadVideo(videoRecent.getVideoId(), 0)
        }
    }
    private var suggestionAdapter: ItemVideoAdapter? = null
    val layoutId: Int
        get() = R.layout.fragment_detail_music
    protected val viewModel: Class<VideoDetailViewModel>
        protected get() = VideoDetailViewModel::class.java

    protected fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        initAdapter()
        initListener()
        initData()
    }

    private fun initView() {
//        binding.tvComment.setText();
        binding.tvMusicTitle.setText(videoRecent.getTitle())
        binding.tvNameChanel.setText(videoRecent.getChannelTitle())
        binding.tvNameChanelBig.setText(videoRecent.getChannelTitle())
        //        Glide.with(binding.imImageUserComment).load((itemPlayVideo.getAuthorImageUrl())).into(binding.imImageUserComment);


//        binding.tvSubscriber.setText(ConvertTime.convertSub(videoYoutube.getItems().get(0)));
//        binding.tvSubscriber.setText(items.getStatistics().getSubscriberCount());
        with(getContext()).load(videoRecent.getUrl()).fit().into(binding.imImageChanel)
        with(getContext()).load(videoRecent.getAuthorImageUrl()).fit()
            .into(binding.imImageUserComment)
    }

    private fun showComment() {
        bottomsheetCommentFrag = BottomsheetCommentFrag()
        val transaction: FragmentTransaction = getChildFragmentManager().beginTransaction()
        transaction.replace(
            R.id.fragment_detail_music,
            bottomsheetCommentFrag,
            VideoDetailFragment::class.java.simpleName
        ).commit()
    }

    private fun initAdapter() {
        suggestionAdapter = ItemVideoAdapter()
        binding.rcListVideo.setAdapter(suggestionAdapter)
        suggestionAdapter.setIcLickItemSuggestionVideo { position, videoRecent ->
            binding.nestedScrollView.smoothScrollTo(0, 0)
            youTubePlayer.pause()
            this@VideoDetailFragment.videoRecent = videoRecent
            youTubePlayer.loadVideo(this@VideoDetailFragment.videoRecent.getVideoId(), 0)
            if (suggestionAdapter.getItemCount() < 3) {
                viewModel.getVideoHome(this@VideoDetailFragment.videoRecent.getChannelTitle() + this@VideoDetailFragment.videoRecent.getDescription())
            } else {
                viewModel.getVideoHome(this@VideoDetailFragment.videoRecent.getDescription())
            }
            initView()
        }
    }

    fun initData() {
        viewModel.videoTrendingLiveDataVN.observe(getViewLifecycleOwner()) { videoYoutubes ->
            if (videoYoutubes != null) {
                suggestionAdapter.setVideoYoutube(VideoRecent.getListVideoRecent(videoYoutubes))
                binding.loading.setVisibility(View.GONE)
            }
        }
        mainViewModel.itemPlayVideoMutableLiveData.observe(getViewLifecycleOwner()) { itemPlayVideos ->
            if (itemPlayVideos != null) {
                videoRecent = itemPlayVideos
                binding.youTubePlayerView.addYouTubePlayerListener(listener)
                mainViewModel.getComment(videoRecent.getVideoId())
                initView()
            }
        }
    }

    fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    private fun initListener() {
        binding.imImageChanel.setOnClickListener { v -> }

//        binding.imLike.setOnClickListener(v -> {
//            if (!checkLike) {
//                checkLike = true;
//                binding.imLike.setImageResource(R.drawable.like_72);
//            } else {
//                checkLike = false;
//                binding.imLike.setImageResource(R.drawable.like_white_72);
//            }
//
//        });
//        binding.imDisLike.setOnClickListener(v -> {
//            if (!checkDisLike) {
//                checkDisLike = true;
//                binding.imDisLike.setImageResource(R.drawable.dislike_72);
//            } else {
//                checkDisLike = false;
//                binding.imDisLike.setImageResource(R.drawable.dislike_white_72);
//            }
//
//        });
//        binding.imRepeatVideo.setOnClickListener(v -> {
//            if (!checkRepeat) {
//                checkRepeat = true;
//                binding.imRepeatVideo.setImageResource(R.drawable.repeat_one_black_72);
//
//            } else {
//                checkRepeat = false;
//                binding.imRepeatVideo.setImageResource(R.drawable.repeat_one_72);
//
//            }
//
//        });
        binding.tvContentComment.setOnClickListener { v ->
            val bundle = Bundle()
            bundle.putString("videoId", videoRecent.getVideoId())
            (requireActivity() as MainActivity).navController.navigate(
                R.id.fragment_commentYoutube,
                bundle
            )
        }
    }
}