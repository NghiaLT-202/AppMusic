package com.example.appmusic.ui.main.home.mymusic.mymusicdetail.folderfragment

import com.example.appmusic.data.repository.MusicRepository
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FolderViewModel @Inject constructor(private val musicRepository: MusicRepository) :
    BaseViewModel()