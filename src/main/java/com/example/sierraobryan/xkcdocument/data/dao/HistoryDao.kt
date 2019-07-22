package com.example.sierraobryan.xkcdocument.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.sierraobryan.xkcdocument.data.model.ComicShort

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(comicShort: ComicShort)

    @Query("SELECT * FROM fav_table")
    fun getAllHistory() : LiveData<List<ComicShort>>

    @Query("SELECT * FROM fav_table WHERE isFavorite = 1")
    fun getAllFavorites() : LiveData<List<ComicShort>>

    @Delete
    fun delete(comicShort: ComicShort)

}