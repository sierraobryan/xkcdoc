package com.example.sierraobryan.xkcdocument.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sierraobryan.xkcdocument.data.dao.HistoryDao
import com.example.sierraobryan.xkcdocument.data.model.ComicShort

@Database(entities = arrayOf(ComicShort::class), version = 3)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun favoriteDao() : HistoryDao

    companion object {
        private var INSTANCE : HistoryDatabase? = null

        fun getInstance(context: Context): HistoryDatabase? {
            if (INSTANCE == null) {
                synchronized(HistoryDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            HistoryDatabase::class.java,
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