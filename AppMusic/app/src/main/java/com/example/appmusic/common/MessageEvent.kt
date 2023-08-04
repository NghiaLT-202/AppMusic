package com.example.appmusic.common

import com.example.appmusic.data.model.Music
import com.example.appmusic.data.model.PlayList

class MessageEvent {
    var typeEvent = 0
        private set
    var stringValue = ""
        private set
    var intValue1 = 0
        private set
    var intValue2 = 0
        private set
    private var str: String? = null






    private var musicList: List<Music>? = null

    constructor(stringValue: String, musicList: List<Music>) {
        this.stringValue = stringValue
        this.musicList = musicList
    }

    var isBooleanValue = false

    constructor(typeEvent: Int, booleanValue: Boolean) {
        this.typeEvent = typeEvent
        isBooleanValue = booleanValue
    }

    constructor(typeEvent: Int, intValue1: Int, intValue2: Int) {
        this.typeEvent = typeEvent
        this.intValue1 = intValue1
        this.intValue2 = intValue2
    }

    constructor(str: String?) {
        this.str = str
    }

    constructor(typeEvent: Int) {
        this.typeEvent = typeEvent
    }
}