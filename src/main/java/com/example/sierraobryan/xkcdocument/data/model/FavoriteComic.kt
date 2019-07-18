package com.example.sierraobryan.xkcdocument.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_table")
data class FavoriteComic(
        @PrimaryKey var comicId: Int,
        var title: String
)