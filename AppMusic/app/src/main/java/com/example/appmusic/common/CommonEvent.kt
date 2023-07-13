package com.example.appmusic.common

import com.example.appmusic.data.model.ItemRecent
import com.example.appmusic.data.model.Music

class CommonEvent {
    val typeEvent: Int
    var music: Music? = null
        private set
    var reccently: ItemRecent? = null
        private set

    constructor(typeEvent: Int, itemRecent: ItemRecent?) {
        this.typeEvent = typeEvent
        reccently = itemRecent
    }

    constructor(typeEvent: Int, music: Music?) {
        this.typeEvent = typeEvent
        this.music = music
    }
}