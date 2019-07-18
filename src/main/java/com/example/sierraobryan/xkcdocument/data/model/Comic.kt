package com.example.sierraobryan.xkcdocument.data.model

import com.google.gson.annotations.SerializedName

class Comic(
    val alt: String,
    val day: String,
    val img: String,
    val link: String,
    val month: String,
    val news: String,
    val num: Int,
    @SerializedName("safe_title")
    val safeTitle: String,
    val title: String,
    val transcript: String,
    val year: String) {

    fun toComicShort() : ComicShort {
        return ComicShort(this.num, this.safeTitle)
    }
}