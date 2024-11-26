package com.example.musiqal.datamodels.youtubeApiSearchForPlayList

import com.google.gson.annotations.SerializedName


data class Item(
    val etag: String,
    @SerializedName("id")
    val id: Id,
    val kind: String,
    val snippet: Snippet,
    ) {


}
