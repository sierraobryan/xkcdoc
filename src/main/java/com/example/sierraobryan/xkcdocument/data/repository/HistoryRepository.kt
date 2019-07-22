package com.example.sierraobryan.xkcdocument.data.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.sierraobryan.xkcdocument.data.dao.HistoryDao
import com.example.sierraobryan.xkcdocument.data.database.HistoryDatabase
import com.example.sierraobryan.xkcdocument.data.model.ComicShort

class HistoryRepository(application: Application) {

    private val dao: HistoryDao
    private val historyData: LiveData<List<ComicShort>>
    private val favoriteData: LiveData<List<ComicShort>>

    init {
        val db = HistoryDatabase.getInstance(application)
        dao = db?.favoriteDao()!!
        historyData = dao.getAllHistory()
        favoriteData = dao.getAllFavorites()
    }

    fun getAll(): LiveData<List<ComicShort>> {
        return historyData
    }

    fun getAllFavorites(): LiveData<List<ComicShort>> {
        return favoriteData
    }


    fun insert(comicShort: ComicShort) {
        insertAsyncTask(dao).execute(comicShort)
    }

    fun delete(comicShort: ComicShort) {
        deleteAsyncTask(dao).execute(comicShort)
    }

    private class insertAsyncTask internal constructor(private val dao: HistoryDao) :
            AsyncTask<ComicShort, Void, Void>() {
        override fun doInBackground(vararg params: ComicShort): Void? {
            dao.insert(params[0])
            return null
        }
    }

    private class deleteAsyncTask internal constructor(private val dao: HistoryDao) :
            AsyncTask<ComicShort, Void, Void>() {
        override fun doInBackground(vararg params: ComicShort): Void? {
            dao.delete(params[0])
            return null
        }
    }

}