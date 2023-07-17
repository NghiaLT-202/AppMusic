package com.example.appmusic.ui.main.home.albumfragment

import com.example.appmusic.data.MusicRepository
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor( val musicRepository: MusicRepository) :
    BaseViewModel()