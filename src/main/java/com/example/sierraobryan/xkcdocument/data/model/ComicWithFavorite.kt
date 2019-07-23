package com.example.sierraobryan.xkcdocument.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "fav_table")
data class ComicWithFavorite(
        val alt: String,
        val day: String,
        val img: String,
        val link: String,
        val month: String,
        val news: String,
        @PrimaryKey
        val num: Int,
        @SerializedName("safe_title")
        val safeTitle: String,
        val title: String,
        val transcript: String,
        val year: String,
        var isFavorite: Boolean = false
) : Serializable {
    var timeStamp = System.currentTimeMillis()

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
}