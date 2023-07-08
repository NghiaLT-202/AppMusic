package com.example.appmusic.data.model.videos

class Items(var id: String, var key: String, var snippet: Snippet) {
    override fun toString(): String {
        return "Items{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", snippet=" + snippet.toString() +
                '}'
    }
}