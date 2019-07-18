package com.example.sierraobryan.xkcdocument.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "fav_table")
data class ComicShort(
        @PrimaryKey var comicId: Int,
        var safeTitle: String
) : Serializable