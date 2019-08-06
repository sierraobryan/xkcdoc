package com.example.sierraobryan.xkcdocument.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ComicWithTagFromDatabase(
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
        var tags: Map<String, String> = hashMapOf()
) : Serializable {

    fun convertFromDataBase(): ComicWithTag {
        val list = listOfValues(this.tags)
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
                list as MutableList<String>)
    }

    private fun listOfValues(hashMap: Map<String, String>) = ArrayList(hashMap.values)

}

