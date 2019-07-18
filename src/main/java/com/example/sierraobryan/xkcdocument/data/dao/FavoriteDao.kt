package com.example.sierraobryan.xkcdocument.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.sierraobryan.xkcdocument.data.model.ComicShort

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(comicShort: ComicShort)

    @Query("SELECT * FROM fav_table")
    fun getAllFavorites() : LiveData<List<ComicShort>>

    @Delete
    fun delete(comicShort: ComicShort)

}