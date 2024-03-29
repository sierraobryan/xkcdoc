package com.example.sierraobryan.xkcdocument.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ComicWithTag(
        val alt: String = "",
        val day: String = "",
        val img: String = "",
        val link: String = "",
        val month: String = "",
        val news: String = "",
        val num: Int = 0,
        @SerializedName("safe_title")
        val safeTitle: String  = "",
        val title: String  = "",
        val transcript: String  = "",
        val year: String = "",
        var tags: MutableList<String> = mutableListOf<String>()
) : Serializable {

    fun toComic() : Comic {
        return Comic(this.alt,
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

    fun convertToDataBase(): ComicWithTagFromDatabase {
        return ComicWithTagFromDatabase(this.alt,
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
                tags.map { it to it }.toMap() )
    }

    fun addTag(tag: String) {
        this.tags.add(tag)
    }

}