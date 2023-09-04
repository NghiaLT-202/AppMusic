package com.example.appmusic.ui.main.home.recentlyfragment

import androidx.lifecycle.viewModelScope
import com.example.appmusic.data.MusicRepository
import com.example.appmusic.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class RecentMyMusicVM
@Inject constructor(var musicRepository: MusicRepository) : BaseViewModel() {
    fun deleteRecentMusic() {
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler(fun(
                _: CoroutineContext, throwable: Throwable
        ) {
            run {
                Timber.e(throwable)
            }
        })) {
            musicRepository.deleteRecentMusic()
        }
    }
}