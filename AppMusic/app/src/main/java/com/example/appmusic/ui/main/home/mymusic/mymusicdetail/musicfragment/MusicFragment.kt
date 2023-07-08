package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.musicfragment

import android.Manifest
import android.content.Context
import android.view.View
import com.example.tfmmusic.App
import java.util.Collections
import java.util.Random

class MusicFragment : BaseBindingFragment<FragmentSongBinding?, MusicViewModel?>() {
    private val musicList: MutableList<Music> = ArrayList<Music>()
    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(RequestPermission()) { isGranted ->
            if (isGranted) {
                mainViewModel.getAllMusicDetail(getContext())
            }
        }
    var musicAdapter: MusicAdapter? = null
    val layoutId: Int
        get() = R.layout.fragment_song
    protected val viewModel: Class<MusicViewModel>
        protected get() = MusicViewModel::class.java

    protected fun onCreatedView(view: View?, savedInstanceState: Bundle?) {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (musicList.size == 0) {
                mainViewModel.getAllMusicDetail(getContext())
            }
        } else {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        initAdapter()
        initListener()
        initData()
    }

    private fun initListener() {
        binding.imSortSong.setOnClickListener { v ->
            val bottomSheetSortFragment = BottomSheetSortFragment()
            bottomSheetSortFragment.setMusicList(musicList)
            bottomSheetSortFragment.show(getChildFragmentManager(), null)
            musicAdapter.setArrayList(bottomSheetSortFragment.getMusicList())
        }
        binding.imPlay.setOnClickListener(View.OnClickListener {
            randomListMusic(musicList)
            App.getInstance().setListMusic(musicList)
            App.getInstance().setMusicCurrent(musicList[1])
            (requireActivity() as MainActivity).navController.navigate(
                R.id.fragment_detail_music,
                null
            )
        })
    }

    fun randomListMusic(musicList: MutableList<Music>): List<Music> {
        val rand = Random()
        for (i in musicList.indices) {
            val randomNum = rand.nextInt(musicList.size - 1)
            musicList[i] = musicList[randomNum]
        }
        return musicList
    }

    private fun initAdapter() {
        musicAdapter = MusicAdapter()
        binding.rcMusic.setAdapter(musicAdapter)
        musicAdapter.setIclickMusic(object : IclickMusic() {
            fun clickItem(position: Int) {
                App.getInstance().setMusicCurrent(musicList[position])
                val bundle = Bundle()
                bundle.putBoolean(Constant.RUN_NEW_MUSIC, true)
                (requireActivity() as MainActivity).navController.navigate(
                    R.id.fragment_detail_music,
                    bundle
                )
            }

            fun clickMenu(position: Int) {
                val bottomSheetFragment = BottomSheetListFuntionFrag()
                bottomSheetFragment.setMusic(musicList[position])
                bottomSheetFragment.show(getChildFragmentManager(), null)
            }
        })
    }

    private fun initData() {
        mainViewModel.listAllMusicDevice.observe(getViewLifecycleOwner()) { music ->
            if (music != null) {
                musicList.clear()
                musicList.addAll(music)
                musicAdapter.setArrayList(music)
                binding.loading.setVisibility(View.GONE)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(messageEvent: MessageEvent) {
        if (messageEvent.getStringValue() === Constant.SORT_NAME) {
            Collections.sort(musicList, java.util.Comparator<T> { o1: T, o2: T ->
                o1.getMusicName()
                    .compareTo(o2.getMusicName())
            })
        }
        if (messageEvent.getStringValue() === Constant.SORT_TIME) {
            Collections.sort(musicList, java.util.Comparator<T> { o1: T, o2: T ->
                o1.getDate()
                    .compareTo(o2.getDate())
            })
        }
        musicAdapter.setArrayList(musicList)
    }

    fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }

    fun onAttach(context: Context) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }
}