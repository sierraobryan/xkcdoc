package com.example.sierraobryan.xkcdocument.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Comic(
    val alt: String = "",
    val day: String = "",
    val img: String = "",
    val link: String = "",
    val month: String = "",
    val news: String = "",
    val num: Int = 0,
    @SerializedName("safe_title")
    val safeTitle: String = "",
    val title: String = "",
    val transcript: String = "",
    val year: String = "") : Serializable {

    fun toComicWithFavorite(isFavorite:Boolean) : ComicWithFavorite {
        return ComicWithFavorite(this.alt,
                this.day,
                this.img,
                this.link,
                this.month,
                this.news,
                this.num,
                this.safeTitle,
                this.title,
                this.transcript,
                this.year,
                isFavorite)
    }

    fun toComicWithFavorite() : ComicWithFavorite {
        return ComicWithFavorite(this.alt,
                this.day,
                this.img,
                this.link,
                this.month,
                this.news,
                this.num,
                this.safeTitle,
                this.title,
                this.transcript,
                this.year)
    }

    fun toComicWithTag(tags : List<String>) : ComicWithTag {
        return ComicWithTag(this.alt,
                this.day,
                this.img,
                this.link,
                this.month,
                this.news,
                this.num,
                this.safeTitle,
                this.title,
                this.transcript,
                this.year,
                tags as MutableList<String>)
    }
}