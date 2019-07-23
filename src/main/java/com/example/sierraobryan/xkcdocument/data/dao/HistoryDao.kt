package com.example.sierraobryan.xkcdocument.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.sierraobryan.xkcdocument.data.model.ComicWithFavorite

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(comicWithFavorite: ComicWithFavorite)

    @Query("SELECT * FROM fav_table")
    fun getAllHistory() : LiveData<List<ComicWithFavorite>>

    @Query("SELECT * FROM fav_table WHERE isFavorite = 1")
    fun getAllFavorites() : LiveData<List<ComicWithFavorite>>

    @Delete
    fun delete(comicWithFavorite: ComicWithFavorite)

}