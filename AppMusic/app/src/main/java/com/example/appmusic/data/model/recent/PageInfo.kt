package com.example.appmusic.data.model.recent

import com.google.gson.annotations.Expose

class PageInfo {
    @SerializedName("totalResults")
    @Expose
    var totalResults: Int? = null

    @SerializedName("resultsPerPage")
    @Expose
    var resultsPerPage: Int? = null
}