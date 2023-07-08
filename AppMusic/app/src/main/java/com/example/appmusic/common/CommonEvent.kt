package com.example.appmusic.common

import com.example.tfmmusic.data.model.ItemRecent

class CommonEvent {
    val typeEvent: Int
    private var music: Music? = null
    private var itemRecent: ItemRecent? = null

    constructor(typeEvent: Int, itemRecent: ItemRecent?) {
        this.typeEvent = typeEvent
        this.itemRecent = itemRecent
    }

    val reccently: ItemRecent?
        get() = itemRecent

    fun getMusic(): Music? {
        return music
    }

    constructor(typeEvent: Int, music: Music?) {
        this.typeEvent = typeEvent
        this.music = music
    }
}