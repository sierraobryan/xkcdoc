package com.example.sierraobryan.xkcdocument.data.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.sierraobryan.xkcdocument.data.dao.HistoryDao
import com.example.sierraobryan.xkcdocument.data.database.HistoryDatabase
import com.example.sierraobryan.xkcdocument.data.model.ComicWithFavorite

class HistoryRepository(application: Application) {

    private val dao: HistoryDao
    private val historyData: LiveData<List<ComicWithFavorite>>
    private val favoriteData: LiveData<List<ComicWithFavorite>>

    init {
        val db = HistoryDatabase.getInstance(application)
        dao = db?.favoriteDao()!!
        historyData = dao.getAllHistory()
        favoriteData = dao.getAllFavorites()
    }

    fun getAll(): LiveData<List<ComicWithFavorite>> {
        return historyData
    }

    fun getAllFavorites(): LiveData<List<ComicWithFavorite>> {
        return favoriteData
    }


    fun insertOrUpdate(comicWithFavorite: ComicWithFavorite) {
        insertAsyncTask(dao).execute(comicWithFavorite)
    }

    fun delete(comicWithFavorite: ComicWithFavorite) {
        deleteAsyncTask(dao).execute(comicWithFavorite)
    }

    private class insertAsyncTask internal constructor(private val dao: HistoryDao) :
            AsyncTask<ComicWithFavorite, Void, Void>() {
        override fun doInBackground(vararg params: ComicWithFavorite): Void? {
            dao.insert(params[0])
            return null
        }
    }

    private class deleteAsyncTask internal constructor(private val dao: HistoryDao) :
            AsyncTask<ComicWithFavorite, Void, Void>() {
        override fun doInBackground(vararg params: ComicWithFavorite): Void? {
            dao.delete(params[0])
            return null
        }
    }

}