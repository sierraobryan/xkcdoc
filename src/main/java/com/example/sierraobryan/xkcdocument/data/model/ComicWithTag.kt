package com.example.sierraobryan.xkcdocument.data.model

import java.io.Serializable

class ComicWithTag(
        var comicId: Int = 0,
        var safeTitle: String = "",
        var tag: String = ""
) : Serializable {

    fun toComicShort() : ComicShort {
        return ComicShort(this.comicId, this.safeTitle)
    }

}