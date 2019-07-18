package com.example.sierraobryan.xkcdocument.data.model

import java.io.Serializable

data class ComicTag(
        var comicId: Int = 0,
        var safeTitle: String = "",
        var tag: String = ""
) : Serializable