package com.example.appmusic.utils

import java.text.SimpleDateFormat

object TimeUtils {
    fun getTimeDurationMusic(duration: Int): String {
        val simpleDateFormat = SimpleDateFormat("mm:ss")
        return simpleDateFormat.format(duration)
    }
}