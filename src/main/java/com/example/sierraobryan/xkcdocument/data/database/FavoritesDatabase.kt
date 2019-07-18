package com.example.sierraobryan.xkcdocument.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sierraobryan.xkcdocument.data.dao.FavoriteDao
import com.example.sierraobryan.xkcdocument.data.model.FavoriteComic

@Database(entities = arrayOf(FavoriteComic::class), version = 1)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract fun favoriteDao() : FavoriteDao

    companion object {
        private var INSTANCE : FavoritesDatabase? = null

        fun getInstance(context: Context): FavoritesDatabase? {
            if (INSTANCE == null) {
                synchronized(FavoritesDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            FavoritesDatabase::class.java,
                            "favorites.db"
                    )
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE
        }
    }
}