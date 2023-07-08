package com.example.appmusic.data.model.table

import com.example.tfmmusic.data.model.recent.Item
import java.nio.channels.Channel

class VideoChannel {
    private var item: Items? = null
    private var itemSearch: Item? = null
    var channel: Channel? = null

    constructor(item: Items?, itemSearch: Item?, channel: Channel?) {
        this.item = item
        this.itemSearch = itemSearch
        this.channel = channel
    }

    constructor(items: Items?, itemSearch: Any?, channel: Channel?) {}

    fun getItem(): Items? {
        return item
    }

    fun setItem(item: Items?) {
        this.item = item
    }

    fun getItemSearch(): Item? {
        return itemSearch
    }

    fun setItemSearch(itemSearch: Item?) {
        this.itemSearch = itemSearch
    }
}