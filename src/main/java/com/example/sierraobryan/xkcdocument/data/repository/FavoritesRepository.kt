package com.example.sierraobryan.xkcdocument.data.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.sierraobryan.xkcdocument.data.dao.FavoriteDao
import com.example.sierraobryan.xkcdocument.data.database.FavoritesDatabase
import com.example.sierraobryan.xkcdocument.data.model.ComicShort

class FavoritesRepository(application: Application) {

    private val dao: FavoriteDao
    private val data: LiveData<List<ComicShort>>

    init {
        val db = FavoritesDatabase.getInstance(application)
        dao = db?.favoriteDao()!!
        data = dao.getAllFavorites()
    }

    fun getAll(): LiveData<List<ComicShort>> {
        return data
    }

    fun insert(comicShort: ComicShort) {
        insertAsyncTask(dao).execute(comicShort)
    }

    fun delete(comicShort: ComicShort) {
        deleteAsyncTask(dao).execute(comicShort)
    }

    private class insertAsyncTask internal constructor(private val dao: FavoriteDao) :
            AsyncTask<ComicShort, Void, Void>() {
        override fun doInBackground(vararg params: ComicShort): Void? {
            dao.insert(params[0])
            return null
        }
    }

    private class deleteAsyncTask internal constructor(private val dao: FavoriteDao) :
            AsyncTask<ComicShort, Void, Void>() {
        override fun doInBackground(vararg params: ComicShort): Void? {
            dao.delete(params[0])
            return null
        }
    }

}