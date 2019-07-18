package com.example.sierraobryan.xkcdocument.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.sierraobryan.xkcdocument.data.model.FavoriteComic

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteComic: FavoriteComic)

    @Query("SELECT * FROM fav_table")
    fun getAllFavorites() : LiveData<List<FavoriteComic>>

    @Delete
    fun delete(favoriteComic: FavoriteComic)

}