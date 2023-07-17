package com.example.appmusic.common

import com.example.appmusic.data.model.ItemRecent
import com.example.appmusic.data.model.Music

class CommonEvent {
    private val typeEvent: Int
    private var music: Music? = null
    private var itemRecent: ItemRecent? = null

    constructor(typeEvent: Int, itemRecent: ItemRecent?) {
        this.typeEvent = typeEvent
        this.itemRecent = itemRecent
    }

    constructor(typeEvent: Int, music: Music?) {
        this.typeEvent = typeEvent
        this.music = music
    }
}